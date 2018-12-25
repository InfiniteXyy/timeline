package com.ecnu.testcourse.timeline.api.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotBlank;

@JsonRootName("message")
public class NewMessageParam {

  @NotBlank(message = "can't be empty")
  private String body;

  public String getBody() {
    return body;
  }
}
