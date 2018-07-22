package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBLinker
{
  private static final String sqlName = "campus_repair";
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/campus_repair?useUnicode=true&characterEncoding=UTF-8";
  private static final String USER = "stitp";
  private static final String PASSWORD = "lin@2014";
  private static Connection connection;
  
  static
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      
      connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/campus_repair?useUnicode=true&characterEncoding=UTF-8", "stitp", "lin@2014");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public static Connection getConnection()
  {
    return connection;
  }
}
