package com.example.back.service;


import cn.hutool.json.JSONObject;
import com.example.back.entity.CanvasInfo;

import java.util.List;

public interface canvasService {
     JSONObject saveCanvas(String openId,JSONObject data);

     JSONObject updateCanvas(String openid, JSONObject data);
     JSONObject getCanvasListByOpenId(String openId);

     JSONObject getElementsByCanvasId(JSONObject data);

     JSONObject deleteCanvasByCanvasIdAndOpenId(String openid, JSONObject data);

     JSONObject updateCanvasName(String openid, JSONObject data);
}
