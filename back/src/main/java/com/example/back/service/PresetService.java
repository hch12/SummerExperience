package com.example.back.service;

import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import java.util.List;

public interface PresetService {
    void uploadPreset(PresetTemplate template, List<PresetElement> elements);
    List<PresetTemplate> getPresetList();
    PresetTemplate getPresetById(String id);
    List<PresetElement> getPresetElements(String presetId);
} 