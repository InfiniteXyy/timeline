package com.ecnu.testcourse.timeline.models.user;

/**
 * @author xuyiyang
 */
public interface EncryptService {

  /**
   * 密码加密
   *
   * @param password 待加密的密码
   * @return 加密过后的字符串
   */
  String encrypt(String password);


  /**
   * 检测密码与加密过后的字符串是否一致
   *
   * @param checkPassword 用户输入的密码
   * @param realPassword 加密过后的字符串
   * @return 是否一致
   */
  boolean check(String checkPassword, String realPassword);
}
