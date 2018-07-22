package com.repairapp.tool;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

public class ExitAppUtils extends Activity {

	private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitAppUtils instance;
   
    private ExitAppUtils() {
    }
   
    // 单例模式中获取唯一的ExitAPPUtils实例
    public static ExitAppUtils getInstance() {
        if(null == instance) {
            instance =new ExitAppUtils();
        }
        return instance;
    }
   
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
   
    // 遍历所有Activity并finish
    public void exit() {
        for(Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
