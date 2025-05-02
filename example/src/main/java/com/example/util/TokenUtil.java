package com.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    private static Map<String, String> tokenMap = new HashMap<>();

    public static String generateToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenMap.put(token, username);
        return token;
    }

    public static String getUsername(String token) {
        return tokenMap.get(token);
    }
}
