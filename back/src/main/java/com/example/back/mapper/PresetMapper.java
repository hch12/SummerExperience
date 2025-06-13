package com.example.back.mapper;

import com.example.back.entity.PresetTemplate;
import com.example.back.entity.PresetElement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PresetMapper {
    @Insert("INSERT INTO preset_templates (id, creatorid, height, name, `order`, width, createtime, updatetime) " +
            "VALUES (#{id}, #{creatorId}, #{height}, #{name}, #{order}, #{width}, NOW(6), NOW(6))")
    void insertPresetTemplate(PresetTemplate template);

    @Insert("INSERT INTO preset_elements (id, angle, height, presetid, src, type, width, x, y, createtime, updatetime, submissionId) " +
            "VALUES (#{id}, #{angle}, #{height}, #{presetId}, #{src}, #{type}, #{width}, #{x}, #{y}, NOW(6), NOW(6), #{submissionId})")
    void insertPresetElement(PresetElement element);

    @Select("SELECT * FROM preset_templates ORDER BY `order` ASC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "elements", column = "id", javaType = List.class, many = @Many(select = "getPresetElementsByPresetId"))
    })
    List<PresetTemplate> getAllPresetTemplates();

    @Select("SELECT * FROM preset_elements WHERE presetid = #{presetId}")
    List<PresetElement> getPresetElementsByPresetId(String presetId);

    @Select("SELECT * FROM preset_templates WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "elements", column = "id", javaType = List.class, many = @Many(select = "getPresetElementsByPresetId"))
    })
    PresetTemplate getPresetTemplateById(String id);
} 