package com.example.back.mapper;

import com.example.back.entity.Admin;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE openid = #{openid}")
    Admin findByOpenid(String openid);
} 