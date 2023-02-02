package com.me;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {

    @Test
    public void xx() {
        var dt = "appIsbn=txpbspapi&timestamp=1671006887&nonce=qNDOaeCcOU&series=54c2f5af78974208b23654ba88b9631d&appsecret=gSttavYmFRXXVGYJsXq";
        var pwd = encryptSHA1(dt);
        System.out.println(pwd);
    }

    public static String encryptSHA1(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);

                if (shaHex.length() < 2) {
                    hexString.append(0);
                }

                hexString.append(shaHex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void dtt() {
        Integer x = 5;
        System.out.println(gt(x));
    }

    public double gt(double g) {
        return g/2;
    }
}

