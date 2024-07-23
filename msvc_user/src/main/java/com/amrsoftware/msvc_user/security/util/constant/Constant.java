package com.amrsoftware.msvc_user.security.util.constant;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class Constant {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final String PREFIX_TOKEN = "Bearer ";
}
