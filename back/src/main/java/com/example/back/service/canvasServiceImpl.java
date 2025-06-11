package com.example.back.service;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.entity.Element;
import com.example.back.mapper.canvasMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class canvasServiceImpl implements canvasService{
    @Resource
    canvasMapper canvasMapper;

    @Override
    public JSONObject saveCanvas(String openId,JSONObject data) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(12);
        // 可用字符集：0-9和a-f
        String chars = "0123456789abcdef";

        for (int i = 0; i < 12; i++) {
            // 随机选择一个字符
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        int canvasWidth = data.getInt("canvasWidth");
        int canvasHeight = data.getInt("canvasHeight");
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
            element.setCanvasid(sb.toString());
            elements.add(element);
        }
        canvasMapper.insertCanvas(canvasHeight,canvasWidth,sb.toString(),openId);
        for (Element i:elements) {
            canvasMapper.insertElement(i);
        }
        JSONObject result=new JSONObject();
        result.append("openid",sb.toString());
        return result;
    }
}
