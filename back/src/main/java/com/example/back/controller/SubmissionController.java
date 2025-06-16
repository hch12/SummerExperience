package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.entity.SubmittedCanvas;
import com.example.back.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.back.entity.page;

import java.util.Map;
import cn.hutool.json.JSONObject;
import java.util.List; // 导入List接口
import java.util.ArrayList; // 如果需要使用ArrayList等实现类

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/list")
    public Result getSubmissions(@RequestBody page page) {
        if (page == null || page.getData() == null) {
            return Result.error("参数无效");
        }

        String openid = page.getOpenid();
        Integer check;
        try {
            check = page.getData().getInt("check");
        } catch (Exception e) {
            return Result.error("check参数无效");
        }

        List<SubmittedCanvas> result; // 使用List接口

        if (check != null && check == 1) {
            result = submissionService.getSubmissions(openid);
        } else if (check != null && check == 0) {
            result = submissionService.getSubmissions(null);
        } else {
            return Result.error("check参数必须为0或1");
        }

        return Result.success(result);
    }

    @PostMapping("/max")
    public Result getMaxSubmissions(@RequestBody page page) {
        try {
            String openid = page.getOpenid();
            if (openid == null || openid.isEmpty()) {
                return Result.error("Missing openID");
            }

            // 从全局设置中获取最大提交数（不再依赖 openid）
            int maxSubmissions = submissionService.getMaxSubmissions();
            return Result.success(Map.of("maxSubmissions", maxSubmissions));
        } catch (Exception e) {
            return Result.error("获取最大提交数失败: " + e.getMessage());
        }
    }

    @PostMapping("/update-max")
    public Result updateMaxSubmissions(@RequestBody page page) {
        if (page == null || page.getData() == null) {
            return Result.error("Invalid parameters");
        }

        Integer maxSubmissions = null;

        try {
            // data 是 JSONObject，调用 getInt("maxSubmissions") 获取值
            maxSubmissions = page.getData().getInt("maxSubmissions");
        } catch (Exception e) {
            return Result.error("maxSubmissions参数无效");
        }

        if (maxSubmissions == null) {
            return Result.error("maxSubmissions参数缺失");
        }

        submissionService.updateMaxSubmissions(maxSubmissions);
        return Result.success("最大提交数更新成功");
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



}