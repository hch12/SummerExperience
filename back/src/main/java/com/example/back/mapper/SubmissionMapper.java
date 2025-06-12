package com.example.back.mapper;

import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.entity.SystemSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface SubmissionMapper {
    @Select("SELECT * FROM submitted_canvas WHERE openid = #{openid}")
    List<SubmittedCanvas> findCanvasByOpenid(@Param("openid") String openid);

    @Select("SELECT * FROM submitted_elements WHERE submissionid = #{submissionid}")
    List<SubmittedElement> findElementsBySubmissionId(@Param("submissionid") String submissionid);

    @Select("SELECT value FROM system_setting WHERE id = 'maxSubmissions'")
    Integer getMaxSubmissions();

    @Update("UPDATE submitted_canvas SET status = #{status} WHERE id = #{submissionId}")
    void updateSubmissionStatus(@Param("submissionId") String submissionId, @Param("status") String status);

    @Update("UPDATE system_setting SET value = #{maxSubmissions} WHERE id = 'maxSubmissions'")
    void updateMaxSubmissions(@Param("maxSubmissions") Integer maxSubmissions);

    @Insert("INSERT INTO submitted_canvas (id, createtime, height, name, openid, status, updatetime, width) " +
            "VALUES (#{id}, #{createtime}, #{height}, #{name}, #{openid}, #{status}, #{updatetime}, #{width})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCanvas(SubmittedCanvas canvas);

    @Insert("INSERT INTO submitted_elements (id, angle, createtime, height, src, submissionid, type, width, x, y) " +
            "VALUES (#{id}, #{angle}, #{createtime}, #{height}, #{src}, #{submissionid}, #{type}, #{width}, #{x}, #{y})")
    void insertElement(SubmittedElement element);
}