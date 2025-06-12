package com.example.back.entity;

import java.time.LocalDateTime;

public class SubmittedElement {
    private String id;
    private Double angle;
    private LocalDateTime createtime;
    private Integer height;
    private String src;
    private String submissionid;
    private String type;
    private Integer width;
    private Double x;
    private Double y;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Double getAngle() { return angle; }
    public void setAngle(Double angle) { this.angle = angle; }
    public LocalDateTime getCreatetime() { return createtime; }
    public void setCreatetime(LocalDateTime createtime) { this.createtime = createtime; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }
    public String getSubmissionid() { return submissionid; }
    public void setSubmissionid(String submissionid) { this.submissionid = submissionid; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
}