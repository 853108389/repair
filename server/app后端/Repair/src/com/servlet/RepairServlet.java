package com.servlet;

import com.db.DAO;
import com.google.gson.Gson;
import entry.RepairRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RepairServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean success = false;
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		ServletInputStream instream = request.getInputStream();
		RepairRecord record = new RepairRecord();
		if(request.getParameter("thing")!=null){
			record.setThing(request.getParameter("thing"));
		}
		if(request.getParameter("describe")!=null){
			record.setDescribe(request.getParameter("describe"));
		}
		if(request.getParameter("address")!=null){
			record.setAddress(request.getParameter("address"));
		}
		if(request.getParameter("name")!=null){
			record.setName(request.getParameter("name"));
		}
		if(request.getParameter("tel")!=null){
			record.setTel(request.getParameter("tel"));
		}
		if(request.getParameter("longitude")!=null){
			record.setLongitude(request.getParameter("longitude"));
		}
		if(request.getParameter("latitude")!=null){
			record.setLatitude(request.getParameter("latitude"));
		}
		if(request.getParameter("thing")!=null){
			record.setThing(request.getParameter("thing"));
		}
		if(request.getParameter("username")!=null){
			record.setUsername(request.getParameter("username"));
		}else{
			record.setUsername("error");
		}
		
		if(request.getParameter("time")!=null){
			record.setTime(request.getParameter("time"));
		}
//		else{
//			record.setUsername(request.getSession().getAttribute("userName").toString());
//		}
		if(request.getParameter("order")!=null){
			record.setOrder(request.getParameter("order"));
		}
		success = DAO.repair(record);
		System.out.println(success);
		
		out.print(success);
	
	}
}
