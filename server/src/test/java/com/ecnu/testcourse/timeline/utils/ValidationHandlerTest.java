package com.ecnu.testcourse.timeline.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;

public class ValidationHandlerTest {

  @Test
  public void should_serialize_correct() throws Exception {
    BindingResult bindingResult = new DirectFieldBindingResult("", "");
    bindingResult.addError(new FieldError("", "test1", "message1"));
    bindingResult.addError(new FieldError("", "test2", "message2"));

    Map<String, Object> real = ValidationHandler.serialize(bindingResult);

    Map<String, Object> actual = new HashMap<String, Object>() {{
      put("errors", new HashMap<String, Object>() {{
        put("test1", "message1");
        put("test2", "message2");
      }});
    }};

    assertThat(real, equalTo(actual));
  }

  @Test
  public void should_wrap_root_with_errors() throws Exception {
    Map<String, Object> map = new HashMap<String, Object>() {{
      put("sad", "test");
    }};

    Map<String, Object> real = ValidationHandler.wrapErrorRoot(map);

    Map<String, Object> actual = new HashMap<String, Object>() {{
      put("errors", map);
    }};

    assertThat(real, equalTo(actual));
  }
}