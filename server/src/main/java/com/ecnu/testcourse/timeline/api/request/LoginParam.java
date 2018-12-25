package com.ecnu.testcourse.timeline.api.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonRootName("user")
public class LoginParam {

  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  private String email;
  @NotBlank(message = "can't be empty")
  private String password;

  public LoginParam() {
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
