package com.example.back.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.back.config.LocalDateTimeSerializer;
import java.time.LocalDateTime;

public class Element {
    private String openid;
    private String src;
    private double x;
    private double y;
    private int width;
    private int height;
    private double angle;
    private String canvasid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    // Getter和Setter方法
    public String getOpenid() { return openid; }
    public void setOpenid(String openid) { this.openid = openid; }

    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    public String getCanvasid() { return canvasid; }
    public void setCanvasid(String canvasid) { this.canvasid = canvasid; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    @Override
    public String toString() {
        return "Element{" +
                "src='" + src + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", angle=" + angle +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
