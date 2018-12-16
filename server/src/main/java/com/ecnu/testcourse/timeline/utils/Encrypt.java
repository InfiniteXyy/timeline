package com.ecnu.testcourse.timeline.utils;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

public class Encrypt {

  public static String encrypt(String key, String initVector, String value) throws Exception {
    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

    byte[] encrypted = cipher.doFinal(value.getBytes());

    return Base64.encodeBase64String(encrypted);
  }

  public static String decrypt(String key, String initVector, String encrypted) throws Exception {
    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

    byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

    return new String(original);

  }
}
