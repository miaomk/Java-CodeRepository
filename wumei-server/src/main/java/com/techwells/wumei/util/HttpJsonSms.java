package com.techwells.wumei.util;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.dahantc.api.sms.json.JSONHttpClient;

public class HttpJsonSms {
	private static final Logger LOG = Logger.getLogger(HttpJsonSms.class);
	private static String account = "dh62061";// 用户名（必填）
	private static String password = "6ej6nG14";// 密码（必填,明文）
	// private static String phone = "139********"; // 手机号码（必填,多条以英文逗号隔开）
	public static String sign = "【蓝天旅游】"; // 短信签名（必填）
	public static String subcode = ""; // 子号码（可选）
	public static String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
	public static String sendtime = ""; // 定时发送时间（可选）

	// public static void main(String[] args) {
	public static void sendSms(String phone, String code) {
		try {
			String content = "您的验证码为:" + code + "，验证码一分钟之内有效，如非本人操作，请忽略本条信息";// 短信内容（必填）

			JSONHttpClient jsonHttpClient = new JSONHttpClient(
					"http://www.dh3t.com");
			jsonHttpClient.setRetryCount(1);
			String sendhRes = jsonHttpClient.sendSms(account, password, phone,
					content, sign, subcode);
			LOG.info("提交单条普通短信响应：" + sendhRes);

			//
			// List<SmsData> list = new ArrayList<SmsData>();
			// list.add(new
			// SmsData("11111111,15711666133,1738786465,44554545",
			// content, msgid, sign, subcode, sendtime));
			// list.add(new SmsData("15711777134", content, msgid, sign,
			// subcode, sendtime));
			// String sendBatchRes = jsonHttpClient.sendBatchSms(account,
			// password, list);
			// LOG.info("提交批量短信响应：" + sendBatchRes);
			//
			// String reportRes = jsonHttpClient.getReport(account, password);
			// LOG.info("获取状态报告响应：" + reportRes);
			//
			// String smsRes = jsonHttpClient.getSms(account, password);
			// LOG.info("获取上行短信响应：" + smsRes);
			//
			// String balanceRes = jsonHttpClient.getBalance(account, password);
			// LOG.info("获取余额短信响应：" + balanceRes);

		} catch (Exception e) {

			LOG.error("应用异常", e);
		}

	}
}
