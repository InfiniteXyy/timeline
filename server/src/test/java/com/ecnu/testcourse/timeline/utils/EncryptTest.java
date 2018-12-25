package com.ecnu.testcourse.timeline.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EncryptTest {

  @Test
  public void should_be_same_after_encrypt_decrypt() throws Exception {
    String password = "123";
    String initVector = "Bar12345Bar12345";
    String key = "Timelinexyyxyyxy";

    String secret = Encrypt.encrypt(key, initVector, password);
    String real = Encrypt.decrypt(key, initVector, secret);

    assertEquals(password, real);
  }

  @Test
  public void should_encrypt_return_null_if_key_is_not_24bits() throws Exception {
    String password = "123";
    String initVector = "Bar12345Bar12345";
    String key = "Timeline";

    String real = Encrypt.encrypt(key, initVector, password);
    assertNull(real);
  }

  @Test
  public void should_decrypt_return_null_with_wrong_key() throws Exception {
    String password = "123";
    String initVector = "Bar12345Bar12345";
    String key = "Timelinexyyxyyxy";
    String wrongKey = "key";

    String actual = Encrypt.encrypt(key, initVector, password);
    String real = Encrypt.decrypt(wrongKey, initVector, actual);

    assertNull(real);
  }

}