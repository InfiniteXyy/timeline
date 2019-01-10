package com.ecnu.testcourse.timeline.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author xuyiyang
 */
public class ValidationHandler {

  public static Map<String, Object> serialize(BindingResult bindingResult) {
    return wrapErrorRoot(new HashMap<String, Object>(bindingResult.getFieldErrorCount() + 1) {{
      for (FieldError r : bindingResult.getFieldErrors()) {
        put(r.getField(), r.getDefaultMessage());
      }
    }});
  }

  public static Map<String, Object> wrapErrorRoot(Object o) {
    return new HashMap<String, Object>(2) {{
      put("errors", o);
    }};
  }
}
