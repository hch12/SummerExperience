package com.example.back.service;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.common.Result;
import com.example.back.entity.CanvasInfo;
import com.example.back.entity.Element;
import com.example.back.mapper.canvasMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class canvasServiceImpl implements canvasService{
    @Resource
    canvasMapper canvasMapper;

    @Override
    public JSONObject saveCanvas(String openId, JSONObject data) {
        // 1. 生成唯一的canvasid
        String canvasId = generateUniqueCanvasId();

        // 2. 提取画布基本信息
        int canvasWidth = data.getInt("canvasWidth");
        int canvasHeight = data.getInt("canvasHeight");

        // 3. 处理元素数据
        List<Element> elements = processElements(data, canvasId, openId);

        // 4. 插入数据库
        canvasMapper.insertCanvas(canvasHeight, canvasWidth, canvasId, openId);
        elements.forEach(canvasMapper::insertElement);

        // 5. 返回结果
        JSONObject result = new JSONObject();
        result.append("canvasid", canvasId);
        return result;
    }

    /**
     * 生成唯一的canvasid
     */
    private String generateUniqueCanvasId() {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        int maxLength = 12; // 确保不超过数据库字段长度

        List<String> existingIds = canvasMapper.selectAllUniqueCanvasIds();
        String newId;

        // 最多尝试100次生成唯一ID
        for (int attempt = 0; attempt < 100; attempt++) {
            StringBuilder sb = new StringBuilder(maxLength);
            for (int i = 0; i < maxLength; i++) {
                int index = random.nextInt(chars.length());
                sb.append(chars.charAt(index));
            }
            newId = sb.toString();

            if (!existingIds.contains(newId)) {
                return newId;
            }
        }

        throw new RuntimeException("无法生成唯一的canvasid，请稍后再试");
    }

    /**
     * 处理元素数据
     */
    private List<Element> processElements(JSONObject data, String canvasId, String openId) {
        List<Element> elements = new ArrayList<>();
        JSONArray pageElements = data.getJSONArray("elements");

        for (int i = 0; i < pageElements.size(); i++) {
            JSONObject pageElement = pageElements.getJSONObject(i);
            Element element = new Element();

            element.setSrc(pageElement.getStr("src"));
            element.setX(Float.parseFloat(pageElement.getStr("x")));
            element.setY(Float.parseFloat(pageElement.getStr("y")));
            element.setWidth(pageElement.getInt("width"));
            element.setHeight(pageElement.getInt("height"));
            element.setAngle(Float.parseFloat(pageElement.getStr("angle")));
            element.setOpenid(openId);
            element.setCanvasid(canvasId);
            elements.add(element);
        }

        return elements;
    }

    @Override
    public JSONObject updateCanvas(String openId, JSONObject data) {
        int canvasWidth = data.getInt("canvasWidth");
        int canvasHeight = data.getInt("canvasHeight");
        String canvasId=data.getStr("canvasId");
        List<Element> elements= new ArrayList<>();
        JSONArray pageElements = data.getJSONArray("elements");
        for (int i = 0; i < pageElements.size(); i++) {

            Element element = new Element();
            JSONObject pageElement =pageElements.getJSONObject(i);
            element.setSrc(pageElement.getStr("src"));
            element.setX(pageElement.getDouble("x"));
            element.setY(pageElement.getDouble("y"));
            element.setWidth(pageElement.getInt("width"));
            element.setHeight(pageElement.getInt("height"));
            element.setAngle(pageElement.getDouble("angle"));
            element.setOpenid(openId);
            element.setCanvasid(canvasId);
            elements.add(element);
        }
        try {
            CanvasInfo info = canvasMapper.selectInfoByCanvasIdAndOpenId(canvasId, openId);
            if (info.getHeight() != canvasHeight || info.getWidth() != canvasWidth) {
                canvasMapper.updateinfoByCanvasIdAndOpenId(canvasHeight, canvasWidth, canvasId, openId);
            }
            canvasMapper.deleteElementByCanvasIdAndOpenId(canvasId, openId);
            for (Element i : elements) {
                canvasMapper.insertElement(i);
            }
        }catch (Exception e) {
            // 发生异常时回滚事务
            throw new RuntimeException("更新画布失败", e);
        }
            JSONObject result = new JSONObject();
            result.append("canvasId", canvasId);
            return result;
    }
    @Override
    public JSONObject getCanvasListByOpenId(String openId) {
        JSONObject result=new JSONObject();
        // 参数验证
        if (StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        try {
            result.append("list", canvasMapper.selectCanvasListByOpenId(openId));
        }catch (Exception e) {
            // 发生异常时回滚事务
            throw new RuntimeException("获取画布列表失败", e);
        }
        // 调用Mapper方法查询数据库
        return result;
    }

    @Override
    public JSONObject getElementsByCanvasId(JSONObject data) {
        JSONObject result=new JSONObject();
        String canvasId= data.getStr("canvasId");
        if (StringUtils.isEmpty(canvasId)) {
            throw new IllegalArgumentException("画布ID不能为空");
        }
        try {
            result.append("detail",canvasMapper.selectElementsByCanvasId(canvasId));
        }catch (Exception e) {
            throw new RuntimeException("获取画布信息失败", e);
        }

        return result;
    }

    @Override
    public JSONObject deleteCanvasByCanvasIdAndOpenId(String openId, JSONObject data) {
        JSONObject result = new JSONObject();
        String canvasId = data.getStr("canvasId");
        if (StringUtils.isEmpty(canvasId) || StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("画布ID和用户ID不能为空");
        }
        try {
            result.append("deleteNum", canvasMapper.deleteCanvasInfoByCanvasIdAndOpenId(canvasId, openId));
        }catch (Exception e) {
            throw new RuntimeException("删除画布失败", e);
        }
        return result;
    }

    @Override
    public JSONObject updateCanvasName(String openId, JSONObject data) {
        JSONObject result = new JSONObject();
        String canvasId = data.getStr("canvasId");
        String newname=data.getStr("newName");
        if (StringUtils.isEmpty(canvasId) || StringUtils.isEmpty(openId)) {
            throw new IllegalArgumentException("画布ID和用户ID不能为空");
        }
        try {
            canvasMapper.updateCanvasName(canvasId, openId,newname);
            result.append("result", "更名成功");
        }catch (Exception e) {
            throw new RuntimeException("删除画布失败", e);
        }
        return result;
    }
}
