package com.hapjuhasil.server.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Getter
public class ValueConfig {

    @Value("${jwt.security.secret}")
    private String secretKey;

//    @Value("${jwt.APPLE_URL}")
//    private String appleUri;

    @Value("${jwt.security.ACCESS_TOKEN_EXPIRED}")
    private Long accessTokenExpired;

    @Value("${jwt.security.REFRESH_TOKEN_EXPIRED}")
    private Long refreshTokenExpired;

    private final String IOS_FORCE_UPDATE_VERSION = "0.0.9";
    private final String IOS_APP_VERSION = "1.0.0";
    private final String ANDROID_FORCE_UPDATE_VERSION = "0.0.9";
    private final String ANDROID_APP_VERSION = "1.0.0";
    private final String NOTIFICATION_TITLE = "알림 제목";
    private final String NOTIFICATION_CONTENT = "알림 내용";
    private final String TOKEN_VALUE_DELIMITER = "\\.";
    private final String BEARER_HEADER = "Bearer ";
    private final String BLANK = "";
    private final String MODULUS = "n";
    private final String EXPONENT = "e";
    private final String KID_HEADER_KEY = "kid";
    private final String ALG_HEADER_KEY = "alg";
    private final String RSA = "RSA";
    private final String KEY = "keys";
    private final String ID = "sub";
    private final int QUOTES = 1;
    private final int POSITIVE_NUMBER = 1;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}