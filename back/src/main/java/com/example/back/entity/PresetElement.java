package com.example.back.entity;

import com.example.back.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class PresetElement {
    private String id;
    private float angle;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    private int height;
    private String presetId;
    private String src;
    private String type;
    private int width;
    private float x;
    private float y;
    private String submissionId;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public String getPresetId() { return presetId; }
    public void setPresetId(String presetId) { this.presetId = presetId; }

    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public String getSubmissionId() { return submissionId; }
    public void setSubmissionId(String submissionId) { this.submissionId = submissionId; }
} 