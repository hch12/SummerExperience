package com.example.back.service;
import com.example.back.mapper.canvasMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class canvasServiceImpl implements canvasService{
    @Resource
    canvasMapper canvasMapper;
}
