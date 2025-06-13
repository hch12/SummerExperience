package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.service.AdminService;
import com.example.back.entity.page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/check-admin")
    public Result checkAdmin(@RequestBody page object) {
        String openid = object.getOpenid();
        if (openid == null || openid.isEmpty()) {
            return Result.error("OpenID 不能为空");
        }
        boolean isAdmin = adminService.checkAdmin(openid);
        return Result.success(isAdmin);
    }

    @PostMapping("/all-images")
    public Result getAllImages() {
        Map<String, String[]> images = adminService.getAllImages();
        return Result.success(images);
    }
} 