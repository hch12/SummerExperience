package com.example.back.mapper;

import com.example.back.entity.Element;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface canvasMapper {
    @Insert("INSERT INTO canvas_info (createtime, height , width , canvasid , openid) " +
            "VALUES ( NOW(), #{height}, #{width}, #{canvasid} , #{openid})")
    void insertCanvas(
            @Param("height") int height,
            @Param("width") int width,
            @Param("canvasid") String canvasid,
            @Param("openid") String openid);
    @Insert("INSERT INTO canvas_elements (angle, canvasid, createtime, height, openid, src, width, x, y) " +
            "VALUES ( #{angle}, #{canvasid}, NOW(), #{height}, #{openid}, #{src}, #{width}, #{x}, #{y})")
    void insertElement(Element element);
}
