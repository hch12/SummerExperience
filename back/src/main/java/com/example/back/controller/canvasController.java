package com.example.back.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.common.Result;
import com.example.back.entity.page;
import com.example.back.service.canvasService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/canvas")
public class canvasController {
    @Resource com.example.back.service.canvasService canvasService;

    @PostMapping("/save") // 接口的路径，全局唯一的
    public Result hello(@RequestBody page object) {
        JSONObject result=canvasService.saveCanvas(object.getOpenid(),object.getData());
        System.out.println("hello");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        return Result.success(result);  // 接口的返回值
    }
}
