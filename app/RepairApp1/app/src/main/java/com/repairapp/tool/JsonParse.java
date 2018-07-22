package com.repairapp.tool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParse {
/**

 * 解析Json数据

 *

 * @param urlPath

 * @return mlists

 * @throws Exception

 */

public static List<Orders> getListPerson(String urlPath) throws Exception {

        List<Orders> mlists = new ArrayList<Orders>();

        byte[] data = readParse(urlPath);

        JSONArray array = new JSONArray(new String(data));

        for (int i = 0; i < array.length(); i++) {

                JSONObject item = array.getJSONObject(i);
                
                String orders = item.getString("orders");
                String thing = item.getString("thing");
                String describes = item.getString("describes");
                String address = item.getString("address");
                String name = item.getString("name");
                String tel = item.getString("tel");
                String time = item.getString("time");
                String state = item.getString("state");
                
                mlists.add(new Orders(orders, thing, describes, address, name, tel, time, state));

        }

        return mlists;

}

/**

 * 从指定的url中获取字节数组

 *

 * @param urlPath

 * @return 字节数组

 * @throws Exception

 */

public static byte[] readParse(String urlPath) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] data = new byte[1024];

        int len = 0;

        URL url = new URL(urlPath);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        InputStream inStream = conn.getInputStream();

        while ((len = inStream.read(data)) != -1) {

                outStream.write(data, 0, len);

        }

        inStream.close();

        return outStream.toByteArray();

}

}