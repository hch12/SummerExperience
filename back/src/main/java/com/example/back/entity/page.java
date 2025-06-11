package com.example.back.entity;

import cn.hutool.json.JSONObject;

public class page {
    String openid;
    JSONObject data;
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
