<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="GB18030"%>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>身份验证</title>
</head>
<body>
<%
request.setCharacterEncoding("GB18030");
String name = request.getParameter("user");
String password = request.getParameter("pass");
/* if(name.equals("admin")&& password.equals("admin")) { */

%>
<% session.setAttribute("userName",name); %>

<jsp:forward page="repair_list.jsp">
	<jsp:param name="user" value="<%=name%>"/>
</jsp:forward>


<%
/* } */
/* <%-- else { */
%>
<%-- <jsp:forward page="index.html"/>
<%
} --%> 

</body>
</html> 