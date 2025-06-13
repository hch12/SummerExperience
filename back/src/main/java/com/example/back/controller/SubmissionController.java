package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.entity.CanvasRequest;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.entity.UserSubmissionStats;
import com.example.back.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.back.entity.page;

import java.util.List;
import java.util.Map;
import cn.hutool.json.JSONObject;
import java.util.HashMap;


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
            String openID = request.get("openid");
            if (openID == null || openID.isEmpty()) {
                return Result.error("Missing openID");
            }
            return Result.success(Map.of("data", submissionService.getSubmissions(openID)));
        } catch (Exception e) {
            return Result.error("获取提交列表失败: " + e.getMessage());
        }
    }

    @PostMapping("/max")
    public Result getMaxSubmissions(@RequestBody page page) {
        String openid = page.getOpenid();
        if (openid == null || openid.isEmpty()) {
            return Result.error("缺少 openid");
        }

        try {
            Integer max = submissionService.getMaxSubmissions(openid);
            Map<String, Object> result = new HashMap<>();
            result.put("maxSubmissions", max);
            result.put("success", true);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    @PostMapping("/update-status")
    public Result updateSubmissionStatus(@RequestBody page page) {
        try {
            String openid = page.getOpenid();
            JSONObject data = page.getData();

            if (openid == null || data == null) {
                return Result.error("缺少参数");
            }

            String submissionId = data.getStr("submissionId");  // 注意大小写需和前端一致
            String status = data.getStr("status");

            if (submissionId == null || status == null) {
                return Result.error("缺少 submissionId 或 status");
            }

            submissionService.updateSubmissionStatus(submissionId, status);
            return Result.success("状态更新成功");
        } catch (Exception e) {
            return Result.error("更新状态失败: " + e.getMessage());
        }
    }


    @PostMapping("/update-max")
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