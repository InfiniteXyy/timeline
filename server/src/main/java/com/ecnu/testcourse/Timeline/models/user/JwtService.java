package com.ecnu.testcourse.Timeline.models.user;

public interface JwtService {

  String toToken(User user);

  Long toId(String token);

}
