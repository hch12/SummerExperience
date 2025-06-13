package com.example.back.entity;

import com.example.back.entity.SubmittedCanvas;

public class CanvasRequest {
    private String openid;
    private SubmittedCanvas canvas;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public SubmittedCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(SubmittedCanvas canvas) {
        this.canvas = canvas;
    }
}
