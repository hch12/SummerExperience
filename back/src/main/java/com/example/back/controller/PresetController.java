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
    public Result uploadPreset(@RequestBody Map<String, Object> data) {
        try {
            // 组装 PresetTemplate
            PresetTemplate template = new PresetTemplate();
            template.setName((String) data.get("name"));
            template.setWidth(((Number) data.get("width")).intValue());
            template.setHeight(((Number) data.get("height")).intValue());
            template.setOrder(((Number) data.get("order")).intValue());
            template.setCreatorId(data.get("creatorId") != null ? (String) data.get("creatorId") : "system");

            // 组装 elements
            List<Map<String, Object>> elementsRaw = (List<Map<String, Object>>) data.get("elements");
            List<PresetElement> elements = elementsRaw.stream().map(e -> {
                PresetElement element = new PresetElement();
                element.setType((String) e.get("type"));
                element.setSrc((String) e.get("src"));
                element.setWidth(((Number) e.get("width")).intValue());
                element.setHeight(((Number) e.get("height")).intValue());
                element.setX(((Number) e.get("x")).floatValue());
                element.setY(((Number) e.get("y")).floatValue());
                element.setAngle(((Number) e.get("angle")).floatValue());
                // 其它字段可选
                return element;
            }).toList();

            presetService.uploadPreset(template, elements);
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