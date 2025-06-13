package com.example.back.entity;

import java.time.LocalDateTime;

public class UserSubmissionStats {
    private String id;
    private String openid;
    private Integer totalSubmissions;
    private Integer remainingSubmissions;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Integer totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public Integer getRemainingSubmissions() {
        return remainingSubmissions;
    }

    public void setRemainingSubmissions(Integer remainingSubmissions) {
        this.remainingSubmissions = remainingSubmissions;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }
}