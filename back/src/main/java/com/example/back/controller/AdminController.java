package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/check")
    public Result checkAdmin(@RequestParam String openid) {
        boolean isAdmin = adminService.checkAdmin(openid);
        return Result.success(isAdmin);
    }

    @GetMapping("/images")
    public Result getAllImages() {
        Map<String, String[]> images = adminService.getAllImages();
        return Result.success(images);
    }
} 