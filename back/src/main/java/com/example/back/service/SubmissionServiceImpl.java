package com.example.back.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.entity.GlobalSettings;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.entity.UserSubmissionStats;
import com.example.back.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionMapper submissionMapper;
    private final GlobalSettings globalSettings;

    @Autowired
    public SubmissionServiceImpl(SubmissionMapper submissionMapper, GlobalSettings globalSettings) {
        this.submissionMapper = submissionMapper;
        this.globalSettings = globalSettings;
    }

    @Override
    public List<SubmittedCanvas> getSubmissions(String openid) {
        List<SubmittedCanvas> canvases;

        if (openid == null || openid.trim().isEmpty()) {
            canvases = submissionMapper.findAllCanvases();
        } else {
            canvases = submissionMapper.findCanvasByOpenid(openid);
        }

        canvases.forEach(canvas -> {
            List<SubmittedElement> elements = submissionMapper.findElementsBySubmissionId(canvas.getId());
            canvas.setElements(elements);
        });

        return canvases;
    }

    @Override
    public int getMaxSubmissions() {
        return globalSettings.getMaxSubmissions();
    }

    @Override
    public void updateSubmissionStatus(String submissionId, String status) {
        submissionMapper.updateSubmissionStatus(submissionId, status);
    }

    @Override
    public void updateMaxSubmissions(int maxSubmissions) {
        globalSettings.setMaxSubmissions(maxSubmissions);

        // 查询所有用户提交统计数据
        List<UserSubmissionStats> allStats = submissionMapper.findAllUserSubmissionStats();

        for (UserSubmissionStats stats : allStats) {
            if (stats.getRemainingSubmissions() > maxSubmissions) {
                stats.setRemainingSubmissions(maxSubmissions);
                stats.setUpdatetime(java.time.LocalDateTime.now());
                submissionMapper.updateUserSubmissionStats(stats);
            }
        }
    }

    @Override
    @Transactional
    public String submitCanvas(String openid, JSONObject data) {
        // 先查限制表获取最大提交数
        Integer maxSubmissions = submissionMapper.getUserMaxSubmissions(openid);
        if (maxSubmissions == null) {
            maxSubmissions = 5; // 默认最大提交数，比如5
        }

        UserSubmissionStats stats = submissionMapper.findUserSubmissionStatsByOpenid(openid);
        if (stats != null && stats.getRemainingSubmissions() <= 0) {
            throw new RuntimeException("提交次数已达上限");
        }

        String canvasId = UUID.randomUUID().toString();
        int canvasWidth = data.getInt("canvasWidth");
        int canvasHeight = data.getInt("canvasHeight");
        String name = data.getStr("name");

        SubmittedCanvas canvas = new SubmittedCanvas();
        canvas.setHeight(canvasHeight);
        canvas.setWidth(canvasWidth);
        canvas.setId(canvasId);
        canvas.setName(name);
        canvas.setOpenid(openid);

        List<SubmittedElement> elements = new ArrayList<>();
        JSONArray pageElements = data.getJSONArray("elements");
        for (int i = 0; i < pageElements.size(); i++) {
            SubmittedElement element = new SubmittedElement();
            JSONObject pageElement = pageElements.getJSONObject(i);
            element.setSrc(pageElement.getStr("src"));
            element.setX(pageElement.getDouble("x"));
            element.setY(pageElement.getDouble("y"));
            element.setWidth(pageElement.getInt("width"));
            element.setHeight(pageElement.getInt("height"));
            element.setAngle(pageElement.getDouble("angle"));
            element.setId(UUID.randomUUID().toString());
            element.setSubmissionid(canvasId);
            elements.add(element);
        }

        submissionMapper.insertCanvas(canvas);
        for (SubmittedElement element : elements) {
            submissionMapper.insertElement(element);
        }

        // 更新用户提交统计
        if (stats == null) {
            stats = new UserSubmissionStats();
            stats.setId(UUID.randomUUID().toString());
            stats.setOpenid(openid);
            stats.setTotalSubmissions(1);
            stats.setRemainingSubmissions(maxSubmissions - 1);
            stats.setCreatetime(java.time.LocalDateTime.now());
            stats.setUpdatetime(java.time.LocalDateTime.now());
            submissionMapper.insertUserSubmissionStats(stats);
        } else {
            stats.setTotalSubmissions(stats.getTotalSubmissions() + 1);
            stats.setRemainingSubmissions(stats.getRemainingSubmissions() - 1);
            stats.setUpdatetime(java.time.LocalDateTime.now());
            submissionMapper.updateUserSubmissionStats(stats);
        }

        return canvasId;
    }

    @Override
    public UserSubmissionStats getUserSubmissionStats(String openid) {
        return submissionMapper.findUserSubmissionStatsByOpenid(openid);
    }
}
