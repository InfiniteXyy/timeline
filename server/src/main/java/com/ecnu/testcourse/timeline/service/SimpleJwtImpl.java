package com.ecnu.testcourse.timeline.service;

import com.ecnu.testcourse.timeline.models.user.JwtService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.utils.Encrypt;
import org.springframework.stereotype.Service;

/**
 * @author xuyiyang
 */
@Service
public class SimpleJwtImpl implements JwtService {

  private String initVector = "Bar12345Bar12345";
  private String key = "tokentokentokenx";

  @Override
  public String toToken(User user) {
    String value = String.valueOf(user.getId());
    return Encrypt.encrypt(key, initVector, value);
  }

  @Override
  public String toValue(String token) {
    return Encrypt.decrypt(key, initVector, token);
  }
}
