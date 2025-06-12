package com.example.back.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.common.Result;
import com.example.back.entity.page;
import com.example.back.service.canvasService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/canvas")
public class canvasController {
    @Resource com.example.back.service.canvasService canvasService;

    @PostMapping("/save") // 接口的路径，全局唯一的
    public Result save(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        JSONObject result=canvasService.saveCanvas(object.getOpenid(),object.getData());
        return Result.success(result);  // 接口的返回值
    }
    @PostMapping("/update") // 接口的路径，全局唯一的
    public Result update(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        JSONObject result=canvasService.updateCanvas(object.getOpenid(),object.getData());
        return Result.success(result);  // 接口的返回值
    }
    @PostMapping("/list") // 接口的路径，全局唯一的
    public Result getCanvasList(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        JSONObject result=canvasService.getCanvasListByOpenId(object.getOpenid());
        return Result.success(result);  // 接口的返回值
    }
    @PostMapping("/detail") // 接口的路径，全局唯一的
    public Result getCanvasDetail(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getData());
        JSONObject result=canvasService.getElementsByCanvasId(object.getData());
        return Result.success(result);  // 接口的返回值
    }
    @PostMapping("/delete") // 接口的路径，全局唯一的
    public Result DeleteCanvas(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        JSONObject result=canvasService.deleteCanvasByCanvasIdAndOpenId(object.getOpenid(),object.getData());
        return Result.success(result);  // 接口的返回值
    }
    @PostMapping("/update-name") // 接口的路径，全局唯一的
    public Result updateCanvasName(@RequestBody page object) {
        System.out.println("接收数据:");
        System.out.println(object.getOpenid());
        System.out.println(object.getData());
        JSONObject result=canvasService.updateCanvasName(object.getOpenid(),object.getData());
        return Result.success(result);  // 接口的返回值
    }
}
