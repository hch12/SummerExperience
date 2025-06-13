package com.example.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.back.config.LocalDateTimeSerializer;
import java.time.LocalDateTime;


public class CanvasInfo {
    private String canvasId;  // 画布唯一标识
    private String openid;    // 用户唯一标识
    private Integer height;   // 画布高度
    private Integer width;    // 画布宽度

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;  // 创建时间

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;  // 更新时间

    // Getters and Setters
    public String getCanvasId() { return canvasId; }
    public void setCanvasId(String canvasId) { this.canvasId = canvasId; }

    public String getOpenid() { return openid; }
    public void setOpenid(String openid) { this.openid = openid; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
