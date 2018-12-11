package com.ecnu.testcourse.Timeline.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationHandler {

  public static Map<String, Object> serialize(BindingResult bindingResult) {
    return wrapRoot(new ArrayList<Object>() {{
      for (FieldError r : bindingResult.getFieldErrors()) {
        add(new HashMap<String, Object>() {{
          put(r.getField(), r.getDefaultMessage());
        }});
      }
    }});
  }

  public static Map<String, Object> wrapRoot(Object o) {
    return new HashMap<String, Object>() {{
      put("errors", o);
    }};
  }
}
