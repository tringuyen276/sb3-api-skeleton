package com.digiex.utility.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String encode(String password) {
    return encoder.encode(password);
  }

  public static boolean comapareHash(String password, String hash) {
    return encoder.matches(password, hash);
  }
}
