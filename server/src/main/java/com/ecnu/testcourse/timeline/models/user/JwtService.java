package com.ecnu.testcourse.timeline.models.user;

/**
 * @author xuyiyang
 */
public interface JwtService {

  /**
   * 根据数据库用户生成token
   *
   * @param user 用户
   * @return 生成的token
   */
  String toToken(User user);

  /**
   * 将token转为用户ID
   *
   * @param token 输入的token
   * @return 与token相符合的ID
   */
  String toValue(String token);

}
