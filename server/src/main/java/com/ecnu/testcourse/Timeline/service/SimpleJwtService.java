package com.ecnu.testcourse.Timeline.service;

import com.ecnu.testcourse.Timeline.models.user.JwtService;
import com.ecnu.testcourse.Timeline.models.user.User;
import org.springframework.stereotype.Service;

@Service
public class SimpleJwtService implements JwtService {

  @Override
  public String toToken(User user) {
    return String.valueOf(user.getId());
  }

  @Override
  public Long toId(String token) {
    return Long.valueOf(token);
  }
}
