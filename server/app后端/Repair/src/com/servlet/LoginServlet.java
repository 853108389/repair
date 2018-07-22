package com.servlet;

import com.db.DAO;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet
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
    if ((userName != null) && (password != null))
    {
      boolean success = DAO.login(userName, password);
      
      PrintWriter out = response.getWriter();
      request.getSession().setAttribute("userName", userName);
    

//      Gson gson = new Gson();
//      String jsonStr = gson.toJson(new HashMap<String,String>().put("message","true"));
      String aString = "{\"message\": \"" + success + "\"}";
     out.print(aString);
    }
  }
}
