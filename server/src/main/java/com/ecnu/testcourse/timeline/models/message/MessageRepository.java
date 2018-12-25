package com.ecnu.testcourse.timeline.models.message;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

  @Query(
      value = "SELECT * from message where created_at < ?2 order by created_at DESC LIMIT ?1",
      nativeQuery = true)
  List<Message> findTopMessagesSince(int limit, String from);

  @Query(value = "SELECT * from message order by created_at DESC LIMIT ?1", nativeQuery = true)
  List<Message> findTopMessages(int limit);
}
