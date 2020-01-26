package com.techwells.wumei.util.wechat;


/**
 * 全局变量
 * 
 * @author shone
 * 
 */
public class ConstantWeChat {
	public static final int AGENTID = 1;

/*	public static final String APPID = "wx67b189682f0cdbc0";
	public static final String SECRET = "ad876e8626075b0bc52e0adccbd07fcb";*/
	//演绎星球
	public static final String APPID = "wx9704107d6ee309ff";
	public static final String SECRET = "e13c6f15cade08c85ef2e6e37c21e524";
	
	//public static final String MP_APPID = "wxd0cddea7ea9f4330";
	//public static final String MP_SECRET = "b4604f57fc4e16d1269abf33fb52b8b3";

	//演艺星球小程序appId与secret
	public static final String MP_APPID = "wx9704107d6ee309ff";
	public static final String MP_SECRET = "e13c6f15cade08c85ef2e6e37c21e524";

	public static final String ACCESS_TOKEN = "你的企业号_ACCESS_TOKEN";
	public static final String encodingAESKey = "";
	public static final String LINE_URL = "http://www.twxztss.cc";
	public static final String grant_type = "authorization_code";
	/**
	 * 演艺星球商户号
	 */
	public static final String MCH_ID = "1570454191";
	/**
	 * 演艺星球商户apiKey
	 */
	public static final String API_KEY = "worunyanyixingqiub475eb35f2171b3";

	

	public static final String BANK_TYPE = "WX";// 银行通道类型
	public static final String SIGN_TYPE = "MD5";// 签名方式
	public static final String PARTNER = "";// 商户号
	// private String partnerKey = "c513b82634eef322";// 商户密码
	public static final String PARTNER_KEY = "";// 商户密码
	public static final String FEE_TYPE = "1";// 现金支付币种

	public static final String INPUT_CHARSET = "UTF-8";// 字符编码
	public static final String TRADE_TYPE = "JSAPI";// 交易类型

	public static final String NOTIFY_URL = "weChatPay/callback";// 回调地址

	public static final String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一支付接口地址
	public static final String WXREFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";// 微信退款申请接口
	// 订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 微信支付成功支付后跳转的地址
	public final static String SUCCESS_URL = "";

}
