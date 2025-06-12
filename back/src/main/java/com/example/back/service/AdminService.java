package com.example.back.service;

import com.example.back.entity.Admin;
import java.util.Map;

public interface AdminService {
    boolean checkAdmin(String openid);
    Map<String, String[]> getAllImages();
} 