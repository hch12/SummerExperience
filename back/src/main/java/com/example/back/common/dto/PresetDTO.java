package com.example.back.common.dto;

import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import java.util.List;

public class PresetDTO {
    private PresetTemplate template;
    private List<PresetElement> elements;

    public PresetTemplate getTemplate() {
        return template;
    }

    public void setTemplate(PresetTemplate template) {
        this.template = template;
    }

    public List<PresetElement> getElements() {
        return elements;
    }

    public void setElements(List<PresetElement> elements) {
        this.elements = elements;
    }
} 