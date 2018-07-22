package com.servlet;

import com.db.DAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/ShowImageServlet"})
public class ShowImageServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = request.getParameter("path");
    
    System.out.println("path=" + path);
    InputStream in = DAO.getImage(path);
    byte[] b = new byte[in.available()];
    System.out.println(in.available());
    in.read(b);
    

    OutputStream out = response.getOutputStream();
    
    out.write(b);
    out.flush();
    out.close();
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
}
