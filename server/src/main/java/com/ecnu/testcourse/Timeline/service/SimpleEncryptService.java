package com.ecnu.testcourse.Timeline.service;

import com.ecnu.testcourse.Timeline.models.user.EncryptService;
import org.springframework.stereotype.Service;

@Service
public class SimpleEncryptService implements EncryptService {

  @Override
  public String encrypt(String password) {
    return password;
  }

  @Override
  public boolean check(String checkPassword, String realPassword) {
    return checkPassword.equals(realPassword);
  }
}
