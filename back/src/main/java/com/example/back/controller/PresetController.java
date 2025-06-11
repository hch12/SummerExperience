package com.example.back.controller;

import com.example.back.common.Result;
import com.example.back.common.dto.PresetDTO;
import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import com.example.back.service.PresetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preset")
public class PresetController {
    @Autowired
    private PresetService presetService;

    @PostMapping("/uploadPreset")
    public Result<Void> uploadPreset(@RequestBody PresetDTO presetDTO) {
        presetService.uploadPreset(presetDTO.getTemplate(), presetDTO.getElements());
        return Result.success(null);
    }

    @GetMapping("/getPresetList")
    public Result<List<PresetTemplate>> getPresetList() {
        List<PresetTemplate> presets = presetService.getPresetList();
        return Result.success(presets);
    }

    @GetMapping("/{id}")
    public Result<PresetTemplate> getPresetById(@PathVariable String id) {
        PresetTemplate preset = presetService.getPresetById(id);
        return Result.success(preset);
    }

    @GetMapping("/{id}/elements")
    public Result<List<PresetElement>> getPresetElements(@PathVariable String id) {
        List<PresetElement> elements = presetService.getPresetElements(id);
        return Result.success(elements);
    }
} 