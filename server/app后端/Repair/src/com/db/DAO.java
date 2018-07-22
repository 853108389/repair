package com.db;

import entry.RepairRecord;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class DAO
{
  public static boolean register(String userName, String password)
  {
    boolean success = false;
    Connection conn = DBLinker.getConnection();
    
    String query = "select password from user where userName='" + userName + "'";
    String sql = "insert into user(userName,password)values('" + userName + "','" + password + "')";
    try
    {
      Statement state = conn.createStatement();
      ResultSet set = state.executeQuery(query);
      if (!set.next())
      {
        state.execute(sql);
        success = true;
      }
      else
      {
        success = false;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return success;
  }
  
  public static boolean login(String userName, String password)
  {
    boolean success = false;
    Connection conn = DBLinker.getConnection();
    
    String sql = "select password from user where userName='" + userName + "'";
    try
    {
      Statement state = conn.createStatement();
      ResultSet set = state.executeQuery(sql);
      if (!set.next())
      {
        success = false;
      }
      else
      {
        String tempPwd = set.getString("password");
        if (tempPwd.equals(password)) {
          success = true;
        } else {
          success = false;
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return success;
  }
  
  public static boolean repair(RepairRecord record)
  {
    boolean success = false;
    Connection conn = DBLinker.getConnection();
    
    String sql = "insert into repair_info(longitude,latitude,orders,thing,time,describes,state,name,address,tel,img_file_id,userName)values(?,?,?,?,?,?,?,?,?,?,?,?)";
    try
    {
      PreparedStatement state = conn.prepareStatement(sql);
      state.setString(1, record.getLongitude());
      state.setString(2, record.getLatitude());
      state.setString(3, record.getOrder());
      state.setString(4, record.getThing());
      state.setString(5, record.getTime());
      state.setString(6, record.getDescribe());
      state.setString(7, "Î´Î¬ÐÞ");
      state.setString(8, record.getName());
      state.setString(9, record.getAddress());
      state.setString(10, record.getTel());
      state.setString(11, record.getOrder());
      state.setString(12, record.getUsername());
      
      success = !state.execute();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return success;
  }
  
  public static ArrayList<HashMap<String, String>> getRepairInfo(String userName)
  {
    ArrayList<HashMap<String, String>> data = new ArrayList();
    
    Connection conn = DBLinker.getConnection();
    String sql = "select * from repair_info where userName='" + userName + "'";
    try
    {
      Statement state = conn.createStatement();
      ResultSet set = state.executeQuery(sql);
      while (set.next())
      {
        HashMap<String, String> item = new HashMap();
        item.put("thing", set.getString("thing"));
        item.put("describes", set.getString("describes"));
        item.put("address", set.getString("address"));
        item.put("name", set.getString("name"));
        item.put("tel", set.getString("tel"));
        item.put("time", set.getString("time"));
        item.put("orders", set.getString("orders"));
        item.put("state", set.getString("state"));
        
        data.add(item);
      }
      set.close();
      state.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return data;
  }
  
  public static ArrayList<HashMap<String, String>> getOrders(String orderId)
  {
    ArrayList<HashMap<String, String>> data = new ArrayList();
    
    Connection conn = DBLinker.getConnection();
    String sql = "select * from repair_info where orders='" + orderId + "'";
    try
    {
      Statement state = conn.createStatement();
      ResultSet set = state.executeQuery(sql);
      while (set.next())
      {
        HashMap<String, String> item = new HashMap();
        item.put("thing", set.getString("thing"));
        item.put("describes", set.getString("describes"));
        item.put("address", set.getString("address"));
        item.put("name", set.getString("name"));
        item.put("tel", set.getString("tel"));
        item.put("time", set.getString("time"));
        item.put("orders", set.getString("orders"));
        item.put("state", set.getString("state"));
        
        data.add(item);
      }
      set.close();
      state.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return data;
  }
  
  
  
  public static boolean saveImagePath(String order, String imageName)
  {
    boolean success = false;
    Connection conn = DBLinker.getConnection();
    
    String imagePath = order + "\\" + imageName;
    System.out.println(imagePath);
    String sql = "insert into image_paths(img_file_id,img_path)values('" + order + "','" + imagePath + "')";
    try
    {
      Statement state = conn.createStatement();
      success = !state.execute(sql);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return success;
  }
  
  public static ArrayList<String> getImgFiles(String imgFileId)
  {
    ArrayList<String> imgFiles = new ArrayList();
    
    Connection conn = DBLinker.getConnection();
    
    String sql = "select img_path from image_paths where img_file_id='" + imgFileId + "'";
    Statement state = null;
    ResultSet set = null;
    try
    {
      state = conn.createStatement();
      set = state.executeQuery(sql);
      while (set.next())
      {
        String imgPath = set.getString("img_path");
        imgFiles.add(imgPath);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      try
      {
        set.close();
        state.close();
      }
      catch (SQLException e2)
      {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        set.close();
        state.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    return imgFiles;
  }
  
  public static boolean saveImage(String order, String imageName, HttpServletRequest request, int size)
  {
    boolean success = false;
    Connection conn = DBLinker.getConnection();
    
    String imagePath = order + "/" + imageName;
    
    String sql = "insert into image_paths(img_file_id,img_path,image)values(?,?,?)";
    try
    {
      PreparedStatement state = conn.prepareStatement(sql);
      state.setString(1, order);
      state.setString(2, imagePath);
      state.setBinaryStream(3, request.getInputStream(), size);
      
      state.execute();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return success;
  }
  
  public static InputStream getImage(String path)
  {
    Connection conn = DBLinker.getConnection();
    String sql = "select image from image_paths where img_path='" + path + "'";
    InputStream in = null;
    try
    {
      Statement state = conn.createStatement();
      ResultSet set = state.executeQuery(sql);
      while (set.next()) {
        in = set.getBinaryStream("image");
      }
      System.out.println(in.available());
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return in;
  }
}
