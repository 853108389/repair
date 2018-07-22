package com.utill;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamPaser
{
  public static byte[] readInputStream(InputStream inputStream)
    throws Exception
  {
    byte[] buffer = new byte[1024];
    int len = -1;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    while ((len = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, len);
    }
    outputStream.close();
    inputStream.close();
    return outputStream.toByteArray();
  }
}
