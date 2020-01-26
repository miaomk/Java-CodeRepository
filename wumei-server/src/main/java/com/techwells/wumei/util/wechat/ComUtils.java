package com.techwells.wumei.util.wechat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(ComUtils.class);

	// 表示请求器是否已经做了初始化工作
	private boolean hasInit = false;

	// 连接超时时间，默认10秒
	private int socketTimeout = 10000;

	// 传输超时时间，默认30秒
	private int connectTimeout = 30000;

	// 请求器的配置
	private RequestConfig requestConfig;

	// HTTP请求器
	private CloseableHttpClient httpClient;

	private void init(String pfxFileName, String pfxPwd) throws IOException,
			KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyManagementException {
		logger.debug("refund init start");
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		InputStream inCfg = getClass().getClassLoader().getResourceAsStream(
				pfxFileName);
		BufferedInputStream ksbufin = new BufferedInputStream(inCfg);

		try {
			keyStore.load(ksbufin, pfxPwd.toCharArray());// 设置证书密码
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			// instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, pfxPwd.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

		// 根据默认超时限制初始化requestConfig
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();

		hasInit = true;
		logger.debug("refund init end");
	}

	// 发送退款请求
	public String postRefund(String url, String xml, String pfxFileName,
			String pfxPwd) throws UnrecoverableKeyException,
			KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, IOException {
		logger.debug(" postRefund start");
		if (!hasInit) {
			init(pfxFileName, pfxPwd);
		}
		String result = null;

		HttpPost httpPost = new HttpPost(url);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xml, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		// 设置请求器的配置
		httpPost.setConfig(requestConfig);

		try {
			logger.debug(" postRefund get response");
			HttpResponse response = httpClient.execute(httpPost);
			logger.debug(" postRefund get response : " + response);
			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			logger.error("http get throw ConnectionPoolTimeoutException(wait time out)");

		} catch (ConnectTimeoutException e) {
			logger.error("http get throw ConnectTimeoutException");

		} catch (SocketTimeoutException e) {
			logger.error("http get throw SocketTimeoutException");

		} catch (Exception e) {
			logger.error("http get throw Exception");

		} finally {
			httpPost.abort();
		}
		return result;
	}

	/**
	 * 发送请求返回结果
	 * 
	 * @param strUrl
	 * @param param
	 * @return
	 */
	public static String postSend(String strUrl, String param) {

		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			// POST方法时使用
			OutputStream outputStream = connection.getOutputStream();

			outputStream.write(param.getBytes("UTF-8"));
			outputStream.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * 添加键对值
	 * 
	 * @param returns
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public static StringBuffer addParam(StringBuffer returns, String paramId,
			String paramValue) {
		if (returns != null) {
			if (!"".equals(paramValue) && paramValue != null) {
				returns.append("&" + paramId + "=" + paramValue);
			}
		} else {
			returns = new StringBuffer();
			if (!"".equals(paramValue) && paramValue != null) {
				returns.append(paramId + "=" + paramValue);
			}
		}
		return returns;
	}

	public static String fetchStringFromParams(Map<String, String[]> params,
			String name) {
		String[] resultArray = params.get(name);
		return fetchFirstElementFromStringArray(resultArray);
	}

	public static String fetchFirstElementFromStringArray(String[] stringArray) {
		if (ArrayUtils.isEmpty(stringArray)) {
			return StringUtils.EMPTY;
		}
		String result = stringArray[0];
		if (StringUtils.isBlank(result)) {
			return StringUtils.EMPTY;
		}
		return result;
	}

	public static Date fetchDateFromParams(Map<String, String[]> params,
			String name, String dateFormat) {
		String[] resultArray = params.get(name);
		String dateString = fetchFirstElementFromStringArray(resultArray);
		return parseIsoDateTime(dateString, dateFormat);
	}

	private static Date parseIsoDateTime(final String isoDateString,
			final String dateFormat) {
		final DateFormat df = new SimpleDateFormat(dateFormat);
		df.setTimeZone(TimeZone.getDefault());
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
}
