package com.repairapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.repairapp.tool.ExitAppUtils;
import com.repairapp.tool.HttpConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends Activity {

	private EditText txUserName;
	private EditText txPassword;
	private Button btnRegister;
	private Button btnLogin;
	String result = null;
	private long exitTime = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// 设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		super.onCreate(savedInstanceState);
		ExitAppUtils.getInstance().addActivity(this);
		// 设置页面布局
		setContentView(R.layout.activity_login);
		// 设置初始化视图
		initView();
		// 设置事件监听器方法
		setListener();
	}

	/**
	 * 创建初始化视图的方法
	 */
	private void initView() {
		btnLogin = (Button) findViewById(R.id.btnLogin);
		txUserName = (EditText) findViewById(R.id.UserName);
		txPassword = (EditText) findViewById(R.id.textPasswd);
		btnRegister = (Button) findViewById(R.id.btnRegister);
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		//登录
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = txUserName.getText().toString();
				String password = txPassword.getText().toString();
				loginRemoteService(userName, password);
			}
		});
		//注册
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			    startActivity(intent);
			}
		});
	}

	/**
	 * 获取Struts2 Http 登录的请求信息
	 * 
	 * @param userName
	 * @param password
	 */
	public void loginRemoteService(String userName, String password) {

		try {
			// 创建一个HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			// 远程登录URL
			String url = HttpConfig.login_url + "userName=" + userName + "&password="
					+ password;
			// 创建HttpGet对象
			HttpGet request = new HttpGet(url);
			request.addHeader("Accept", "text/json");
			// 获取响应的结果
			HttpResponse response = httpclient.execute(request);
			// 获取HttpEntity
			HttpEntity entity = response.getEntity();
			// 获取响应的结果信息
			String json = EntityUtils.toString(entity, "UTF-8");
			// JSON的解析过程
			if (json != null) {
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("message").toString();
			}
			if (result == null) {
				json = "登录失败请重新登录";
			}
			// 创建提示框提醒是否登录成功
			AlertDialog.Builder builder = new Builder(LoginActivity.this);
			if (txUserName.getText().toString().equals("")
					|| txPassword.getText().toString().equals("")) {
				builder.setTitle("提示")
						.setMessage("账号或者密码不能为空，请重新输入！")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).create().show();
			} else {

				builder.setTitle("提示")
						.setMessage(result.equals("true") ? "欢迎登入" : "用户名或密码错误")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										if (result.equals("true")) {
											String username = txUserName.getText().toString();
											Intent intent = new Intent(
													LoginActivity.this,
													MenuActivity.class);
											intent.putExtra("username", username);
											startActivity(intent);
										}
									}
								}).create().show();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 再按一次返回退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次返回退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// finish();
				// System.exit(0);
				ExitAppUtils.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
