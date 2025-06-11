package com.example.back.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1)) // 1天后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥, HMAC256算法加密
    }
}

