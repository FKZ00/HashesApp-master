package com.example.hashesapp.utils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public final class FileUtils {
  public static byte[] readFileFromFileInputStream(InputStream inputStream, FormDataContentDisposition fileMetaData) {
    if (inputStream == null) {
      throw new NullPointerException();
    }
    byte[] image = new byte[8192];
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      image = outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }
}
