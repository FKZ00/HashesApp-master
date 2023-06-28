package com.example.hashesapp.exceptions;

public class HashException extends Exception {
  public static final String HASH_NOT_FOUND_EXCEPTION = "Hash not found in database";

  public HashException(String message) {
    super(message);
  }
}
