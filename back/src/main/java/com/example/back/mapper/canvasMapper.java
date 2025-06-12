package com.example.back.mapper;

import com.example.back.entity.CanvasInfo;
import com.example.back.entity.Element;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface canvasMapper {
    /**
     * 插入画布基本信息
     * @param height 画布高度
     * @param width 画布宽度
     * @param canvasid 画布唯一标识
     * @param openid 用户唯一标识
     */
    @Insert("INSERT INTO canvas_info (name, createtime, updatetime, height, width, canvasid, openid) " +
            "VALUES ('未命名作品', NOW(), NOW(), #{height}, #{width}, #{canvasid}, #{openid})")
    void insertCanvas(
            @Param("height") int height,
            @Param("width") int width,
            @Param("canvasid") String canvasid,
            @Param("openid") String openid);

    /**
     * 插入画布元素信息
     * @param element
     */
    @Insert("INSERT INTO canvas_elements (angle, canvasid, createtime, updatetime, height, openid, src, width, x, y) " +
            "VALUES (#{angle}, #{canvasid}, NOW(), NOW(), #{height}, #{openid}, #{src}, #{width}, #{x}, #{y})")
    void insertElement(Element element);

    /**
     * 根据画布ID和用户ID删除元素数据
     * @param canvasId 画布唯一标识
     * @param openId 用户唯一标识
     */
    @Delete("DELETE FROM canvas_elements WHERE canvasid = #{canvasId} AND openid = #{openId}")
    void deleteElementByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据画布ID和用户ID查询画布信息
     * @param canvasId 画布唯一标识
     * @param openId 用户唯一标识
     * @return 画布信息实体
     */
    @Select("SELECT name, createtime, updatetime, height, width, canvasid, openid FROM canvas_info " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    CanvasInfo selectInfoByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据画布ID和用户ID更新画布信息
     * @param canvasHeight 新的画布高度
     * @param canvasWidth 新的画布宽度
     * @param canvasId 画布唯一标识
     * @param openId 用户唯一标识
     */
    @Update("UPDATE canvas_info " +
            "SET height = #{canvasHeight}, width = #{canvasWidth}, updatetime = NOW() " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    void updateinfoByCanvasIdAndOpenId(
            @Param("canvasHeight") int canvasHeight,
            @Param("canvasWidth") int canvasWidth,
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据用户openid获取该用户的所有画布列表
     * @param openId 用户唯一标识
     * @return 画布信息列表
     */
    @Select("SELECT name, createtime, updatetime, height, width, canvasid, openid FROM canvas_info WHERE openid = #{openId}")
    List<CanvasInfo> selectCanvasListByOpenId(@Param("openId") String openId);

    @Select("SELECT DISTINCT canvasid FROM canvas_info")
    List<String> selectAllUniqueCanvasIds();

    @Select("SELECT angle, canvasid, createtime, updatetime, height, openid, src, type, width, x, y " +
            "FROM canvas_elements WHERE canvasid = #{canvasId}")
    List<Element> selectElementsByCanvasId(@Param("canvasId") String canvasId);

    /**
     * 根据canvasid和openid删除画布基本信息
     * 由于设置了级联删除，关联的元素将自动被删除
     *
     @param canvasId
     画布唯一标识
     *
     @param openId
     用户唯一标识
     *
     @return
     删除的记录数
     */
    @Delete("DELETE FROM canvas_info WHERE canvasid = #{canvasId} AND openid = #{openId}")
    int deleteCanvasInfoByCanvasIdAndOpenId(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId);

    /**
     * 根据canvasid和openid更新画布名称
     * @param canvasId 画布唯一标识
     * @param openId 用户唯一标识
     * @param name 新的画布名称
     * @return 更新的记录数
     */
    @Update("UPDATE canvas_info SET name = #{name}, updatetime = NOW() " +
            "WHERE canvasid = #{canvasId} AND openid = #{openId}")
    int updateCanvasName(
            @Param("canvasId") String canvasId,
            @Param("openId") String openId,
            @Param("name") String name);
}