package com.ecnu.testcourse.timeline.api.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;

/**
 * @author xuyiyang
 */
@JsonRootName("user")
public class UpdateUserParam {

  @Email(message = "should be an email")
  private String email = "";
  private String password = "";
  private String username = "";
  private String image = "";

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public String getImage() {
    return image;
  }
}
