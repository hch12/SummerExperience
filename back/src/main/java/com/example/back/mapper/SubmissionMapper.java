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

    @Select("SELECT max_submissions FROM user_submission_limit WHERE openid = #{openid}")
    Integer getUserMaxSubmissions(String openid);

    @Select("SELECT value FROM settings WHERE id = 'maxSubmissions'")
    String getSystemMaxSubmissions();

    @Update("UPDATE submitted_canvas SET status = #{status} WHERE id = #{submissionId}")
    void updateSubmissionStatus(@Param("submissionId") String submissionId, @Param("status") String status);

    @Update("UPDATE system_setting SET value = #{maxSubmissions} WHERE id = 'maxSubmissions'")
    void updateMaxSubmissions(@Param("maxSubmissions") Integer maxSubmissions);

    @Insert("INSERT INTO submitted_canvas (id, createtime, height, name, openid, updatetime, width) " +
            "VALUES (#{id}, NOW(6), #{height}, #{name}, #{openid}, NOW(6), #{width})")
    void insertCanvas(SubmittedCanvas canvas);

    @Insert("INSERT INTO submitted_elements (id, angle, createtime, updatetime, height, src, submissionid, width, x, y) " +
            "VALUES (#{id}, #{angle}, NOW(6), NOW(6), #{height}, #{src}, #{submissionid}, #{width}, #{x}, #{y})")
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

    @Select("  SELECT * FROM user_submission_stats")
    List<UserSubmissionStats> findAllUserSubmissionStats();

    @Select("SELECT * FROM submitted_canvas")
    List<SubmittedCanvas> findAllCanvases();
}