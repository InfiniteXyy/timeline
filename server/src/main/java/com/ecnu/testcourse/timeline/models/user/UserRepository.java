package com.ecnu.testcourse.timeline.models.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xuyiyang
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * 根据邮箱查找用户
   *
   * @param email 用户邮箱
   * @return OptionUser
   */
  Optional<User> findByEmail(String email);

  /**
   * 根据id查找用户
   *
   * @param id 用户id
   * @return OptionUser
   */
  @Override
  Optional<User> findById(Long id);

  /**
   * 根据用户名查找用户
   *
   * @param username 用户名
   * @return OptionUser
   */
  Optional<User> findByUsername(String username);


  /**
   * 根据id列表返回用户列表
   *
   * @param ids id列表
   * @return 用户列表
   */
  List<User> findByIdIn(List<Long> ids);

}
