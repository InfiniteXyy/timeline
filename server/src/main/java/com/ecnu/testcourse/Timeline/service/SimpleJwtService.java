package com.ecnu.testcourse.Timeline.service;

import com.ecnu.testcourse.Timeline.models.user.JwtService;
import com.ecnu.testcourse.Timeline.models.user.User;
import com.ecnu.testcourse.Timeline.utils.Encrypt;
import org.springframework.stereotype.Service;

@Service
public class SimpleJwtService implements JwtService {

  private String initVector = "Bar12345Bar12345";
  private String key = "tokentokentokenx";

  @Override
  public String toToken(User user) {
//    String value = user.getId() + " " + new DateTime().toString();
    String value = String.valueOf(user.getId());
    return Encrypt.encrypt(key, initVector, value);
  }

  @Override
  public String toValue(String token) {
    return Encrypt.decrypt(key, initVector, token);
  }
}
