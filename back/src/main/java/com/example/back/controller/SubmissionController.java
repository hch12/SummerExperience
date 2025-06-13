package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.entity.CanvasRequest;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.entity.UserSubmissionStats;
import com.example.back.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list")
    public Result getSubmissions(@RequestBody Map<String, String> request) {
        try {
            String openID = request.get("openID");
            if (openID == null || openID.isEmpty()) {
                return Result.error("Missing openID");
            }
            return Result.success(Map.of("data", submissionService.getSubmissions(openID)));
        } catch (Exception e) {
            return Result.error("获取提交列表失败: " + e.getMessage());
        }
    }

    @PostMapping("/maxSubmissions")
    public Result getMaxSubmissions() {
        try {
            return Result.success(Map.of(
                    "maxSubmissions", submissionService.getMaxSubmissions(),
                    "success", true
            ));
        } catch (Exception e) {
            return Result.error("获取最大提交数失败: " + e.getMessage());
        }
    }

    @PostMapping("/updateStatus")
    public Result updateSubmissionStatus(@RequestBody Map<String, String> data) {
        try {
            String submissionId = data.get("submissionId");
            String status = data.get("status");

            if (submissionId == null || status == null) {
                return Result.error("Invalid parameters");
            }

            submissionService.updateSubmissionStatus(submissionId, status);
            return Result.success("状态更新成功");
        } catch (Exception e) {
            return Result.error("更新状态失败: " + e.getMessage());
        }
    }

    @PostMapping("/updateMaxSubmissions")
    public Result updateMaxSubmissions(@RequestBody Map<String, Integer> data) {
        try {
            Integer maxSubmissions = data.get("maxSubmissions");

            if (maxSubmissions == null) {
                return Result.error("Invalid parameters");
            }

            submissionService.updateMaxSubmissions(maxSubmissions);
            return Result.success("最大提交数更新成功");
        } catch (Exception e) {
            return Result.error("更新最大提交数失败: " + e.getMessage());
        }
    }


}