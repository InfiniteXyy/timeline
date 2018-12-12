package com.ecnu.testcourse.Timeline.models.user;

public interface JwtService {

  String toToken(User user);

  String toValue(String token);

}
