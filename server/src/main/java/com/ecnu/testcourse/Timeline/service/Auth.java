package com.ecnu.testcourse.Timeline.service;

import com.ecnu.testcourse.Timeline.models.user.JwtService;
import com.ecnu.testcourse.Timeline.models.user.User;
import com.ecnu.testcourse.Timeline.models.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Auth {

  private JwtService jwtService;
  private UserRepository userRepository;

  @Autowired
  public Auth(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  public User authorize(String token) throws Exception {
    token = token.split(" ")[1];
    String value = jwtService.toValue(token);
    if (value == null) {
      throw new Exception();
    }
    Long id = Long.valueOf(value);
    Optional<User> optionalUser = userRepository.findById(id);
    // TODO: 为 token 加上时限
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new Exception();
    }
  }
}
