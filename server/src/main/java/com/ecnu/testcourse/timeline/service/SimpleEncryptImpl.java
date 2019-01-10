package com.ecnu.testcourse.timeline.service;

import com.ecnu.testcourse.timeline.models.user.EncryptService;
import com.ecnu.testcourse.timeline.utils.Encrypt;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

/**
 * @author xuyiyang
 */
@Service
public class SimpleEncryptImpl implements EncryptService {

  private String initVector = "Bar12345Bar12345";
  private String key = "Timelinexyyxyyxy";
  IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
  SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

  @Override
  public String encrypt(String value) {
    return Encrypt.encrypt(key, initVector, value);
  }


  @Override
  public boolean check(String checkPassword, String realPassword) {
    return checkPassword.equals(Encrypt.decrypt(key, initVector, realPassword));
  }

}
