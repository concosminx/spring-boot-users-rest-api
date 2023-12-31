package com.practica.restdemo.utils;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utils {

  private final Random RANDOM = new SecureRandom();
  private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public String generateUserId(int length) {
    return generateRandomString(length);
  }

  public String generateAddressId(int length) {
    return generateRandomString(length);
  }

  private String generateRandomString(int length) {
    StringBuilder returnValue = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }
    return new String(returnValue);
  }
}
