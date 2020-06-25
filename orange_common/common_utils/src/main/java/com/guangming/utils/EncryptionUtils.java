package com.guangming.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class EncryptionUtils {
    private static final String SECURE = "orangeWangke.cn";

    /**
     * Base64 encode
     */
    public static String base64Encode(String data) {
        return Base64.encodeBase64String(data.getBytes());
    }

    /**
     * Base64 decode
     */
    public static String base64Decode(String data) {

        return new String(Base64.decodeBase64(data.getBytes()), StandardCharsets.UTF_8);
    }

    /**
     * md5
     */
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(SECURE + data);
    }

    /**
     * sha256
     */
    public static String sha256Hex(String data) {
        return DigestUtils.sha256Hex(SECURE + data);
    }

}