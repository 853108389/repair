package com.repairapp.tool;

public class HttpConfig {
	
	 public final static String  url="http://180.209.64.36:8080/Repair20161129";
//	 public final static String  login_url=url+"/JsonWeb/login.action?";
//	 public final static String  register_url=url+"/JsonWeb/register.action?";
//	 public final static String  repair_url=url+"/JsonWeb/repair.action";
//	 public final static String  history_url=url+"/JsonWeb/history.action?";
//	 public final static String  order_url=url+"/JsonWeb/order_info.action?";
	public final static String  login_url=url+"/LoginServlet?";
	public final static String  register_url=url+"/RegisterServlet?";
	public final static String  repair_url=url+"/RepairServlet?";
	public final static String  history_url=url+"/HistoryServlet?";
	public final static String  order_url=url+"/OrderServlet?";
}
