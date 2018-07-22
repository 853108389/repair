package com.repairapp.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.repairapp.tool.ExitAppUtils;
import com.repairapp.tool.HttpConfig;
import com.repairapp.tool.MyAutoCompleteTextView;
import com.repairapp.tool.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;

public class ServiceActivity extends Activity implements AMapLocationListener {

    private String thing;// 报修物品
    private String describe;// 故障描述
    private String address;// 报修地点
    private String name;// 报修人员
    private String tel;// 联系号码
    private String time;// 报修时间
    // private String state;// 维修状态
    private String longitude;// 经度
    private String latitude;// 纬度
    private String username;// 账号
    private String order;// 工单号

    private Spinner thingSpinner;// 报修物品
    private EditText descriEditText;// 故障描述
    private MyAutoCompleteTextView addressEditText;// 报修地点
    private EditText nameEditText;// 报修人员
    private EditText telEditText;// 联系电话
    private Button btnRepair;
    String result = null;
    private boolean bl;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private Spinner firstSpinner = null; // 一级
    private Spinner secondSpinner = null; // 二级
    ArrayAdapter<String> firstAdapter = null; // 一级适配器
    ArrayAdapter<String> secondAdapter = null; // 二级适配器
    static int provincePosition = 3;
    // private EditText address;

    // 一级选项值
    private String[] first = new String[]{"请选择", "教学楼", "食堂", "宿舍", "其他"};

    // 二级选项值
    private String[][] second = new String[][]{
            {"请选择"},
            {"请选择", "投影仪", "电脑", "电灯", "风扇", "音响", "空调", "桌椅", "门", "窗户",
                    "饮水机"},
            {"请选择", "圈存机", "风扇", "空调", "电灯", "刷卡机", "电视", "桌椅"},
            {"请选择", "电灯", "网孔", "插座", "空调", "淋浴刷卡器", "水龙头", "桌椅", "床"},
            {"请选择", "路灯", "垃圾桶", "路牌", "自动售卖机"}};

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
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ExitAppUtils.getInstance().addActivity(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        setContentView(R.layout.activity_service);
        // 定位
        locationInit();
        // 下拉框
        setSpinner();
        // 设置初始化视图
        initViews();
        // 设置事件监听器方法
        setListener();
        addressEditText
                .setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            // 此处为得到焦点时的处理内容
                            String[] allUserName = new String[1];
                            allUserName[0] = address;
                            addressEditText
                                    .setAdapter(new ArrayAdapter<String>(
                                            ServiceActivity.this,
                                            android.R.layout.simple_dropdown_item_1line,
                                            allUserName));
                        } else {
                            // 此处为失去焦点时的处理内容
                        }
                    }
                });
    }

    // 初始化
    private void initViews() {
        thingSpinner = (Spinner) findViewById(R.id.spin_second);
        descriEditText = (EditText) findViewById(R.id.describe);
        addressEditText = (MyAutoCompleteTextView) findViewById(R.id.address);
        nameEditText = (EditText) findViewById(R.id.repair_name);
        telEditText = (EditText) findViewById(R.id.repair_tel);
        btnRepair = (Button) findViewById(R.id.repair_confirm);

    }

    /**
     * 设置事件的监听器的方法
     */
    private void setListener() {
        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thing = (String) thingSpinner.getSelectedItem();
                Log.v("thing = ", thing);
                describe = descriEditText.getText().toString();
                address = addressEditText.getText().toString();
                name = nameEditText.getText().toString();
                tel = telEditText.getText().toString();
                order = createOrder(time);
                if (thing.equals("请选择")) {
                    String string = "请选择报修事项！";
                    alertClick(string);
                    return;
                }
                if (describe.equals("")) {
                    String string = "请描述故障情况！";
                    alertClick(string);
                    return;
                }
                if (address.equals("")) {
                    String string = "请填写报修地点！";
                    alertClick(string);
                    return;
                }
                if (name.equals("")) {
                    String string = "请填写报修人员！";
                    alertClick(string);
                    return;
                }
                if (tel.equals("")) {
                    String string = "请填写联系电话！";
                    alertClick(string);
                    return;
                }

                doRepair(thing.trim(), describe.trim(), address.trim(), name.trim(), tel.trim(), time.trim(), longitude.trim(),
                        latitude.trim(), username.trim(), order.trim());
            }
        });
    }


    public void doRepair(String thing, String describe, String address,
                                   String name, String tel, String time, String longitude,
                                   String latitude, String username, String order) {
        try {
            String params = "thing=" + URLEncoder.encode(thing, "utf-8") + "&describe=" +  URLEncoder.encode(describe, "utf-8") + "&address="
                    +  URLEncoder.encode(address, "utf-8") + "&name="
                    +  URLEncoder.encode(name, "utf-8") + "&tel="
                    + URLEncoder.encode(tel, "utf-8") + "&time="
                    + URLEncoder.encode(time, "utf-8")  + "&longitude="
                    +URLEncoder.encode(longitude, "utf-8") + "&latitude="
                    + URLEncoder.encode(latitude, "utf-8") + "&username="
                    + URLEncoder.encode(username, "utf-8") + "&order="
                    +  URLEncoder.encode(order, "utf-8");
            // 创建一个HttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
//            params = URLEncoder.encode(params, "utf-8");
            String url = HttpConfig.repair_url + params;
            Log.d("远程URL", url);

            // 创建HttpGet对象
            HttpGet request = new HttpGet(url);
            request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            request.addHeader("Accept", "text/json");
            // 获取响应的结果
            HttpResponse response = httpclient.execute(request);
            // 获取HttpEntity
            HttpEntity entity = response.getEntity();
            // 获取响应的结果信息
            String string = "报修提交成功！";
            alertClick(string);

        } catch (Exception e) {
            e.printStackTrace();
            String string = "报修失败，请重新提交！";
            alertClick(string);
        }
    }


