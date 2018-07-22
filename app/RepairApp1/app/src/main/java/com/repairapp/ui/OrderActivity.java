package com.repairapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends Activity {

	private TextView orderNum;
	private TextView order;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		orderNum = (TextView)findViewById(R.id.orderNum_result);
		order = (TextView)findViewById(R.id.order_result);
		
		orderNum_click();
		order_click();
	}

	public void orderNum_click() {
		StringBuffer sb = new StringBuffer();
		sb.append("2016041514080工单信息");
		orderNum.setText(sb.toString());
	}
	
	public void order_click() {
		StringBuffer sb = new StringBuffer();

		sb.append("\n" + "报修工单: " + "\n");
		sb.append("\n" + "报修物品: " + "\n");
		sb.append("\n" + "故障描述: " + "\n");
		sb.append("\n" + "报修地点: " + "\n");
		sb.append("\n" + "报修人员: " + "\n");
		sb.append("\n" + "联系电话: " + "\n");
		sb.append("\n" + "报修时间: " + "\n");
		sb.append("\n" + "维修状态: " + "\n");
		order.setText(sb.toString());
	}
}
