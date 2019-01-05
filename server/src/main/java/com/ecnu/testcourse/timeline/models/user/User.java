package com.ecnu.testcourse.timeline.models.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xuyiyang
 */
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String email;
  private String username;
  private String password;
  private String image;

  public User(String email, String username, String password, String image) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.image = image;
  }

  public User() {

  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getImage() {
    return image;
  }

  public void update(String email, String username, String password, String image) {
    if (!"".equals(email)) {
      this.email = email;
    }
    if (!"".equals(username)) {
      this.username = username;
    }
    if (!"".equals(password)) {
      this.password = password;
    }
    if (!"".equals(image)) {
      this.image = image;
    }
  }
}
