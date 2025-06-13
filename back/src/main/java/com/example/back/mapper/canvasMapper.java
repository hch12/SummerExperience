package com.example.back.mapper;

import com.example.back.entity.CanvasInfo;
import com.example.back.entity.Element;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface canvasMapper {
    /**
     * 插入画布基本信息
     */
    @Insert("INSERT INTO canvas_info (name, createtime, updatetime, height, width, canvasid, openid) " +
            "VALUES ('未命名作品', NOW(6), NOW(6), #{height}, #{width}, #{canvasid}, #{openid})")
    void insertCanvas(
            @Param("height") int height,
            @Param("width") int width,
            @Param("canvasid") String canvasid,
            @Param("openid") String openid);

    /**
     * 插入画布元素信息
     */
    @Insert("INSERT INTO canvas_elements (angle, canvasid, createtime, updatetime, height, openid, src, width, x, y) " +
            "VALUES (#{angle}, #{canvasid}, NOW(6), NOW(6), #{height}, #{openid}, #{src}, #{width}, #{x}, #{y})")
    void insertElement(Element element);

    /**
     * 根据画布ID和用户ID删除元素数据
     */
    @Delete("DELETE FROM canvas_elements WHERE canvasid = #{canvasId} AND openid = #{openId}")
    void deleteElementByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据画布ID和用户ID查询画布信息
     */
    @Select("SELECT name, createtime, updatetime, height, width, canvasid, openid FROM canvas_info " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    CanvasInfo selectInfoByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据画布ID和用户ID更新画布信息
     */
    @Update("UPDATE canvas_info " +
            "SET height = #{canvasHeight}, width = #{canvasWidth}, updatetime = NOW(6) " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    void updateinfoByCanvasIdAndOpenId(
            @Param("canvasHeight") int canvasHeight,
            @Param("canvasWidth") int canvasWidth,
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据用户openid获取该用户的所有画布列表
     */
    @Select("SELECT name, createtime, updatetime, height, width, canvasid, openid FROM canvas_info " +
            "WHERE openid = #{openId} ORDER BY updatetime DESC")
    List<CanvasInfo> selectCanvasListByOpenId(@Param("openId") String openId);

    @Select("SELECT DISTINCT canvasid FROM canvas_info")
    List<String> selectAllUniqueCanvasIds();

    @Select("SELECT angle, canvasid, createtime, updatetime, height, openid, src, type, width, x, y " +
            "FROM canvas_elements WHERE canvasid = #{canvasId}")
    List<Element> selectElementsByCanvasId(@Param("canvasId") String canvasId);

    /**
     * 根据canvasid和openid删除画布基本信息
     */
    @Delete("DELETE FROM canvas_info WHERE canvasid = #{canvasId} AND openid = #{openId}")
    int deleteCanvasInfoByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据canvasid和openid更新画布名称
     */
    @Update("UPDATE canvas_info SET name = #{name}, updatetime = NOW(6) " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    int updateCanvasName(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId,
            @Param("name") String name);
}
