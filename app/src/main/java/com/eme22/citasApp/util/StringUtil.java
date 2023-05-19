package com.eme22.citasApp.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class StringUtil {

    public static String encodeToBase64(String text) {
        byte[] data = text.getBytes();
        byte[] base64 = Base64.encode(data, Base64.DEFAULT);
        return new String(base64);
    }

}
