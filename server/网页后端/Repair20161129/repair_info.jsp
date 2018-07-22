<%@page import="com.db.DAO"%>
<%@page import="com.db.DBLinker"%>
<%@ page contentType="text/html" language="java" import="java.sql.*"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Math.*"%>
<%@ page import="java.io.*"%>
<%@ include file="checksession.jsp"%>

<!DOCTYPE 
    html
    PUBLIC
    "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报修信息</title>

<link rel="stylesheet" type="text/css" href="./css/style.css">
	<script type="text/javascript"
		src="http://webapi.amap.com/maps?v=1.3&key=ec83300ab2d153d705ae266e4b51afdb">
		
	</script>
	<script type="text/javascript"
		src="http://cache.amap.com/lbs/static/addToolbar.js">
		
	</script>
	<script type="text/javascript" src="./JS/map.js">
		
	</script>


	<style type="text/css">
#info {
	width: 780px;
	margin: auto;
	padding: 5px;
	font-size: 15px;
	border: 0px;
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
}
</style>
</head>

<body onLoad="mapInit()">
	<div id="panel"></div>
	<div id="main">

		<table width="1300" bordercolor="#FFF" border="1" cellSpacing=0
			cellPadding=1 align="center">

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
				<td id=map width="497" rowspan="2">&nbsp;</td>
				<td width="800" height="680" valign="top">
					<div>
						<table id=info width="780" border="1" cellpadding=0 cellspacing=0>
							<%
								//连接MySQL数据库
								Connection conn = DBLinker.getConnection();
								Statement st = conn.createStatement();
							%>

							<%
								String lon = "";
								String lat = "";

								String id = request.getParameter("id");

								ResultSet rs = st.executeQuery("SELECT * FROM repair_info WHERE id= '" + id + "' ");

								if (rs.next()) {
							%>

							<%
								lon = rs.getString("longitude");
							%>
							<%
								lat = rs.getString("latitude");
							%>
							<tr>
								<td colspan=4 bgcolor=#0099FF>
									<center>
										工单号为 <font color=red> <%=rs.getString("orders")%>
										</font> 的报修信息
									</center>
								</td>

							</tr>
							<tr>
								<div></div>
								<td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>报修物品</center>
								</td>
								<td><%=rs.getString("thing")%></td>
								<td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>报修时间</center>
								</td>
								<td><%=rs.getString("time")%></td>
							</tr>
							<tr>
								<td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>报修人</center>
								</td>
								<td><%=rs.getString("name")%></td>
								<td width="100" bordercolor=#A6D2FF bgcolor="#A6D2FF">
									<center>联系电话</center>
								</td>
								<td width=280><%=rs.getString("tel")%></td>
							</tr>
							<tr >
								<td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>报修地址</center>
								</td>
								<td colspan="3"><%=rs.getString("address")%></td>
								<%-- <td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>经纬度</center>
								</td>
								<td>[ <%=lon%> , <%=lat%> ]
								</td> --%>
							</tr>
							<tr>
								<td width=100 bordercolor=#A6D2FF bgcolor=#A6D2FF>
									<center>故障描述</center>
								</td>
								<td colspan=3><%=rs.getString("describes")%></td>
							</tr>

							<!-- 出现故障设施的图片 -->

						<!-- 	<tr>
								<td colspan=4 width="780" bgcolor="#A6D2FF">
									<center>故障图片</center>
								</td>
							</tr> -->
							<tr>
								<td colspan="4" height="100">
									<%
										String fileId=rs.getString("img_file_id");
										ArrayList<String> imgPaths = DAO.getImgFiles(fileId);
										
										for(int i=0;i<imgPaths.size();i++){
											String path=imgPaths.get(i); 
										
											
											%> 
											<img  height="100" src="ShowImageServlet?path=<%=path %>" alt="维修图片" />
											
											<%
										}
											%>
								</td>
							</tr>


							<tr>
								<td colspan=4 bgcolor=#FF6600>
									<center>
										工单号为 <font color="blue"> <%=rs.getString("orders")%>
										</font> 的跟踪信息
									</center>
								</td>
							</tr>
							<tr>
								<td width="150" bordercolor="#FFCC00" bgcolor=#FFCC00>
									<center>操作时间</center>
								</td>
								<td colspan=2 bordercolor=#FFCC00 bgcolor=#FFCC00>
									<center>跟踪信息</center>
								</td>
								<td width=100 bordercolor=#FFCC00 bgcolor=#FFCC00>
									<center>维修进度</center>
								</td>
							</tr>
							<tr>
								<td bgcolor=#B5EEAE><%=rs.getString("time")%></td>
								<td colspan=2 bgcolor=#B5EEAE>已生成工单 <%=rs.getString("orders")%>
									，已受理。
								</td>
								<td bgcolor=#B5EEAE><%=rs.getString("state")%>
									<FORM action="update.jsp" Method=post>
										<Input type="hidden" name="id" value="<%=rs.getString("id")%>">

											进&nbsp;&nbsp;&nbsp;度:&nbsp; <select name="state">
												<option value="未维修">未维修</option>
												<option value="维修中">维修中</option>
												<option value="已维修">已维修</option>
										</select> <Input type="submit" value="更新状态">
									</FORM></td>





								</td>
							</tr>



							<%
								}
							%>
						</table>
					</div> <%
 	rs.close();
 	/* conn.close(); */
 %>


					<div>
						<ul>
							<li><input type=button value=刷新 onclick="history.go(0)">
									<button
										onclick="javascript:window.location.href='repair_list.jsp';">
										返回</button></li>

						</ul>


					</div>



				</td>
			</tr>
			<tr>
				<td id=show2 height="69">&nbsp;
					<ul>
						 <p>
						<!-- 	坐标： --> <span id="lnglats"> &nbsp; </span>
						</p> 
						<li>
							<button onclick="javascript:position_now();">维修地点</button>
							<button onclick="javascript:walking_route();">路线导航</button>
							<button onclick="javascript:clearMap();">清空地图</button>
						</li>
					</ul>

				</td>

			</tr>
			<tr>
				<td id=bottom height="60" colspan="2">&nbsp;</td>
			</tr>
		</table>
	</div>



</body>

<script>
	var a =
<%=lon%>
	;
	var b =
<%=lat%>
	;

	var end_xy = new AMap.LngLat(a, b);
</script>
<script type="text/javascript" charset="UTF-8">
	function repairing() {

		var gt = "更改成功！";

		alert(gt);

		//history.go(0);

	}
	
	
</script>

</html>




