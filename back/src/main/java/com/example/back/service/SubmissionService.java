package com.example.back.service;

import cn.hutool.json.JSONObject;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.UserSubmissionStats;
import java.util.List;

public interface SubmissionService {
    List<SubmittedCanvas> getSubmissions(String openid);
    int getMaxSubmissions();
    void updateMaxSubmissions(int maxSubmissions);
    void updateSubmissionStatus(String submissionId, String status);
    String submitCanvas(String openid, JSONObject data);

    // 添加的新方法
    UserSubmissionStats getUserSubmissionStats(String openid);
}

