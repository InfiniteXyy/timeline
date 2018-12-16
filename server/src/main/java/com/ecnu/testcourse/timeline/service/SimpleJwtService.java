package com.ecnu.testcourse.timeline.service;

import com.ecnu.testcourse.timeline.models.user.JwtService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.utils.Encrypt;
import org.springframework.stereotype.Service;

@Service
public class SimpleJwtService implements JwtService {

  private String initVector = "Bar12345Bar12345";
  private String key = "tokentokentokenx";

  @Override
  public String toToken(User user) {
//    String value = user.getId() + " " + new DateTime().toString();
    String value = String.valueOf(user.getId());
    try {
      return Encrypt.encrypt(key, initVector, value);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String toValue(String token) {
    try {
      return Encrypt.decrypt(key, initVector, token);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
