<%@page import="com.db.DBLinker"%>
<%@ page language="java" contentType="text/html" import="java.sql.*"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Math.*"%>
<%@ page import="java.io.*"%>
<%@ include file="checksession.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报修列表</title>

<link rel="stylesheet" type="text/css" href="./css/style.css">
	<script type="text/javascript"
		src="http://webapi.amap.com/maps?v=1.3&key=ec83300ab2d153d705ae266e4b51afdb"></script>
	<script type="text/javascript"
		src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
	<script type="text/javascript" src="./JS/map.js"></script>


	<style type="text/css">
#list {
	width: 600px;
	margin: auto;
	padding: 2px;
	font-size: 15px;
	border: 3px solid;
	background: #A6D2FF;
}

tr {
	background: #fff;
}

td {
	padding: 5px;
}

#title {
	text-align: center;
	background-color: #A6D2FF;
}
</style>
</head>

<body onLoad="mapInit()">

	<div id="panel"></div>
	<div id=all>
		<div id="main">
			<table width="1350" border="0">
				<td width="300"></td>
				<td>
					<table id=whole width="1300" bordercolor="#FFF" border="1"
						cellSpacing=0 cellPadding=1 align="center">
						<tr>
							<td id=head height="170" colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td id=mapinfo>
								<div id=font1>地图显示</div>
							</td>
							<td id=repairinfo>
								<div id=font2>报修列表</div>
							</td>
						</tr>
						<tr>
							<td id=map height="700">&nbsp;</td>
							<td valign="top" id=listall>
								<table id=list width="780" border="1" cellpadding=0
									cellspacing=0 align="center"margin-top:"0px";>
									<tr>
										<td width="70" id="title">序号</td>
										<td width="100" id="title">报修物品</td>
										<td width="100" id="title">故障描述</td>
										<td width="150" id="title">报修区域</td>
										<td width="100" id="title">报修人员</td>
										<td width="150" id="title">报修时间</td>
										<td width="100" id="title">维修状态</td>
									</tr>




									<%
										try {
											
											Connection conn =DBLinker.getConnection(); 
											//DriverManager.getConnection(ConnStr)
											//创建执行语句
											String sql = "SELECT * FROM repair_info ORDER BY id desc ";
											Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
											ResultSet rs = stmt.executeQuery(sql);

											int intPageSize; //一页显示的记录数
											int intRowCount; //记录的总数
											int intPageCount; //总页数
											int intPage; //待显示的页码
											String strPage;
											int i;
											//设置一页显示的记录数
											intPageSize = 12;
											//取得待显示的页码
											strPage = request.getParameter("page");
											//判断strPage是否等于null,如果是，显示第一页数据
											if (strPage == null) {
												intPage = 1;
											} else {
												//将字符串转换为整型
												intPage = java.lang.Integer.parseInt(strPage);
											}
											if (intPage < 1) {
												intPage = 1;
											}
											//获取记录总数
											rs.last();
											intRowCount = rs.getRow();
											//计算机总页数
											intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
											//调整待显示的页码
											if (intPage > intPageCount)
												intPage = intPageCount;
											if (intPageCount > 0) {
												//将记录指针定位到待显示页的第一条记录上
												rs.absolute((intPage - 1) * intPageSize + 1);
											}
											//下面用于显示数据
											i = 0;
											while (i < intPageSize && !rs.isAfterLast()) {
									%>
									<tr>
										<td width="70"><a
											href="repair_info.jsp?id=<%=rs.getString("id")%>"
											title=点击查看该工单号的详情><%=rs.getString("id")%></a></td>
										<td width="100"><%=rs.getString("thing")%></td>
										<td width="100"><%=rs.getString("describes")%></td>
										<td width="150"><%=rs.getString("address")%></td>
										<td width="100"><%=rs.getString("userName")%></td>
										<td width="150"><%=rs.getString("time")%></td>
										<td width="100"><%=rs.getString("state")%></td>
									</tr>
									<%
										rs.next();
												i++;
											}

											//关闭连接、释放资源
											rs.close();
											stmt.close();
											/* conn.close(); */
									%>
									<tr>
										<td colspan=7 valign="top">
											<div align="center">
												共<%=intRowCount%>个记录，分<%=intPageCount%>页显示，当前页是：第<%=intPage%>页
												<%
												for (int j = 1; j <= intPageCount; j++) {
														out.print("&nbsp;&nbsp;<a href='repair_list.jsp?page=" + j + "'>" + j + "</a>");

													}
											%>
											</div>
										</td>
									</tr>


									<div height="50"；>
										<ul>
											<li><input type=button value=刷新 onclick="history.go(0)">
													<button
														onclick="javascript:window.location.href='unrepaired_list.jsp';">未维修工单</button>
													<button
														onclick="javascript:window.location.href='repairing_list.jsp';">维修中工单</button>
													<button
														onclick="javascript:window.location.href='repaired_list.jsp';">已维修工单</button></li>

										</ul>



									</div>
									<%
										} catch (Exception e) {
											e.printStackTrace();
										}
									%>
								</table>

							</td>
						</tr>
						<tr>


						</tr>
						<tr>
							<td id=bottom height="60" colspan="2">&nbsp;</td>
						</tr>
					</table>
				</td>
				<td width="300"></td>
			</table>
		</div>
	</div>


</body>



</html>
