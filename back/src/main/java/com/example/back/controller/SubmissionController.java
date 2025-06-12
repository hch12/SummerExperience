package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/list")
    public Result getSubmissions(@RequestParam String openID) {
        List<SubmittedCanvas> data = submissionService.getSubmissions(openID);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return Result.success(result);
    }

    @GetMapping("/maxSubmissions")
    public Result getMaxSubmissions() {
        Integer maxSubmissions = submissionService.getMaxSubmissions();
        Map<String, Object> result = new HashMap<>();
        result.put("maxSubmissions", maxSubmissions);
        result.put("success", true);
        return Result.success(result);
    }

    @PostMapping("/updateStatus")
    public Result updateSubmissionStatus(@RequestParam String openID, @RequestBody Map<String, String> data) {
        String submissionId = data.get("submissionId");
        String status = data.get("status");

        if (submissionId == null || status == null) {
            return Result.error("Invalid parameters");
        }

        submissionService.updateSubmissionStatus(submissionId, status);
        return Result.success("Status updated successfully");
    }

    @PostMapping("/updateMaxSubmissions")
    public Result updateMaxSubmissions(@RequestBody Map<String, Integer> data) {
        Integer maxSubmissions = data.get("maxSubmissions");

        if (maxSubmissions == null) {
            return Result.error("Invalid parameters");
        }

        submissionService.updateMaxSubmissions(maxSubmissions);
        return Result.success("Max submissions updated successfully");
    }

    @PostMapping("/submitCanvas")
    public Result submitCanvas(@RequestParam String openID, @RequestBody SubmittedCanvas canvas) {
        List<SubmittedElement> elements = canvas.getElements();
        if (elements == null || elements.isEmpty()) {
            return Result.error("Canvas must contain at least one element");
        }

        String submissionId = submissionService.submitCanvas(openID, canvas, elements);
        Map<String, Object> result = new HashMap<>();
        result.put("submissionId", submissionId);
        result.put("remainingSubmissions", submissionService.getMaxSubmissions() - 1);
        return Result.success(result);
    }
}