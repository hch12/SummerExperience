package com.example.back.controller;

import cn.hutool.json.JSONObject;
import com.example.back.common.Result;
import com.example.back.service.canvasService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/canvas")
public class canvasController {
    @Resource com.example.back.service.canvasService canvasService;

    @GetMapping("/hello") // 接口的路径，全局唯一的
    public Result hello() {
        return Result.success("ty");  // 接口的返回值
    }
}
