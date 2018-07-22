/**
 * 
 */
package com.repairapp.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;


public class Utils {
	/**
	 * 开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP = 2;

	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";

	/**
	 * 根据定位结果返回定位信息的字符串
	 * 
	 * @param loc
	 * @return
	 */
	// 获取经度Longitude
	public synchronized static String getLocationLongitude(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		sb.append(location.getLongitude());
		return sb.toString();
	}

	// 获取纬度Latitude
	public synchronized static String getLocationLatitude(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		sb.append(location.getLatitude());
		return sb.toString();
	}

	// 获取定位时间time
	public synchronized static String getLocationTime(AMapLocation location) {
		if (null == location) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(location.getTime());
		return df.format(date);// 定位时间
	}
	//获取兴趣点
	public synchronized static String getPosition(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		sb.append(location.getPoiName());
		return sb.toString();
	}
}
