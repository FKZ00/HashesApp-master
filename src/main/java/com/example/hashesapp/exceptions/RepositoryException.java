package com.example.hashesapp.exceptions;

import lombok.ToString;

@ToString
public class RepositoryException extends Exception {
  public static final String REPOSITORY_ADD_EXCEPTION = "Invalid attempt to add hash to database.";
  public static final String REPOSITORY_REMOVE_EXCEPTION = "Invalid attempt to remove hash from database.";

  public RepositoryException(String message) {
    super(message);
  }
}
