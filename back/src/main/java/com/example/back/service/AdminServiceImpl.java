package com.example.back.service;

import com.example.back.entity.Admin;
import com.example.back.mapper.AdminMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Value("${image.base-url}")
    private String baseUrl;

    @Value("${image.directory}")
    private String imageDirectory;

    @Override
    public boolean checkAdmin(String openid) {
        Admin admin = adminMapper.findByOpenid(openid);
        return admin != null && "active".equals(admin.getStatus());
    }

    @Override
    public Map<String, String[]> getAllImages() {
        Map<String, String[]> result = new HashMap<>();
        File imagesDir = new File(imageDirectory);

        if (!imagesDir.exists() || !imagesDir.isDirectory()) {
            return result;
        }

        File[] folders = imagesDir.listFiles(File::isDirectory);
        if (folders != null) {
            for (File folder : folders) {
                String folderName = folder.getName();
                File[] files = folder.listFiles((dir, name) -> 
                    name.toLowerCase().endsWith(".png") ||
                    name.toLowerCase().endsWith(".jpg") ||
                    name.toLowerCase().endsWith(".jpeg") ||
                    name.toLowerCase().endsWith(".gif") ||
                    name.toLowerCase().endsWith(".webp")
                );

                if (files != null) {
                    String[] imageUrls = new String[files.length];
                    for (int i = 0; i < files.length; i++) {
                        imageUrls[i] = baseUrl + "/" + folderName + "/" + files[i].getName();
                    }
                    result.put(folderName, imageUrls);
                }
            }
        }

        return result;
    }
} 