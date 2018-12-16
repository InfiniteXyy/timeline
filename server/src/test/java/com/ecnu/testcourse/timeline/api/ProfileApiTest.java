package com.ecnu.testcourse.timeline.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
  public void should_get_user_profile_success() throws Exception {
    User user = new User("infinitex@infinitex.cn", "xyy", "123",
        "http://cdn.infinitex.cn/images/pikachu.jpg");
    when(userRepository.findByUsername(eq(user.getUsername())))
        .thenReturn(Optional.of(user));

    when().get("/api/profile/{username}", user.getUsername())
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("user.username", equalTo(user.getUsername()))
        .body("user.image", equalTo(user.getImage()));
  }

  @Test
  public void should_get_404_if_use_wrong_username() throws Exception {
    String wrongUsername = "zyy";
    when(userRepository.findByUsername(eq(wrongUsername)))
        .thenReturn(Optional.empty());

    when().get("/api/profile/{username}", wrongUsername)
        .prettyPeek()
        .then()
        .statusCode(404)
        .body("errors.username", equalTo("not found"));

  }
}