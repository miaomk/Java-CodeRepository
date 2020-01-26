package com.techwells.wumei.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class AuthUtil {
	public static final String APPID = "wx546adbfc052c7d94";
	public static final String APPSECRET = "0a3e8534cb44669b90f6b26d336b62c4";

	public static JSONObject doGetJson(String url) throws IOException,
			JSONException {
		JSONObject jsonObject = null;
		@SuppressWarnings("resource")
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpEntity != null) {
			String result = EntityUtils.toString(httpEntity, "UTF-8");
			jsonObject = new JSONObject(result);
			// System.out.println("jsonObject:  "+jsonObject);
		}
		httpGet.releaseConnection();
		return jsonObject;
	}
}
