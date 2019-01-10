package com.ecnu.testcourse.timeline.models.message;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xuyiyang
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

  /**
   * 取出从某个时刻开始的n条信息
   *
   * @param limit 信息数目
   * @param from 起始时间
   * @return 信息列表
   */
  @Query(
      value = "SELECT * from message where created_at < ?2 order by created_at DESC LIMIT ?1",
      nativeQuery = true)
  List<Message> findTopMessagesSince(int limit, String from);

  /**
   * 取出前n条信息
   *
   * @param limit 信息数目
   * @return 信息列表
   */
  @Query(value = "SELECT * from message order by created_at DESC LIMIT ?1", nativeQuery = true)
  List<Message> findTopMessages(int limit);
}
