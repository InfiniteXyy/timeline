package com.ecnu.testcourse.timeline.utils;

import java.util.HashMap;
import java.util.Map;

public class JsonHelper {

  public static Map<String, Object> object(String field, Object value) {
    return new HashMap<String, Object>() {{
      put(field, value);
    }};
  }
}
