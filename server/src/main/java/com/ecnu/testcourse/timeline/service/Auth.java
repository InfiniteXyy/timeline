package com.ecnu.testcourse.timeline.service;

import com.ecnu.testcourse.timeline.models.user.JwtService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuyiyang
 */
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
      throw new Exception("wrong token");
    }
    Long id = Long.valueOf(value);
    Optional<User> optionalUser = userRepository.findById(id);
    // 应该给token加上时间
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new Exception("no user");
    }
  }
}
