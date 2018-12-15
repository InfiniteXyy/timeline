package com.ecnu.testcourse.timeline.api;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfileApi.class)
@RunWith(SpringRunner.class)
public class ProfileApiTest {

  private User user;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    user = new User("infinitex@infinitex.cn", "xyy", "123", "");
    when(userRepository.findByUsername(eq(user.getUsername())))
        .thenReturn(Optional.of(user));
  }

  @Test
  public void should_get_user_profile_success() throws Exception {
    // TODO: user better json tools
    mvc.perform(get("/api/profile/xyy"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string(equalTo("{\"user\":{\"image\":\"\",\"username\":\"xyy\"}}")));
  }
}