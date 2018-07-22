<%@page import="com.db.DBLinker"%>
<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.Math.*" %>
<%@ page import="java.io.*" %>
<%@ include  file="checksession.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维修中列表</title>

<link rel="stylesheet" type="text/css" href="./css/style.css">
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=ec83300ab2d153d705ae266e4b51afdb"></script>
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript" src="./JS/map.js"></script>

<style type="text/css"> 
#list{ width:780px; margin:auto; padding: 2px; font-size:15px; border:3px solid;  background:#A6D2FF;} 
tr{ background:#fff;} 
td{ padding: 5px;} 
#title{ text-align:center;  background-color:#A6D2FF;} 
</style> 
</head>

<body onLoad="mapInit()">
<div id="panel"></div>
<div id="main">

<table width="1300" bordercolor="#FFF" border="1" cellSpacing=0 cellPadding=1 align="center">
  <tr>
    <td id=head height="170" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td id=mapinfo >
    <div id=font1>地图显示</div> 
    </td>
    <td id=repairinfo >
     <div id=font2>报修列表</div> 
    </td>
  </tr>
  <tr>
    <td id=map width="497" rowspan="2" >&nbsp;</td>
    <td width="800" height="780" valign="top" >
  <div>
 <% 
 //连接MySQL数据库  
    Connection conn =DBLinker.getConnection(); 
    Statement st = conn.createStatement(); 
     
 %> 
    <table width="800" border="1" cellpadding=0 cellspacing=0 align="center"> 
  <tr> 
    <td width="70" id="title">序号</td> 
    <td width="100" id="title">报修物品</td> 
    <td width="100" id="title">故障描述</td>
    <td width="100" id="title">报修区域</td>
    <td width="100" id="title">报修人员</td>
    <td width="200" id="title">报修时间</td>  
    <td width="100" id="title">维修状态员</td>    
  </tr> 
     
<% 
    //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。 
    ResultSet rs = st.executeQuery("SELECT * FROM repair_info  WHERE state= '维修中' ORDER BY id desc LIMIT 15"); 
    
	while(rs.next()){%> 
    
     
  <tr> 
    <td width="70" ><a href="repair_info.jsp?id=<%=rs.getString("id")%>"><%=rs.getString("id")%></a></td> 
    <td width="100" ><%=rs.getString("thing") %></td> 
    <td width="100"><%= rs.getString("describes") %></td>
    <td width="100"><%=rs.getString("address") %></td> 
    <td width="100"><%=rs.getString("userName") %></td>  
    <td width="200"><%=rs.getString("time") %></td>
    <td width="100"><%=rs.getString("state") %></td>
  </tr> 
 
<%} 
//注意"}"的位置 %> 
 </table> 
</div> 
<% 
    rs.close(); 
    /* conn.close();  */
 %> 
  
  <div>  
        <ul>            
            <li>
                <button onclick="javascript:window.location.href='repair_list.jsp';">返回</button>
                
               
            </li>
         </ul>
    


</div> 
      
      </td>
  </tr>
  <tr>
    
    
  </tr>
  <tr>
    <td id=bottom height="60" colspan="2">&nbsp;
    </td>
  </tr>
</table>
</div>



</body>



</html>
