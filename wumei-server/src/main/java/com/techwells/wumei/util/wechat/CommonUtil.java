package com.techwells.wumei.util.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.conn.ssl.SSLContexts;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(CommonUtil.class);

	/**
	 * 
	 * @param requestUrl
	 *            接口地址
	 * @param requestMethod
	 *            请求方法：POST、GET...
	 * @param output
	 *            接口入参
	 * @param needCert
	 *            是否需要数字证书
	 * @return
	 */
	public static StringBuffer httpsRequest(String requestUrl,
			String requestMethod, String output, boolean needCert)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			KeyManagementException, MalformedURLException, IOException,
			ProtocolException, UnsupportedEncodingException {

		URL url = new URL(requestUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();

		// 是否需要数字证书
		if (needCert) {
			// 设置数字证书
			setCert(connection);
		}
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(requestMethod);
		if (null != output) {
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(output.getBytes("UTF-8"));
			outputStream.close();
		}

		// 从输入流读取返回内容
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}

		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		connection.disconnect();
		return buffer;
	}

	/**
	 * 给HttpsURLConnection设置数字证书
	 * 
	 * @param connection
	 * @throws IOException
	 */
	private static void setCert(HttpsURLConnection connection)
			throws IOException {
		FileInputStream instream = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			// 读取本机存放的PKCS12证书文件
			instream = new FileInputStream(new File("certPath")); // certPath:数字证书路径

			// 指定PKCS12的密码(商户ID)
			keyStore.load(instream, "商户ID".toCharArray());
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, "商户ID".toCharArray()).build();
			// 指定TLS版本
			SSLSocketFactory ssf = sslcontext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instream.close();
		}
	}

	/**
	 * 如果返回JSON数据包,转换为 JSONObject
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @param needCert
	 * @return
	 */
	public static JSONObject httpsRequestToJsonObject(String requestUrl,
			String requestMethod, String outputStr, boolean needCert) {
		JSONObject jsonObject = null;
		try {
			StringBuffer buffer = httpsRequest(requestUrl, requestMethod,
					outputStr, needCert);
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("连接超时：" + ce.getMessage());
		} catch (Exception e) {
			logger.error("https请求异常：" + e.getMessage());
		}

		return jsonObject;
	}

	/**
	 * 如果返回xml数据包，转换为Map<String, String>
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @param needCert
	 * @return
	 */
	public static Map<String, String> httpsRequestToXML(String requestUrl,
			String requestMethod, String outputStr, boolean needCert) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			StringBuffer buffer = httpsRequest(requestUrl, requestMethod,
					outputStr, needCert);
			result = parseXml(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("连接超时：" + ce.getMessage());
		} catch (Exception e) {
			logger.error("https请求异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * xml转为map
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xml);

			Element root = document.getRootElement();
			List<Element> elementList = root.elements();

			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return map;
	}

	/**
	 * xml转为map
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> parseXml(HttpServletRequest request) {

		logger.debug("parseXml");
		// 解析结果存储在HashMap
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			InputStream inputStream;

			inputStream = request.getInputStream();

			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			System.out.println(document);
			logger.debug("document=" + document);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.debug("map=" + map);
		return map;
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return request.getRemoteAddr();
		}
		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
}
