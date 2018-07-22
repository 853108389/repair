<%@page import="com.db.DBLinker"%>
<%@ page language="java" contentType="text/html" import="java.sql.*"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Math.*"%>
<%@ page import="java.io.*"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<body>
	<%!String update(String id, String state) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn =DBLinker.getConnection(); 
			st = conn.createStatement();
		
			String sql = "UPDATE repair_info SET state='" + state + "' WHERE id= '" + id + "' ";
			st.executeUpdate(sql);
			

		} catch (SQLException e) {
		}

		return "¸�¦";
	}%>
	<%
		String id = request.getParameter("id");
		id = id.trim();
		byte b[] = id.getBytes("UTF-8");
		id = new String(b);

		String state = new String(request.getParameter("state").getBytes("ISO-8859-1"), "utf-8");

		String del = update(id, state);
		response.sendRedirect("repair_info.jsp?id=" + id + "");
	%>

</body>

</html>