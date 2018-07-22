package com.servlet;

import com.db.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");
    
    System.out.println(userName + "," + password);
    if ((userName != null) && (password != null))
    {
      boolean success = DAO.register(userName, password);
      
      PrintWriter out = response.getWriter();
      String aString = "{\"message\": \"" + success + "\"}";
      out.print(aString);
    }
  }
}
