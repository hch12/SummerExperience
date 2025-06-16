package com.example.back.entity;

import org.springframework.stereotype.Component;

@Component
public class GlobalSettings {
    private volatile int maxSubmissions = 1; // 初始值，比如10

    public int getMaxSubmissions() {
        return maxSubmissions;
    }

    public void setMaxSubmissions(int maxSubmissions) {
        this.maxSubmissions = maxSubmissions;
    }
}

