package com.repairapp.tool;

public class Orders {

	private String orders;//报修工单
	private String thing;//报修物品
	private String describes;//故障描述
	private String address;//报修地点
	private String name;//报修人员
	private String tel;//联系电话
	private String time;//报修时间
	private String state;//维修状态
	
	public Orders(String orders, String thing, String describes,
			String address, String name, String tel, String time, String state) {
		super();
		this.orders = orders;
		this.thing = thing;
		this.describes = describes;
		this.address = address;
		this.name = name;
		this.tel = tel;
		this.time = time;
		this.state = state;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getThing() {
		return thing;
	}
	public void setThing(String thing) {
		this.thing = thing;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
