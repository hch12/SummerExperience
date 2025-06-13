package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.common.dto.PresetDTO;
import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import com.example.back.service.PresetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preset")
public class PresetController {
    @Autowired
    private PresetService presetService;

    @PostMapping("/upload")
    public Result uploadPreset(@RequestBody(required = false) PresetDTO presetDTO) {
        if (presetDTO == null) {
            return Result.error("请求数据不能为空");
        }
        try {
            presetService.uploadPreset(presetDTO.getTemplate(), presetDTO.getElements());
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("上传预设失败：" + e.getMessage());
        }
    }

    @PostMapping("/upload/v2")
    public Result uploadPresetV2(@RequestBody(required = false) Map<String, Object> request) {
        if (request == null) {
            return Result.error("请求数据不能为空");
        }
        try {
            PresetTemplate template = new PresetTemplate();
            List<PresetElement> elements = null;

            // 处理模板数据
            if (request.containsKey("template")) {
                Map<String, Object> templateData = (Map<String, Object>) request.get("template");
                template.setName((String) templateData.get("name"));
                template.setWidth(((Number) templateData.get("width")).intValue());
                template.setHeight(((Number) templateData.get("height")).intValue());
                template.setOrder(((Number) templateData.get("order")).intValue());
                if (templateData.containsKey("creatorId")) {
                    template.setCreatorId((String) templateData.get("creatorId"));
                }
            }

            // 处理元素数据
            if (request.containsKey("elements")) {
                elements = ((List<Map<String, Object>>) request.get("elements")).stream()
                    .map(elementData -> {
                        PresetElement element = new PresetElement();
                        element.setType((String) elementData.get("type"));
                        element.setSrc((String) elementData.get("src"));
                        element.setWidth(((Number) elementData.get("width")).intValue());
                        element.setHeight(((Number) elementData.get("height")).intValue());
                        element.setX(((Number) elementData.get("x")).floatValue());
                        element.setY(((Number) elementData.get("y")).floatValue());
                        element.setAngle(((Number) elementData.get("angle")).floatValue());
                        return element;
                    })
                    .toList();
            }

            presetService.uploadPreset(template, elements);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("上传预设失败：" + e.getMessage());
        }
    }

    @PostMapping("/list")
    public Result getPresetList() {
        List<PresetTemplate> presets = presetService.getPresetList();
        return Result.success(presets);
    }

    @PostMapping("/{id}")
    public Result getPresetById(@PathVariable String id) {
        PresetTemplate preset = presetService.getPresetById(id);
        return Result.success(preset);
    }

    @PostMapping("/{id}/elements")
    public Result getPresetElements(@PathVariable String id) {
        List<PresetElement> elements = presetService.getPresetElements(id);
        return Result.success(elements);
    }
} 