//    public void doRepair(String thing, String describe, String address,
//                         String name, String tel, String time, String longitude,
//                         String latitude, String username, String order) {
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("thing", thing);
//        params.put("describe", describe);
//        params.put("address", address);
//        params.put("name", name);
//        params.put("tel", tel);
//        params.put("time", time);
//        params.put("longitude", longitude);
//        params.put("latitude", latitude);
//        params.put("username", username);
//        params.put("order", order);
//
//        try {
//            String url = HttpConfig.repair_url;
//            bl = HttpRequest.sendRequestFromHttpClient(url, params, "UTF-8");
//        } catch (Exception ee) {
//            // TODO Auto-generated catch block
//            ee.printStackTrace();
//        }
//
//        if (bl) {
//            String string = "报修提交成功！";
//            alertClick(string);
//        } else {
//            String string = "报修失败，请重新提交！";
//            alertClick(string);
//        }

		
		/*
		 * 
		 * try {
		 * 
		 * // 创建一个HttpClient对象 HttpClient httpclient = new DefaultHttpClient();
		 * // 远程登录URL url = url_constant + "thing=" + thing + "&describe=" +
		 * describe + "&address=" + address + "&name=" + name + "&tel=" + tel +
		 * "&time=" + time + "&longitude=" + longitude + "&latitude=" + latitude
		 * + "&username=" + username + "&order=" + order; Log.d("远程URL", url);
		 * // 创建HttpGet对象
		 * 
		 * HttpGet request = new HttpGet(url); request.setHeader("Content-Type",
		 * "application/x-www-form-urlencoded; charset=utf-8");
		 * request.addHeader("Accept", "text/json");
		 * 
		 * 
		 * // 获取响应的结果 HttpResponse response = httpclient.execute(request); //
		 * 获取HttpEntity HttpEntity entity = response.getEntity(); // 获取响应的结果信息
		 * String json = EntityUtils.toString(entity, "UTF-8"); // JSON的解析过程 if
		 * (json != null) { JSONObject jsonObject = new JSONObject(json); result
		 * = jsonObject.get("message").toString(); } if (result == null) { json
		 * = "提交失败，请重新提交！"; } // 创建提示框提醒是否报修成功 AlertDialog.Builder builder = new
		 * Builder(MainActivity.this);
		 * 
		 * builder.setTitle("提示") .setMessage(result) .setPositiveButton("确定",
		 * new DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss();
		 * 
		 * } }).create().show();
		 * 
		 * } catch (ClientProtocolException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
//    }

    // 生成工单号
    public String createOrder(String str) {

        return str.replaceAll("[^0-9]", "");
    }


    // // 创建提示框提醒是否报修成功
    private void alertClick(String str) {
        AlertDialog.Builder builder = new Builder(ServiceActivity.this);
        builder.setTitle("提示").setMessage(str)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).create().show();
    }

    private void setSpinner() {
        firstSpinner = (Spinner) findViewById(R.id.spin_first);
        secondSpinner = (Spinner) findViewById(R.id.spin_second);

        // 绑定适配器和值
        firstAdapter = new ArrayAdapter<String>(ServiceActivity.this,
                R.layout.spinner, first);
        firstSpinner.setAdapter(firstAdapter);
        firstSpinner.setSelection(0, true); // 设置默认选中项，此处为默认选中第1个值

        secondAdapter = new ArrayAdapter<String>(ServiceActivity.this,
                R.layout.spinner, second[0]);
        secondSpinner.setAdapter(secondAdapter);
        secondSpinner.setSelection(0, true); // 默认选中第1个

        // 一级下拉框监听
        firstSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                        // position为当前一级选中的值的序号

                        // 将地级适配器的值改变为second[position]中的值
                        secondAdapter = new ArrayAdapter<String>(
                                ServiceActivity.this, R.layout.spinner,
                                second[position]);
                        // 设置二级下拉列表的选项内容适配器
                        secondSpinner.setAdapter(secondAdapter);
                        provincePosition = position; // 记录当前一级序号，留给下面修改县级适配器时用
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                });

    }

    // 定位
    public void locationInit() {
        Log.d("初始化","初始化");
        locationClient = new AMapLocationClient(this.getApplicationContext());
        // 初始化定位参数
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式,同时使用网络定位和GPS定位，优先返回最高精度的定位结果
        locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 设置是否只定位一次,默认为false
        locationOption.setOnceLocation(false);
        // 设置定位间隔,单位毫秒,默认为1000ms
        locationOption.setInterval(1000);
        // 给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
        Log.d("初始化成功","初始化成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {

            AMapLocation loc = (AMapLocation) msg.obj;
            // 经度
            longitude = Utils.getLocationLongitude(loc);
            // 纬度
            latitude = Utils.getLocationLatitude(loc);
            // 时间
            time = Utils.getLocationTime(loc);
            // 位置
            address = Utils.getPosition(loc);
            // address.setText(positionString);

        }
    };

    // 定位监听
    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
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
