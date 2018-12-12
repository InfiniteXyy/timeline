package com.ecnu.testcourse.timeline.models.user;

public interface JwtService {

  String toToken(User user);

  String toValue(String token);

}
