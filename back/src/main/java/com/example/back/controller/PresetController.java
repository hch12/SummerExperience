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
            
            Object templateWidthObj = data.get("width");
            if (templateWidthObj instanceof Number) {
                template.setWidth(((Number) templateWidthObj).intValue());
            } else {
                template.setWidth(0); // Default value if null or not a number
            }

            Object templateHeightObj = data.get("height");
            if (templateHeightObj instanceof Number) {
                template.setHeight(((Number) templateHeightObj).intValue());
            } else {
                template.setHeight(0); // Default value
            }

            Object templateOrderObj = data.get("order");
            if (templateOrderObj instanceof Number) {
                template.setOrder(((Number) templateOrderObj).intValue());
            } else {
                template.setOrder(0); // Default value
            }
            template.setCreatorId(data.get("creatorId") != null ? (String) data.get("creatorId") : "system");

            // 组装 elements
            Object elementsObj = data.get("elements"); // 先获取为Object
            List<PresetElement> elements = new java.util.ArrayList<>(); // 默认初始化为空列表

            if (elementsObj instanceof List) { // 检查是否是List类型
                List<Map<String, Object>> elementsRaw = (List<Map<String, Object>>) elementsObj;
                elements = elementsRaw.stream()
                    .filter(java.util.Objects::nonNull) // 过滤掉列表中的null元素
                    .map(e -> {
                        PresetElement element = new PresetElement();
                        element.setType((String) e.get("type"));
                        element.setSrc((String) e.get("src"));
                        
                        Object elementWidthObj = e.get("width");
                        if (elementWidthObj instanceof Number) {
                            element.setWidth(((Number) elementWidthObj).intValue());
                        } else {
                            element.setWidth(0); // Default value
                        }

                        Object elementHeightObj = e.get("height");
                        if (elementHeightObj instanceof Number) {
                            element.setHeight(((Number) elementHeightObj).intValue());
                        } else {
                            element.setHeight(0); // Default value
                        }

                        Object elementXObj = e.get("x");
                        if (elementXObj instanceof Number) {
                            element.setX(((Number) elementXObj).floatValue());
                        } else {
                            element.setX(0.0f); // Default value
                        }

                        Object elementYObj = e.get("y");
                        if (elementYObj instanceof Number) {
                            element.setY(((Number) elementYObj).floatValue());
                        } else {
                            element.setY(0.0f); // Default value
                        }

                        Object elementAngleObj = e.get("angle");
                        if (elementAngleObj instanceof Number) {
                            element.setAngle(((Number) elementAngleObj).floatValue());
                        } else {
                            element.setAngle(0.0f); // Default value
                        }
                        // 其它字段可选
                        return element;
                    }).toList();
            }

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
            List<PresetElement> elements = new java.util.ArrayList<>(); // Default to empty list

            // 处理模板数据
            if (request.containsKey("template")) {
                Map<String, Object> templateData = (Map<String, Object>) request.get("template");
                template.setName((String) templateData.get("name"));
                
                Object templateDataWidthObj = templateData.get("width");
                if (templateDataWidthObj instanceof Number) {
                    template.setWidth(((Number) templateDataWidthObj).intValue());
                } else {
                    template.setWidth(0); // Default value
                }

                Object templateDataHeightObj = templateData.get("height");
                if (templateDataHeightObj instanceof Number) {
                    template.setHeight(((Number) templateDataHeightObj).intValue());
                } else {
                    template.setHeight(0); // Default value
                }

                Object templateDataOrderObj = templateData.get("order");
                if (templateDataOrderObj instanceof Number) {
                    template.setOrder(((Number) templateDataOrderObj).intValue());
                } else {
                    template.setOrder(0); // Default value
                }

                if (templateData.containsKey("creatorId")) {
                    template.setCreatorId((String) templateData.get("creatorId"));
                }
            }

            // 处理元素数据
            Object requestElementsObj = request.get("elements");
            if (requestElementsObj instanceof List) {
                List<Map<String, Object>> elementsRaw = (List<Map<String, Object>>) requestElementsObj;
                elements = elementsRaw.stream()
                    .filter(java.util.Objects::nonNull)
                    .map(elementData -> {
                        PresetElement element = new PresetElement();
                        element.setType((String) elementData.get("type"));
                        element.setSrc((String) elementData.get("src"));

                        Object elementDataWidthObj = elementData.get("width");
                        if (elementDataWidthObj instanceof Number) {
                            element.setWidth(((Number) elementDataWidthObj).intValue());
                        } else {
                            element.setWidth(0); // Default value
                        }

                        Object elementDataHeightObj = elementData.get("height");
                        if (elementDataHeightObj instanceof Number) {
                            element.setHeight(((Number) elementDataHeightObj).intValue());
                        } else {
                            element.setHeight(0); // Default value
                        }

                        Object elementDataXObj = elementData.get("x");
                        if (elementDataXObj instanceof Number) {
                            element.setX(((Number) elementDataXObj).floatValue());
                        } else {
                            element.setX(0.0f); // Default value
                        }

                        Object elementDataYObj = elementData.get("y");
                        if (elementDataYObj instanceof Number) {
                            element.setY(((Number) elementDataYObj).floatValue());
                        } else {
                            element.setY(0.0f); // Default value
                        }

                        Object elementDataAngleObj = elementData.get("angle");
                        if (elementDataAngleObj instanceof Number) {
                            element.setAngle(((Number) elementDataAngleObj).floatValue());
                        } else {
                            element.setAngle(0.0f); // Default value
                        }
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