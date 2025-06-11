package com.example.back.service;


import cn.hutool.json.JSONObject;

public interface canvasService {
     JSONObject saveCanvas(String openId,JSONObject data);
}
