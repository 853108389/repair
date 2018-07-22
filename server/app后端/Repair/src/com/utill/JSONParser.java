package com.utill;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class JSONParser
{
  public static String toJSONString(Object obj)
  {
    Gson gson = new Gson();
    return gson.toJson(obj);
  }
  
  public static <T> T toJavaBean(String jsonStr, Type type)
  {
    Gson gson = new Gson();
    return gson.fromJson(jsonStr, type);
  }
}
