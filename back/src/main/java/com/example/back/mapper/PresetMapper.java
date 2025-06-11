package com.example.back.mapper;

import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PresetMapper {
    @Insert("INSERT INTO preset_templates (id, createtime, creatorid, height, name, `order`, updatetime, width) " +
            "VALUES (#{id}, #{createTime}, #{creatorId}, #{height}, #{name}, #{order}, #{updateTime}, #{width})")
    void insertPresetTemplate(PresetTemplate template);

    @Insert("INSERT INTO preset_elements (id, angle, createtime, height, presetid, src, type, width, x, y) " +
            "VALUES (#{id}, #{angle}, #{createTime}, #{height}, #{presetId}, #{src}, #{type}, #{width}, #{x}, #{y})")
    void insertPresetElement(PresetElement element);

    @Select("SELECT * FROM preset_templates ORDER BY `order` ASC")
    List<PresetTemplate> getAllPresetTemplates();

    @Select("SELECT * FROM preset_elements WHERE presetid = #{presetId}")
    List<PresetElement> getPresetElementsByPresetId(String presetId);

    @Select("SELECT * FROM preset_templates WHERE id = #{id}")
    PresetTemplate getPresetTemplateById(String id);
} 