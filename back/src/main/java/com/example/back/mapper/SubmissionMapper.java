package com.example.back.mapper;

import com.example.back.entity.SubmittedCanvas;
import com.example.back.entity.SubmittedElement;
import com.example.back.entity.SystemSetting;
import com.example.back.entity.UserSubmissionStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

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
    void insertCanvas(SubmittedCanvas canvas);

    @Insert("INSERT INTO submitted_elements (id, angle, createtime, height, src, submissionid, type, width, x, y) " +
            "VALUES (#{id}, #{angle}, #{createtime}, #{height}, #{src}, #{submissionid}, #{type}, #{width}, #{x}, #{y})")
    void insertElement(SubmittedElement element);

    // 新增用户提交统计相关方法
    @Select("SELECT * FROM user_submission_stats WHERE openid = #{openid}")
    UserSubmissionStats findUserSubmissionStatsByOpenid(@Param("openid") String openid);

    @Insert("INSERT INTO user_submission_stats (id, openid, totalSubmissions, remainingSubmissions, createtime, updatetime) " +
            "VALUES (#{id}, #{openid}, #{totalSubmissions}, #{remainingSubmissions}, #{createtime}, #{updatetime})")
    void insertUserSubmissionStats(UserSubmissionStats stats);

    @Update("UPDATE user_submission_stats SET totalSubmissions = #{totalSubmissions}, " +
            "remainingSubmissions = #{remainingSubmissions}, updatetime = #{updatetime} WHERE openid = #{openid}")
    void updateUserSubmissionStats(UserSubmissionStats stats);
}