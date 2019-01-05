package com.ecnu.testcourse.timeline.models.message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.joda.time.DateTime;

/**
 * @author xuyiyang
 */
@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String body;
  private String createdAt;
  private String imageUrl;
  private String updatedAt;
  private long userId;


  public Message() {
  }

  public Message(String body, String imageUrl, long userId) {
    this.createdAt = new DateTime().toString();
    this.body = body;
    this.imageUrl = imageUrl;
    this.userId = userId;
  }

  public long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public long getUserId() {
    return userId;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
