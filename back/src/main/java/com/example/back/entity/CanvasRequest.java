package com.example.back.entity;

import com.example.back.entity.SubmittedCanvas;

public class CanvasRequest {
    private String openID;
    private SubmittedCanvas canvas;

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public SubmittedCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(SubmittedCanvas canvas) {
        this.canvas = canvas;
    }
}
