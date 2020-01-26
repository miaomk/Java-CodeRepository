package com.techwells.wumei.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.*;
import java.util.*;

import com.techwells.wumei.util.wechat.ConstantWeChat;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.dom4j.Document;
import org.dom4j.Element;

import com.aliyuncs.exceptions.ClientException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class WechatUtil {

	public static Object getAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ ConstantWeChat.APPID + "&secret=" + ConstantWeChat.SECRET
				+ "&code=" + code + "&grant_type=authorization_code";
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}

				JSONObject object = JSONObject.fromObject(sb.toString().trim());

				return object;
				/*
				 * String accessToken = object.getString("access_token"); String
				 * openID = object.getString("openid"); String refreshToken =
				 * object.getString("refresh_token"); String expires_in = ""+
				 * object.getLong("expires_in");
				 * 
				 * WeixinBackInfo wbi = new WeixinBackInfo();
				 * wbi.setAccess_token(accessToken); wbi.setOpenid(openID);
				 * wbi.setRefresh_token(refreshToken);
				 * wbi.setExpires_in(expires_in);
				 * 
				 * 
				 * return wbi;
				 */
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Object getBaseAccessToken() {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ ConstantWeChat.APPID + "&secret=" + ConstantWeChat.SECRET;
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}

				JSONObject object = JSONObject.fromObject(sb.toString().trim());

				return object;
				/*
				 * String accessToken = object.getString("access_token"); String
				 * openID = object.getString("openid"); String refreshToken =
				 * object.getString("refresh_token"); String expires_in = ""+
				 * object.getLong("expires_in");
				 * 
				 * WeixinBackInfo wbi = new WeixinBackInfo();
				 * wbi.setAccess_token(accessToken); wbi.setOpenid(openID);
				 * wbi.setRefresh_token(refreshToken);
				 * wbi.setExpires_in(expires_in);
				 * 
				 * 
				 * return wbi;
				 */
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Object getTicket(String accessToken) {

		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=jsapi";
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}

				JSONObject object = JSONObject.fromObject(sb.toString().trim());

				return object;
				/*
				 * String accessToken = object.getString("access_token"); String
				 * openID = object.getString("openid"); String refreshToken =
				 * object.getString("refresh_token"); String expires_in = ""+
				 * object.getLong("expires_in");
				 * 
				 * WeixinBackInfo wbi = new WeixinBackInfo();
				 * wbi.setAccess_token(accessToken); wbi.setOpenid(openID);
				 * wbi.setRefresh_token(refreshToken);
				 * wbi.setExpires_in(expires_in);
				 * 
				 * 
				 * return wbi;
				 */
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Object getUserInfo(String accessToken, String openID,
			long expires_in) {
		// if (isAccessTokenIsInvalid(accessToken, openID)) {
		String uri = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openID
				+ "&lang=zh_CN";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URI.create(uri));
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					builder.append(temp);
				}
				JSONObject object = JSONObject.fromObject(builder.toString()
						.trim());

				return object;
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

		// }

		return null;
	}

	/**
	 * 生成微信支付签名
	 *
	 * @param parameters 参数
	 * @param apiKey     商户秘钥
	 * @return String
	 */
	public static String createSign(SortedMap<String, String> parameters,
									String apiKey) {

		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + apiKey);// 最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
		try {
			String sign = MD5.encrypt(sb.toString(), "UTF-8").toUpperCase();
			return sign;
		} catch (Exception e) {

		}

		return null;
	}

	public static String parseXML(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {

				sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public static String sendPost(CloseableHttpClient httpclient, String url, String xmlObj) throws IOException {
		String result;
		HttpPost httpPost = new HttpPost(url);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		System.out.println(xmlObj);
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<>(16);
		if (doc == null) {
			return map;
		}
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else {
				map.put(e.getName(), e.getText());
			}
		}
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	private static boolean isAccessTokenIsInvalid(String accessToken,
			String openID) {
		String url = "https://api.weixin.qq.com/sns/auth?access_token="
				+ accessToken + "&openid=" + openID;
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);
		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}
				JSONObject object = JSONObject.fromObject(sb.toString().trim());
				int errorCode = object.getInt("errcode");
				if (errorCode == 0) {
					return true;
				}
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
		return false;
	}

	public static void main(String[] args) throws ClientException {

		// WeixinBackInfo token =
		// (WeixinBackInfo)getAccessToken("031BJS5r1vJLbp06XZ3r129X5r1BJS59");

		// System.out.print(token.getAccess_token());

		String str = RandomStringUtils.random(16,
				"1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");

		System.out.print(str);
	}
	
	
	public static Object getMpAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
				+ ConstantWeChat.MP_APPID + "&secret=" + ConstantWeChat.MP_SECRET
				+ "&js_code=" + code + "&grant_type=authorization_code";
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}

				JSONObject object = JSONObject.fromObject(sb.toString().trim());

				return object;
				/*
				 * String accessToken = object.getString("access_token"); String
				 * openID = object.getString("openid"); String refreshToken =
				 * object.getString("refresh_token"); String expires_in = ""+
				 * object.getLong("expires_in");
				 * 
				 * WeixinBackInfo wbi = new WeixinBackInfo();
				 * wbi.setAccess_token(accessToken); wbi.setOpenid(openID);
				 * wbi.setRefresh_token(refreshToken);
				 * wbi.setExpires_in(expires_in);
				 * 
				 * 
				 * return wbi;
				 */
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取微信绑定手机号
	 *
	 * @param encryptedData
	 * @param session_key
	 * @param iv
	 * @return
	 */
	public static String getPhoneNumber(String encryptedData, String session_key, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(session_key);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);
		try {
			// 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				return new String(resultByte, "UTF-8");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Object getMpAccessToken(){

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ ConstantWeChat.MP_APPID + "&secret=" + ConstantWeChat.MP_SECRET;
		URI uri = URI.create(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader
						.readLine()) {
					sb.append(temp);
				}

				JSONObject object = JSONObject.fromObject(sb.toString().trim());

				return object;

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
