package com.ecnu.testcourse.timeline.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SimpleEncryptImplTest {

  private SimpleEncryptImpl encryptService;

  @Before
  public void setUp() {
    encryptService = new SimpleEncryptImpl();
  }

  @Test
  public void should_password_the_same_after_encrypt_and_decrypt() throws Exception {
    String password = "Hello World";
    String secret = encryptService.encrypt(password);
    boolean same = encryptService.check(password, secret);
    assertTrue(same);
  }

  @Test
  public void should_return_null_encrypt_with_wrong_input() throws Exception {
    String password = null;

    String result = encryptService.encrypt(password);
    assertNull(result);
  }

}