package com.utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamParser
{
  public static String parseInputStream(InputStream is)
  {
    String result = "";
    InputStreamReader isr = null;
    try
    {
      isr = new InputStreamReader(is, "utf-8");
      BufferedReader br = new BufferedReader(isr);
      
      String line = br.readLine();
      while (line != null)
      {
        result = result + line;
        line = br.readLine();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
      try
      {
        assert (isr != null);
        isr.close();
      }
      catch (IOException e2)
      {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        assert (isr != null);
        isr.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return result;
  }
}
