package com.ecnu.testcourse.timeline.utils;

import static com.ecnu.testcourse.timeline.utils.ThrowableCapter.thrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.security.InvalidKeyException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EncryptTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

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
  public void should_throw_error_if_key_is_not_24bits() throws Exception {
    String password = "123";
    String initVector = "Bar12345Bar12345";
    String key = "Timeline";

    Throwable actual = thrownBy(() -> Encrypt.encrypt(key, initVector, password));
    assertNotNull(actual);
    assertTrue(actual instanceof InvalidKeyException);
    assertThat(actual.getMessage(), equalTo("Invalid AES key length: 8 bytes"));
  }
}