
<%@ page language="java" contentType="text/html"  pageEncoding="UTF-8" %>
<%
if(session.getAttribute("userName")==null){
//用户没有登陆
response.sendRedirect("index.html");}
%>