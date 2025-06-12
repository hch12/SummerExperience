package com.example.back.service;

import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;

import java.util.List;

public interface SubmissionService {
    List<SubmittedCanvas> getSubmissions(String openid);
    Integer getMaxSubmissions();
    void updateSubmissionStatus(String submissionId, String status);
    void updateMaxSubmissions(Integer maxSubmissions);
    String submitCanvas(String openid, SubmittedCanvas canvas, List<SubmittedElement> elements);
}