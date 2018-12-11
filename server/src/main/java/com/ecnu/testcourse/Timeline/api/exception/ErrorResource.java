package com.ecnu.testcourse.Timeline.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonSerialize(using = ErrorResourceSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("errors")
public class ErrorResource {
  private List<FieldErrorResource> fieldErrors;

  public ErrorResource(List<FieldErrorResource> fieldErrorResources) {
    this.fieldErrors = fieldErrorResources;
  }

  public List<FieldErrorResource> getFieldErrors() {
    return fieldErrors;
  }
}
