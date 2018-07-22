package com.servlet;

import com.db.DAO;
import com.utill.JSONParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HistoryServlet
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
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    
    PrintWriter out = response.getWriter();
    
    String userName = request.getParameter("username");
    if (userName != null)
    {
      ArrayList<HashMap<String, String>> data = DAO.getRepairInfo(userName);
      String jsonString = JSONParser.toJSONString(data);
      out.print(jsonString);
      System.out.println(jsonString);
    }
  }
}
