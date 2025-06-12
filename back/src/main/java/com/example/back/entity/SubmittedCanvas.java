package com.example.back.entity;

import java.time.LocalDateTime;
import java.util.List;

public class SubmittedCanvas {
    private String id;
    private LocalDateTime createtime;
    private Integer height;
    private String name;
    private String openid;
    private String status;
    private LocalDateTime updatetime;
    private Integer width;
    private List<SubmittedElement> elements;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getCreatetime() { return createtime; }
    public void setCreatetime(LocalDateTime createtime) { this.createtime = createtime; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOpenid() { return openid; }
    public void setOpenid(String openid) { this.openid = openid; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getUpdatetime() { return updatetime; }
    public void setUpdatetime(LocalDateTime updatetime) { this.updatetime = updatetime; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public List<SubmittedElement> getElements() { return elements; }
    public void setElements(List<SubmittedElement> elements) { this.elements = elements; }
}