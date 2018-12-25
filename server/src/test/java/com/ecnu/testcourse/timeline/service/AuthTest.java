package com.ecnu.testcourse.timeline.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ecnu.testcourse.timeline.ThrowableCapter;
import com.ecnu.testcourse.timeline.models.user.JwtService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class AuthTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtService jwtService;

  private Auth auth;

  private User user;

  @Before
  public void setUp() throws Exception {
    jwtService = mock(JwtService.class);
    userRepository = mock(UserRepository.class);
    user = new User("x@x.x", "x", "1", "");
    auth = new Auth(jwtService, userRepository);
  }

  @Test
  public void should_authorize_correct() throws Exception {
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(jwtService.toValue(anyString())).thenReturn(String.valueOf(user.getId()));

    User actual = auth.authorize("bearer token");
    assertEquals(user, actual);
  }

  @Test
  public void should_authorize_fail_if_no_user() throws Exception {
    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
    when(jwtService.toValue(anyString())).thenReturn(String.valueOf(user.getId()));

    Throwable throwable = ThrowableCapter.thrownBy(() -> auth.authorize("bearer token"));
    assertNotNull(throwable);
    assertEquals("no user", throwable.getMessage());
  }

  @Test
  public void should_authorize_fail_if_wrong_token() throws Exception {
    when(jwtService.toValue(anyString())).thenReturn(null);

    Throwable throwable = ThrowableCapter.thrownBy(() -> auth.authorize("bearer token"));
    assertNotNull(throwable);
    assertEquals("wrong token", throwable.getMessage());
  }
}