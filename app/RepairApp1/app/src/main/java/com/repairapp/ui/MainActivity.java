package com.repairapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.repairapp.tool.ExitAppUtils;

public class MainActivity extends Activity {

	//图片显示时间
	private static final int LOAD_DISPLAY_TIME = 1500;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setFormat(PixelFormat.RGBA_8888);  
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);  
		ExitAppUtils.getInstance().addActivity(this);
		
		 new Handler().postDelayed(new Runnable() {  
	            public void run() {  
	                //Go to main activity, and finish load activity  
	                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);  
	                MainActivity.this.startActivity(mainIntent);  
	                MainActivity.this.finish();  
	            }  
	        }, LOAD_DISPLAY_TIME);   
	}
}
