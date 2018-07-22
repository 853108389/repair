package com.repairapp.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    /**
     * 账号
     */
    private EditText newUserName;
    /**
     * 新密码
     */
    private EditText newPassWord;
    /**
     * 确认密码
     */
    private EditText confirmPassWord;
    private String userName;
    private String password;
    private Button btnRegister;
    String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置线程的策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        // 设置虚拟机的策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ExitAppUtils.getInstance().addActivity(this);
        // 设置初始化视图
        initViews();
        // 设置事件监听器方法
        setListener();

    }


    private void initViews() {
        newUserName = (EditText) findViewById(R.id.activity_register_username);
        newPassWord = (EditText) findViewById(R.id.activity_register_newpassword);
        confirmPassWord = (EditText) findViewById(R.id.activity_register_confirpassword);
        btnRegister = (Button) findViewById(R.id.activity_register_confirm);

    }

    /**
     * 设置事件的监听器的方法
     */
    private void setListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = newUserName.getText().toString();
                Log.v("userName = ", userName);
                password = newPassWord.getText().toString();
                Log.v("passwd = ", password);
                if (userName != null && userName != "" && userName.length() > 0) {
                    if (!isNumeric(userName) && !isEmail(userName)) {
                        Toast.makeText(RegisterActivity.this, "手机或邮箱格式不正确",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isNumeric(userName) && !isMobileNO(userName)) {
                        Toast.makeText(RegisterActivity.this, "手机或邮箱格式不正确",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                doRegister(userName, password);
            }
        });
    }

    // 判断手机格式是否正确
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    // 判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    // 判断是否全是数字
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public void doRegister(String userName, String password) {

        try {

            // 创建一个HttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
            // 远程登录URL
            String url = HttpConfig.register_url + "userName=" + userName
                    + "&password=" + password;
            Log.v("url ", url);
            // 创建HttpGet对象
            HttpGet request = new HttpGet(url);
            request.setHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");
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
                json = "注册失败，请重新注册！";
            }
            // 创建提示框提醒是否注册成功
            AlertDialog.Builder builder = new Builder(RegisterActivity.this);
            if (newUserName.getText().toString().equals("")
                    || newPassWord.getText().toString().equals("")
                    || confirmPassWord.getText().toString().equals("")) {
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
            } else if (newPassWord.getText().toString()
                    .equals(confirmPassWord.getText().toString())) {
                builder.setTitle("提示")
                        .setMessage(result.equals("true")?"欢迎登入":"登入失败")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        if (result.equals("true")) {
                                            Intent intent = new Intent(
                                                    RegisterActivity.this,
                                                    LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }).create().show();
            } else {
                builder.setTitle("提示")
                        .setMessage("两次密码输入不一致，请重新输入！")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
