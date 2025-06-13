package com.example.back.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.entity.Element;
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

    @Autowired
    public SubmissionServiceImpl(SubmissionMapper submissionMapper) {
        this.submissionMapper = submissionMapper;
    }

    @Override
    public List<SubmittedCanvas> getSubmissions(String openid) {
        List<SubmittedCanvas> canvases = submissionMapper.findCanvasByOpenid(openid);

        canvases.forEach(canvas -> {
            List<SubmittedElement> elements = submissionMapper.findElementsBySubmissionId(canvas.getId());
            canvas.setElements(elements);
        });

        return canvases;
    }

    @Override
    public Integer getMaxSubmissions() {
        return submissionMapper.getMaxSubmissions();
    }

    @Override
    public void updateSubmissionStatus(String submissionId, String status) {
        submissionMapper.updateSubmissionStatus(submissionId, status);
    }

    @Override
    public void updateMaxSubmissions(Integer maxSubmissions) {
        submissionMapper.updateMaxSubmissions(maxSubmissions);
    }

    @Override
    @Transactional
    public String submitCanvas(String openid, JSONObject data) {
        String canvasId = UUID.randomUUID().toString();
        int canvasWidth = data.getInt("canvasWidth");
        int canvasHeight = data.getInt("canvasHeight");
        String name=data.getStr("name");
        SubmittedCanvas canvas=new SubmittedCanvas();
        canvas.setHeight(canvasHeight);
        canvas.setWidth(canvasWidth);
        canvas.setId(canvasId);
        canvas.setName(name);
        canvas.setOpenid(openid);
        List<SubmittedElement> elements= new ArrayList<>();
        JSONArray pageElements = data.getJSONArray("elements");
        for (int i = 0; i < pageElements.size(); i++) {
            SubmittedElement element = new SubmittedElement();
            JSONObject pageElement =pageElements.getJSONObject(i);
            element.setSrc(pageElement.getStr("src"));
            element.setX(pageElement.getDouble("x"));
            element.setY(pageElement.getDouble("y"));
            element.setWidth(pageElement.getInt("width"));
            element.setHeight(pageElement.getInt("height"));
            element.setAngle(pageElement.getDouble("angle"));
            element.setId(openid);
            element.setSubmissionid(canvasId);
            elements.add(element);
        }
        submissionMapper.insertCanvas(canvas);
        for (SubmittedElement element : elements) {
            submissionMapper.insertElement(element);
        }

        // 更新用户提交统计
        UserSubmissionStats stats = submissionMapper.findUserSubmissionStatsByOpenid(openid);
        if (stats == null) {
            stats = new UserSubmissionStats();
            stats.setId(UUID.randomUUID().toString());
            stats.setOpenid(openid);
            stats.setTotalSubmissions(1);
            //这里没这个表我先用静态数据
            stats.setRemainingSubmissions(4);
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
}  // <-- 这里添加了缺失的闭合大括号