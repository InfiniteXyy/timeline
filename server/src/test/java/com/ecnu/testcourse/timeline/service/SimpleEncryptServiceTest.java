package com.ecnu.testcourse.timeline.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SimpleEncryptServiceTest {

  private SimpleEncryptService encryptService;

  @Before
  public void setUp() throws Exception {
    encryptService = new SimpleEncryptService();
  }

  @Test
  public void should_password_the_same_after_encrypt_and_decrypt() throws Exception {
    String password = "Hello World";
    String secret = encryptService.encrypt(password);
    boolean same = encryptService.check(password, secret);
    assertTrue(same);
  }

}