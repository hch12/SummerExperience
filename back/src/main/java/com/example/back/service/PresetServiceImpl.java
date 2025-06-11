package com.example.back.service;

import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import com.example.back.mapper.PresetMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PresetServiceImpl implements PresetService {
    @Resource
    private PresetMapper presetMapper;

    @Override
    @Transactional
    public void uploadPreset(PresetTemplate template, List<PresetElement> elements) {
        // 生成UUID
        String templateId = UUID.randomUUID().toString().replace("-", "");
        template.setId(templateId);
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        
        // 保存template
        presetMapper.insertPresetTemplate(template);

        // 保存elements
        for (PresetElement element : elements) {
            element.setId(UUID.randomUUID().toString().replace("-", ""));
            element.setPresetId(templateId);
            element.setCreateTime(LocalDateTime.now());
            presetMapper.insertPresetElement(element);
        }
    }

    @Override
    public List<PresetTemplate> getPresetList() {
        return presetMapper.getAllPresetTemplates();
    }

    @Override
    public PresetTemplate getPresetById(String id) {
        return presetMapper.getPresetTemplateById(id);
    }

    @Override
    public List<PresetElement> getPresetElements(String presetId) {
        return presetMapper.getPresetElementsByPresetId(presetId);
    }
} 