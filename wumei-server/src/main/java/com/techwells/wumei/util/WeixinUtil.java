package com.techwells.wumei.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.dahantc.api.commons.MyX509TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WeixinUtil {

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid     凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static String getAccessToken(String appid, String appsecret) {
		// 获取公众号access_token的链接
		String access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

		String requestUrl = access_token.replace("APPID", appid).replace("APPSECRET", appsecret);

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (null != jsonObject) {
			try {
				return jsonObject.getString("access_token");
			} catch (JSONException e) {
			}
		}
		return null;
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param appid     凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static String getJsapiTicket(String accessToken) {
		// 获取公众号jsapi_ticket的链接
		String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		if (accessToken != null) {
			String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
			// String requestUrl = access_token_url.replace("APPID",
			// appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					return jsonObject.getString("ticket");
				} catch (JSONException e) {
				}
			}
		} else {
			System.out.println("*****token为空 获取ticket失败******");
		}

		return null;
	}

	// 签名
	public static Map<String, String> getSign(String url, String jsapi_ticket) throws NoSuchAlgorithmException {
		Map<String, String> resultMap = new HashMap<>(16);
		String noncestr = RandomCodeUtils.getNumberRandom(8);
		long timestamp = System.currentTimeMillis()/1000;

		String decodeUrl = "";
		try {
			decodeUrl = URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		String shaStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ decodeUrl;
		String singStr = getSha1(shaStr);
//		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
//		byte[] result = mDigest.digest(shaStr.getBytes());
//		StringBuffer signature = new StringBuffer();
//		for (int i = 0; i < result.length; i++) {
//			signature.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
//		}
		resultMap.put("nonceStr", noncestr);
		resultMap.put("timestamp", timestamp + "");
		resultMap.put("appId", "wx47085439d65e5fbc");
		resultMap.put("signature", singStr);
		return resultMap;
	}

	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			getSign("sM4AOVdWfPE4DxkXGEs8VE5lKVgG8DNi9XT6EAIk8RwnybHjIG16RWiOAEdgsutYDh0tvBCyiDO2NUt8v_QlBQ","http://localhost:63342/out-pros/walmart2019/");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println(getSha1("jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VE5lKVgG8DNi9XT6EAIk8RwnybHjIG16RWiOAEdgsutYDh0tvBCyiDO2NUt8v_QlBQ&noncestr=31218592&timestamp=1547567276&url=http://localhost:63342/out-pros/walmart2019/"));

	}

}
