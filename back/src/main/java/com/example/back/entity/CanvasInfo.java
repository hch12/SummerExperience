package com.example.back.entity;

import java.sql.Date;
import java.sql.Time;

public class CanvasInfo {
    private String canvasId;  // 画布唯一标识
    private String openid;    // 用户唯一标识
    private Integer height;   // 画布高度
    private Integer width;    // 画布宽度
    private Date createtime;  // 或 LocalDateTime
    private Date updatetime;  // 或 LocalDateTime

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCanvasId() {
        return canvasId;
    }

    public void setCanvasId(String canvasId) {
        this.canvasId = canvasId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
