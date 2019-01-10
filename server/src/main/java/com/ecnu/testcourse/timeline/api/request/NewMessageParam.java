package com.ecnu.testcourse.timeline.api.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * @author xuyiyang
 */
@JsonRootName("message")
public class NewMessageParam {

  @NotBlank(message = "can't be empty")
  private String body;


  @URL(message = "should be url")
  private String imageUrl = "";

  public String getBody() {
    return body;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
