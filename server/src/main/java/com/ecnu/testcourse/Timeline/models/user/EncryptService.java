package com.ecnu.testcourse.Timeline.models.user;

public interface EncryptService {

  String encrypt(String password);

  boolean check(String checkPassword, String realPassword);
}
