package com.ecnu.testcourse.timeline.utils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class JsonHelperTest {

  @Test
  public void should_json_object_be_correct() throws Exception {
    Map<String, Object> actual = new HashMap<String, Object>() {{
      put("happy", "world");
    }};
    Map<String, Object> real = JsonHelper.object("happy", "world");
    assertThat(real, equalTo(actual));
  }
}