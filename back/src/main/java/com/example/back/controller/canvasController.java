package com.example.back.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.back.common.Result;
import com.example.back.entity.*;
import com.example.back.service.canvasService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/canvas")
public class canvasController {
    @Resource com.example.back.service.canvasService canvasService;
    @Resource com.example.back.service.SubmissionService submissionService;

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
    @PostMapping ("/list") // 接口的路径，全局唯一的
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
    @PostMapping("/submit")
    public Result submitCanvas(@RequestBody CanvasRequest request) {
        try {
            String openID = request.getOpenid();
            SubmittedCanvas canvas = request.getCanvas();

            if (openID == null || openID.isEmpty()) {
                return Result.error("Missing openID");
            }
            if (canvas == null) {
                return Result.error("Canvas data is missing");
            }

            List<SubmittedElement> elements = canvas.getElements();
            if (elements == null || elements.isEmpty()) {
                return Result.error("Canvas must contain at least one element");
            }

            String submissionId = submissionService.submitCanvas(openID, canvas, elements);

            // 获取更新后的剩余提交次数
            UserSubmissionStats stats = submissionService.getUserSubmissionStats(openID);
            int remainingSubmissions = stats != null ? stats.getRemainingSubmissions() :
                    submissionService.getMaxSubmissions() - 1;

            return Result.success(Map.of(
                    "submissionId", submissionId,
                    "remainingSubmissions", remainingSubmissions
            ));
        } catch (IllegalStateException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("提交失败: " + e.getMessage());
        }
    }
}
