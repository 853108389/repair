package com.repairapp.tool;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequest {

	public static boolean sendGetRequest(String path,
			Map<String, String> params, String enc) throws Exception {

		StringBuilder sb = new StringBuilder(path);
		sb.append('?');

		// 迭代Map拼接请求参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), enc))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);// 删除最后一个"&"

		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		if (conn.getResponseCode() == 200) {
			return true;
		}
		return false;
	}

	// HttpClient组件 SSL HTTPS Cookie
	public static boolean sendRequestFromHttpClient(String path,
			Map<String, String> params, String enc) throws Exception {

		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 得到经过编码过后的实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				enc);
		HttpPost post = new HttpPost(path); // form
		post.setEntity(entitydata);
		DefaultHttpClient client = new DefaultHttpClient(); // 浏览器
		HttpResponse response = client.execute(post);// 执行请求
		if (response.getStatusLine().getStatusCode() == 200) {
			return true;
		}
		return false;
	}
}
