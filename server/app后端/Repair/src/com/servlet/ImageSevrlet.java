package com.servlet;

import com.db.DAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageSevrlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String param = request.getParameter("upload");
    String imageName = request.getParameter("imageName");
    String order = request.getParameter("order");
    
    System.out.println("order=" + order);
    if ((param != null) && (imageName != null) && (order != null) && (param.equals("image")))
    {
      InputStream in = request.getInputStream();
      int size = request.getContentLength();
      DAO.saveImage(order, imageName, request, size);
      


      System.out.println("size=" + size);
      
      PrintWriter out = response.getWriter();
      out.print(true);
      out.flush();
    }
    else
    {
      PrintWriter out = response.getWriter();
      out.print(false);
      out.flush();
    }
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
  
  private void receiveImage(HttpServletRequest request, String imageName, String order)
  {
    try
    {
      InputStream in = request.getInputStream();
      
      int size = request.getContentLength();
      
      byte[] result = new byte[size];
      

      int i = 0;
      while (i < size)
      {
        char ch = (char)in.read();
        result[i] = ((byte)ch);
        i++;
      }
      in.close();
      

      String folder = request.getRealPath("imgs");
      
      File fileFolder = new File(folder + "\\" + order);
      
      System.out.println(folder + "\\" + order);
      
      fileFolder.mkdir();
      
      String filePath = folder + "\\" + order + "\\" + imageName;
      File file = new File(filePath);
      FileOutputStream fos = new FileOutputStream(file);
      
      fos.write(result);
      
      fos.flush();
      fos.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
