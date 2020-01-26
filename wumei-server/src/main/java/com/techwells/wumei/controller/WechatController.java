package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.*;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.*;
import com.techwells.wumei.util.wechat.ConstantWeChat;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.*;
import java.text.DecimalFormat;
import java.util.*;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


@RestController
@Slf4j
public class WechatController {
	@Resource
	private RedisUtils redisUtils;

	@Resource
	ProductService productService;

	@Resource
	private DealService dealService;

	@Resource
	private OrderService orderService;

	@Resource
	private OrderProductService orderProductService;

	@Resource
	private UserService userService;

	@Resource
	private ActivityOrderService activityOrderService;

	@Resource
	private TechnologyOrderService technologyOrderService;

	@RequestMapping(value = "/wechat/getSign")
	public @ResponseBody Object getUserByMobile(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		String jsapi_ticket = request.getParameter("jsapi_ticket");

		String url = request.getParameter("url");

		Map<String, String> ret = new HashMap<>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;

	}

	@RequestMapping(value = "/wechat/getAccessToken")
	public @ResponseBody Object getAccessToken(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();


		String code = request.getParameter("code");

		if (code == null || "".equals(code)) {
			rsInfo.setMessage("号码不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		JSONObject result = null;
		try {

			result = (JSONObject) WechatUtil.getAccessToken(code);

			String accessToken = result.getString("access_token");
			String openID = result.getString("openid");
			String refreshToken = result.getString("refresh_token");
			long expires_in = result.getLong("expires_in");

		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		rsInfo.setData(result);
		rsInfo.setMessage("获取token成功！");

		return rsInfo;
	}

	@RequestMapping(value = "/wechat/getJsApiProperty")
	public @ResponseBody Object getJsApiProperty(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		String url = request.getParameter("url");

		if (url == null || url.equals("")) {
			rsInfo.setMessage("请求页面链接不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		JSONObject result = null;

		JSONObject result2 = null;

		SortedMap<String, String> params2 = null;

		try {

			result = (JSONObject) WechatUtil.getBaseAccessToken();

			String accessToken = result.getString("access_token");

			result2 = (JSONObject) WechatUtil.getTicket(accessToken);

			String jsapiTicket = result2.getString("ticket");

			SortedMap<String, String> params = new TreeMap<String, String>();

			params.put("jsapi_ticket", jsapiTicket);
			params.put("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");
			String timestamp = create_timestamp();
			params.put("timestamp", timestamp);
			params.put("url", url);

			String sign = SignUtil.getSign(jsapiTicket, url,
					"1add1a30ac87aa2db72f57a2375d8fec", timestamp);

			params2 = new TreeMap<String, String>();
			params2.put("jsapi_ticket", jsapiTicket);

			params2.put("appId", ConstantWeChat.APPID);
			params2.put("timestamp", timestamp);
			params2.put("nonceStr", "1add1a30ac87aa2db72f57a2375d8fec");
			params2.put("signature", sign);

		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		rsInfo.setData(params2);
		rsInfo.setMessage("获取token成功！");

		return rsInfo;
	}

	@RequestMapping(value = "/wechat/getUserInfo")
	public @ResponseBody Object getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		String accessToken = request.getParameter("accessToken");
		String openID = request.getParameter("openID");
		String expiresIn = request.getParameter("expiresIn");

		if (accessToken == null || accessToken.equals("")) {
			rsInfo.setMessage("accessToken不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		if (openID == null || openID.equals("")) {
			rsInfo.setMessage("openID不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		if (expiresIn == null || expiresIn.equals("")) {
			rsInfo.setMessage("expiresIn不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		JSONObject userInfo;
		try {

			userInfo = (JSONObject) WechatUtil.getUserInfo(accessToken, openID,
					Long.parseLong(expiresIn));

		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		if (userInfo == null) {
			rsInfo.setMessage("获取第三方信息失败！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		rsInfo.setData(userInfo);
		rsInfo.setMessage("获取第三方信息成功！");

		return rsInfo;
	}

	@RequestMapping(value = "/wechat/getPrePayId")
	public @ResponseBody Object getPrePayId(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		String openID = request.getParameter("openID");
		String orderIdsMain = request.getParameter("orderIds");
		String[] orderIds = orderIdsMain.split(",");

		// String[] orderIds = {"1", "2"};


		if (openID == null || "".equals(openID)) {
			rsInfo.setMessage("openID不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		if ("".equals(orderIds)) {
			rsInfo.setMessage("订单ID数组不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		String nonce_str = create_nonce_str();
		// String timestamp = create_timestamp();

		SortedMap<String, String> params = new TreeMap<>();

		/*
		 * params.put("nonceStr", nonce_str); params.put("timestamp",
		 * timestamp); params.put("appid", "wxb8542cb0f9990521");
		 * params.put("mch_id", "1407783002"); params.put("device_info",
		 * "1000"); params.put("body", "test"); params.put("openid", openID);
		 *
		 * String apiKey = "hengtianyidaqixiaqiangdiaoxitong";
		 *
		 * String sign = WechatUtil.createSign(params, apiKey);
		 *
		 *
		 * params.put("sign",sign);
		 */

		String dealCode = RandomStringUtils.random(16,
				"1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");

		String outTradeNo = "";

		Deal deal = new Deal();

		Order order = null;

		Double turnover = 0.0;

		for (int i = 0; i < orderIds.length; i++) {
			deal.setOrderId(String.valueOf(orderIds[i]));

			if (orderIds.length == 1) {
				outTradeNo = dealCode + "_" + orderIds[0];
			} else {
				outTradeNo = dealCode;
			}

			try {
				order = orderService.getOrderByOrderId(Integer
						.parseInt(orderIds[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}

			turnover += order.getOrderAmount();

			deal.setDealCode(outTradeNo);
			deal.setDealStatus(1);

			try {
				dealService.delDeal(Integer.parseInt(orderIds[i]));
			} catch (Exception e) {
				rsInfo.setMessage("参与计划失败！");
				rsInfo.setCode("23211");
				return rsInfo;
			}

			int count;
			try {
				count = dealService.addDeal(deal);
			} catch (Exception e) {
				rsInfo.setMessage("参与计划失败！");
				rsInfo.setCode("23211");
				return rsInfo;
			}
			if (count == 0) {
				rsInfo.setMessage("参与计划失败！");
				rsInfo.setCode("28121");
				return rsInfo;
			}

		}

		/*
		 * Order order = null; try { order =
		 * orderService.getOrderByOrderId(Integer.parseInt(orderIds[0])); }
		 * catch (Exception e) { e.printStackTrace(); } DecimalFormat df = new
		 * DecimalFormat("0"); String turnover =
		 * df.format(Math.round(order.getTurnover() * 100));
		 */

		DecimalFormat df = new DecimalFormat("0");
		String turnoverMain = df.format(Math.round(turnover * 100));

		params.put("appid", ConstantWeChat.APPID);
		params.put("attach", "test");
		params.put("body", "APP body");
		params.put("mch_id", ConstantWeChat.MCH_ID);
		params.put("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");
		params.put("notify_url",
				"http://www.techwells.com/sumuji-server/wechat/receiveNotifyUrl.do");
		// params.put("notify_url",
		// "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");

		// params.put("out_trade_no", outTradeNo);
		params.put("out_trade_no", outTradeNo);
		params.put("spbill_create_ip", "101.200.36.139");
		params.put("total_fee", "1");
		params.put("trade_type", "JSAPI");

		params.put("openid", openID);

		String apiKey = ConstantWeChat.API_KEY;

		String sign = WechatUtil.createSign(params, apiKey);

		params.put("sign", sign);

		// params.put("sign", "0CB01533B8C1EF103065174F50BCA001");

		String prepayId = null;
		String result;

		try {

			// 将post的数据转换从xml格式
			String xmlObj = WechatUtil.parseXML(params);
			result = WechatUtil.sendPost(HttpClients.createDefault(),
					"https://api.mch.weixin.qq.com/pay/unifiedorder", xmlObj);
			/*
			 * if(result != null){ rsInfo.setData(result);
			 * rsInfo.setMessage("获取准备ID成功！");
			 *
			 * return rsInfo;
			 *
			 * }
			 */

			// 解析xml获取prepayId
			Document doc = DocumentHelper.parseText(result);
			Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
			String returnCode = (String) objMap.get("return_code");
			String resultCode = (String) objMap.get("result_code");
			if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
				prepayId = (String) objMap.get("prepay_id");
			}
			params.put("package", "prepay_id=" + prepayId);

			String timestamp = create_timestamp();
			params.put("timestamp", timestamp);

		} catch (Exception e) {
			rsInfo.setMessage("解析错误！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		SortedMap<String, String> params2 = new TreeMap<>();

		params2.put("appId", params.get("appid"));
		params2.put("timeStamp", params.get("timestamp"));
		params2.put("nonceStr", params.get("nonce_str"));
		params2.put("package", "prepay_id=" + prepayId);
		params2.put("signType", "MD5");
		String sign2 = WechatUtil.createSign(params2, apiKey);
		params2.put("paySign", sign2);

		rsInfo.setData(params2);
		rsInfo.setMessage("获取准备ID成功！");

		return rsInfo;
	}

	/**
	 * 活动支付接口
	 *
	 * @param request  请求
	 * @param response 相应
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/weChat/getActivityPrePayId")
	@ResponseBody
	public ResultInfo getActivityPrePayId(HttpServletRequest request,
										  HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		//微信code
		String code = request.getParameter("code");
		//订单信息JSON字符串
		String jsonActivityOrder = request.getParameter("JsonActivityOrder");

		if (StringUtil.isEmpty(code)) {
			rsInfo.setMessage("微信code不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		if (StringUtil.isEmpty(jsonActivityOrder)) {
			rsInfo.setMessage("订单信息不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		//通过code获取openId
		JSONObject openIdResult;
		try {

			openIdResult = (JSONObject) WechatUtil.getMpAccessToken(code);
			if (null == openIdResult) {

				rsInfo.setMessage("结果解析为空！");
				rsInfo.setCode("15115");
				return rsInfo;
			}
		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		String openId = openIdResult.getString("openid");
		if (StringUtil.isEmpty(openId)) {

			rsInfo.setMessage("获取用户openId出错，获得空的openId！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		//获取订单详情
		ActivityOrder activityOrder = JSON.parseObject(jsonActivityOrder, ActivityOrder.class);

		if (null == activityOrder.getActivityId()) {
			rsInfo.setMessage("活动id不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (null == activityOrder.getUserId()) {
			rsInfo.setMessage("用户id不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (StringUtil.isEmpty(activityOrder.getBuyerInformation())) {
			rsInfo.setMessage("购买人信息不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (null == activityOrder.getPayAmount()) {
			rsInfo.setMessage("支付金额不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
 		if (null == activityOrder.getPaymentMethod()) {
			rsInfo.setMessage("支付方式不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (StringUtil.isEmpty(activityOrder.getAccount())) {
			rsInfo.setMessage("支付账号不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (null == activityOrder.getTicketCount()) {
			rsInfo.setMessage("购票数不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		ActivityOrder checkActivityOrder;
		try{
			checkActivityOrder = activityOrderService.checkRepeatPurchase(activityOrder.getActivityId(), activityOrder.getUserId());
		}catch (Exception e){

			rsInfo.setMessage("查询是否重复购票异常！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		if (null != checkActivityOrder) {

			if (checkActivityOrder.getPayStatus() == 3) {
				rsInfo.setMessage("您已经有进行中的订单了,请勿重复购买！");
				rsInfo.setData(checkActivityOrder);
				rsInfo.setCode("60000");
				return rsInfo;
			} else {
				rsInfo.setMessage("您已经有已完成的订单,请勿重复购买！");
				rsInfo.setData(checkActivityOrder);
				rsInfo.setCode("70000");
				return rsInfo;
			}

		}

		int count;
		try {

			count = activityOrderService.addActivityOrder(activityOrder);

		} catch (Exception e) {

			rsInfo.setMessage("生成活动订单异常！");
			rsInfo.setCode("23211");
			return rsInfo;

		}
		if (count == 0) {

			rsInfo.setMessage("生成活动订单失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}
		SortedMap<String, String> params = new TreeMap<>();
		//生成32位随机数
		String nonce_str = create_nonce_str().replace("-", "");
		String timestamp = create_timestamp();

		String apiKey = ConstantWeChat.API_KEY;

		//生成商户订单号
		String dealCode = RandomStringUtils.random(16,
				"1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");


		Deal deal = new Deal();


		//设置订单号
		deal.setOrderId(activityOrder.getActivityOrderId());


		deal.setDealCode(dealCode);
		deal.setUserId(activityOrder.getUserId());
		deal.setMoney(activityOrder.getPayAmount());
		//支付状态 正在进行中
		deal.setDealStatus(1);
		//活动支付
		deal.setDealType(3);
		//添加交易记录
		int addCount;
		try {
			addCount = dealService.addDeal(deal);
		} catch (Exception e) {
			rsInfo.setMessage("添加交易记录异常！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (addCount == 0) {
			rsInfo.setMessage("添加交易记录失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		//小程序ID
		params.put("appid", ConstantWeChat.APPID);
		//商户号
		params.put("mch_id", ConstantWeChat.MCH_ID);
		params.put("device_info", "1000");
		//openId
		params.put("openid", openId);
		params.put("attach", "test");
		params.put("body", "APP body");
		params.put("nonce_str", nonce_str);

		//回调地址
		params.put("notify_url",
				"http://101.200.36.139:8080/wumei-server/weChat/receiveActivityNotifyUrl");
		//商户订单号
		params.put("out_trade_no", dealCode);
		//终端ip
		params.put("spbill_create_ip", "101.200.36.139");
		//支付金额
		int totalFee = (int) (activityOrder.getPayAmount() * 100);
		params.put("total_fee", String.valueOf(totalFee));
		//交易类型，小程序取值如下：JSAPI
		params.put("trade_type", "JSAPI");
		//生成签名
		String sign = WechatUtil.createSign(params, apiKey);
		params.put("sign", sign);

		String prepayId = null;
		String wxResult;

		try {

			// 将post的数据转换从xml格式
			String xmlObj = WechatUtil.parseXML(params);
			wxResult = WechatUtil.sendPost(HttpClients.createDefault(),
					"https://api.mch.weixin.qq.com/pay/unifiedorder", xmlObj);


			// 解析xml获取prepayId
			Document doc = DocumentHelper.parseText(wxResult);
			Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
			String returnCode = (String) objMap.get("return_code");
			String resultCode = (String) objMap.get("result_code");
			if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
				prepayId = (String) objMap.get("prepay_id");
			}
			params.put("package", "prepay_id=" + prepayId);


			params.put("timestamp", timestamp);

		} catch (Exception e) {

			rsInfo.setMessage("解析错误！");
			rsInfo.setCode("15114");
			return rsInfo;
		}
		if (null == prepayId) {

			rsInfo.setMessage("解析有误！");
			rsInfo.setCode("15114");
			return rsInfo;
		}


		SortedMap<String, String> params2 = new TreeMap<>();

		params2.put("appId", params.get("appid"));
		params2.put("timeStamp", params.get("timestamp"));
		params2.put("nonceStr", params.get("nonce_str"));
		params2.put("package", "prepay_id=" + prepayId);
		params2.put("signType", "MD5");
		String sign2 = WechatUtil.createSign(params2, apiKey);
		params2.put("paySign", sign2);
		params2.put("activityOrderId", activityOrder.getActivityOrderId());


		//设置两小时后过期
		redisUtils.set("prepayId:" + activityOrder.getActivityOrderId(), prepayId, 7200);

		rsInfo.setData(params2);
		rsInfo.setMessage("获取准备ID成功！");

		log.info("调用统一下单接口成功,prepayId为:{}", prepayId);
		return rsInfo;
	}


	/**
	 * 用户下单大师接口
	 *
	 * @param code                微信code
	 * @param jsonTechnologyOrder 大师下单信息
	 * @param response            相应
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/weChat/getTechnologyPrePayId")
	@ResponseBody
	public ResultInfo getTechnologyPrePayId(@RequestParam("code")String code,
											@RequestParam("jsonTechnologyOrder")String jsonTechnologyOrder,
											HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo resultInfo = new ResultInfo();

		if (StringUtil.isEmpty(code)) {
			resultInfo.setMessage("微信code不能为空！");
			resultInfo.setCode("15113");
			return resultInfo;
		}
		if (StringUtil.isEmpty(jsonTechnologyOrder)) {
			resultInfo.setMessage("大师订单信息不能为空！");
			resultInfo.setCode("15114");
			return resultInfo;
		}
		//通过code获取openId
		JSONObject openIdResult;
		try {

			openIdResult = (JSONObject) WechatUtil.getMpAccessToken(code);
			if (null == openIdResult) {

				resultInfo.setMessage("结果解析为空！");
				resultInfo.setCode("15115");
				return resultInfo;
			}
		} catch (JSONException e) {

			resultInfo.setMessage("结果解析不正确！");
			resultInfo.setCode("15114");
			return resultInfo;
		}

		String openId = openIdResult.getString("openid");
		if (StringUtil.isEmpty(openId)) {

			resultInfo.setMessage("获取用户openId出错，获得空的openId！");
			resultInfo.setCode("15114");
			return resultInfo;
		}
		//获取大师订单详情
		TechnologyOrder technologyOrder = JSON.parseObject(jsonTechnologyOrder, TechnologyOrder.class);
		//校验大师订单是否错误
		if (!SUCCESSCODE.equals(ParamCheckUtil.technologyOrderCheck(technologyOrder, resultInfo).getCode())) {
			return resultInfo;
		}

		int count;
		try {

			count = technologyOrderService.addTechnologyOrder(technologyOrder);
		} catch (Exception e) {

			resultInfo.setMessage("生成大师订单异常！");
			resultInfo.setCode("23211");
			return resultInfo;

		}
		if (count == 0) {

			resultInfo.setMessage("生成大师订单失败！");
			resultInfo.setCode("28121");
			return resultInfo;
		}
		SortedMap<String, String> params = new TreeMap<>();
		//生成32位随机数
		String nonce_str = create_nonce_str().replace("-", "");
		String timestamp = create_timestamp();

		String apiKey = ConstantWeChat.API_KEY;

		//生成商户订单号
		String dealCode = RandomStringUtils.random(16,
				"1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");


		Deal deal = new Deal();

		//设置订单号
		deal.setOrderId(String.valueOf(technologyOrder.getOrderId()));

		deal.setDealCode(dealCode);
		deal.setUserId(technologyOrder.getUserId());
		deal.setMoney(technologyOrder.getTotalPrice());
		//支付状态 正在进行中
		deal.setDealStatus(1);
		//订单类型，大师下单
		deal.setDealType(6);
		//添加交易记录
		int addCount;
		try {
			addCount = dealService.addDeal(deal);
		} catch (Exception e) {
			resultInfo.setMessage("添加大师交易记录异常！");
			resultInfo.setCode("23211");
			return resultInfo;
		}
		if (addCount == 0) {
			resultInfo.setMessage("添加大师交易记录失败！");
			resultInfo.setCode("28121");
			return resultInfo;
		}

		//小程序ID
		params.put("appid", ConstantWeChat.APPID);
		//商户号
		params.put("mch_id", ConstantWeChat.MCH_ID);
		params.put("device_info", "1000");
		//openId
		params.put("openid", openId);
		params.put("attach", "test");
		params.put("body", "APP body");
		params.put("nonce_str", nonce_str);

		//回调地址
		params.put("notify_url",
				"http://101.200.36.139:8080/wumei-server/weChat/receiveTechnologyNotify");
		//商户订单号
		params.put("out_trade_no", dealCode);
		//终端ip
		params.put("spbill_create_ip", "101.200.36.139");
		//支付金额
		int totalFee = (int) (technologyOrder.getTotalPrice() * 100);
		params.put("total_fee", String.valueOf(totalFee));
		//交易类型，小程序取值如下：JSAPI
		params.put("trade_type", "JSAPI");
		//生成签名
		String sign = WechatUtil.createSign(params, apiKey);
		params.put("sign", sign);

		String prepayId = null;
		String wxResult;

		try {

			// 将post的数据转换从xml格式
			String xmlObj = WechatUtil.parseXML(params);
			wxResult = WechatUtil.sendPost(HttpClients.createDefault(),
					"https://api.mch.weixin.qq.com/pay/unifiedorder", xmlObj);


			// 解析xml获取prepayId
			Document doc = DocumentHelper.parseText(wxResult);
			Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
			String returnCode = (String) objMap.get("return_code");
			String resultCode = (String) objMap.get("result_code");
			if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
				prepayId = (String) objMap.get("prepay_id");
			}
			params.put("package", "prepay_id=" + prepayId);


			params.put("timestamp", timestamp);

		} catch (Exception e) {

			resultInfo.setMessage("解析错误！");
			resultInfo.setCode("15114");
			return resultInfo;
		}
		if (null == prepayId) {

			resultInfo.setMessage("解析有误！");
			resultInfo.setCode("15114");
			return resultInfo;
		}


		SortedMap<String, String> params2 = new TreeMap<>();

		params2.put("appId", params.get("appid"));
		params2.put("timeStamp", params.get("timestamp"));
		params2.put("nonceStr", params.get("nonce_str"));
		params2.put("package", "prepay_id=" + prepayId);
		params2.put("signType", "MD5");
		String sign2 = WechatUtil.createSign(params2, apiKey);
		params2.put("paySign", sign2);
		params2.put("technologyOrderId", String.valueOf(technologyOrder.getOrderId()));


		//设置两小时后过期
		redisUtils.set("prepayId:" + technologyOrder.getOrderId(), prepayId, 7200);

		resultInfo.setData(params2);
		resultInfo.setMessage("获取大师下单ID成功！");

		log.info("调用统一下单接口成功,prepayId为:{}", prepayId);
		return resultInfo;
	}
	/**
	 * 活动订单重新支付
	 *
	 * @param activityOrderId 活动订单号
	 * @return ResultInfo
	 */
	@RequestMapping("/weChat/activityRePay")
	@ResponseBody
	public ResultInfo activityRePay(@RequestParam("activityOrderId") String activityOrderId) {
		ResultInfo rsInfo = new ResultInfo();
		String prePayId = redisUtils.get("prepayId:" + activityOrderId);
		SortedMap<String, String> paramsData = new TreeMap<>();

		paramsData.put("appId", ConstantWeChat.APPID);
		paramsData.put("timeStamp", create_timestamp());
		paramsData.put("nonceStr", create_nonce_str().replace("-", ""));
		paramsData.put("package", "prepay_id=" + prePayId);
		paramsData.put("signType", "MD5");
		String sign2 = WechatUtil.createSign(paramsData, ConstantWeChat.API_KEY);
		paramsData.put("paySign", sign2);

		rsInfo.setData(paramsData);
		rsInfo.setCode("200");
		return rsInfo;
	}

	/**
	 * 大师订单重新支付
	 *
	 * @param technologyOrderId 活动订单号
	 * @return ResultInfo
	 */
	@RequestMapping("/weChat/technologyOrderRePay")
	@ResponseBody
	public ResultInfo technologyOrderRePay(@RequestParam("technologyOrderId") String technologyOrderId) {
		ResultInfo resultInfo = new ResultInfo();
		String prePayId = redisUtils.get("prepayId:" + technologyOrderId);
		SortedMap<String, String> paramsData = new TreeMap<>();

		paramsData.put("appId", ConstantWeChat.APPID);
		paramsData.put("timeStamp", create_timestamp());
		paramsData.put("nonceStr", create_nonce_str().replace("-", ""));
		paramsData.put("package", "prepay_id=" + prePayId);
		paramsData.put("signType", "MD5");
		String sign2 = WechatUtil.createSign(paramsData, ConstantWeChat.API_KEY);
		paramsData.put("paySign", sign2);

		resultInfo.setData(paramsData);
		resultInfo.setCode("200");
		return resultInfo;
	}


	@RequestMapping(value = "/wechat/refund")
	public @ResponseBody Object refund(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		String orderId = request.getParameter("orderId");
		String opId = request.getParameter("opId");

		Deal rsDeal = null;
		Double turnoverMain = null;
//		try {
//			rsDeal = dealService.getDealByOrderId(Integer.parseInt(orderId));
//			
//			turnoverMain = orderService
//					.countOrderTurnover(rsDeal.getDealCode());
//			
//		} catch (Exception e) {
//			rsInfo.setMessage("获取新闻列表失败！");
//			rsInfo.setCode("23211");
//			return rsInfo;
//		}
//
//		OrderProduct orderProduct = null;
//		try {
//			orderProduct = orderProductService.getOrderProduct(Integer
//					.parseInt(opId));
//		} catch (Exception e) {
//			rsInfo.setMessage("获取新闻列表失败！");
//			rsInfo.setCode("23211");
//			return rsInfo;
//		}

		/*
		 * String outTradeNo = request.getParameter("outTradeNo");
		 * if(outTradeNo==null||outTradeNo.equals("")){
		 * rsInfo.setMessage("商户单号不能为空！"); rsInfo.setCode("15114");
		 * return rsInfo; }
		 */

		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		File file = new File("C:\\certificate\\cert\\apiclient_cert.p12");
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			keyStore.load(instream, "1407783002".toCharArray());
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, "1407783002".toCharArray())
					.build();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnrecoverableKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();

		int r1 = (int) (Math.random() * (10));
		int r2 = (int) (Math.random() * (10));
		long now = System.currentTimeMillis();
		String autoCode = String.valueOf(r1) + String.valueOf(r2)
				+ String.valueOf(now);

		String nonce_str = create_nonce_str();

		DecimalFormat df = new DecimalFormat("0");
		String salePrice = df
				.format(Math.round(1000 * 100));
		// String turnover = df.format(Math.round(rsDeal.getTurnover() * 100));
		String turnover = df.format(Math.round(turnoverMain * 100));

		SortedMap<String, String> params = new TreeMap<>();

		params.put("appid", "wxb8542cb0f9990521");
		params.put("mch_id", "1407783002");
		params.put("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");

		params.put("op_user_id", "1407783002");

		params.put("out_trade_no", rsDeal.getDealCode());
		// params.put("out_trade_no", "R0L7DZMQEXC0TYAI");
		params.put("out_refund_no", autoCode);
		// params.put("out_refund_no", "141570145451822");

		// params.put("refund_fee", orderProduct.getSalePrice());
		params.put("refund_fee", salePrice);
		params.put("total_fee", turnover);

		String apiKey = "hengtianyidaqixiaqiangdiaoxitong";

		String sign = WechatUtil.createSign(params, apiKey);

		params.put("sign", sign);

		// params.put("sign", "0CB01533B8C1EF103065174F50BCA001");

		String prepayId = null;
		String result = null;

		try {

			// 将post的数据转换从xml格式
			String xmlObj = WechatUtil.parseXML(params);
			result = WechatUtil.sendPost(httpclient,
					"https://api.mch.weixin.qq.com/secapi/pay/refund", xmlObj);
			/*
			 * if(result != null){ rsInfo.setData(result);
			 * rsInfo.setMessage("获取准备ID成功！");
			 *
			 * return rsInfo;
			 *
			 * }
			 */

			String refundFee;
			// 解析xml获取refundFee
			Document doc = DocumentHelper.parseText(result);
			Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
			String returnCode = (String) objMap.get("return_code");
			String resultCode = (String) objMap.get("result_code");
			if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
				refundFee = (String) objMap.get("refund_fee");

			} else {

				rsInfo.setMessage("数据错误！");
				rsInfo.setCode("15114");
				return rsInfo;

			}
			params.put("refundFee", refundFee);

		} catch (Exception e) {
			rsInfo.setMessage("解析错误！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		OrderProduct op = new OrderProduct();
		op.setOpId(Integer.parseInt(opId));
		op.setIsHandleReturns(2);

		int count = 0;
		try {
			//count = orderProductService.modifyReturnStatus(op);
		} catch (Exception e) {
			rsInfo.setMessage("修改退货状态失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (count == 0) {
			rsInfo.setMessage("修改退货状态失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		// 退款成功后，将订单状态改为已完成
		Order mdOrder = new Order();
		mdOrder.setOrderId(Integer.parseInt(orderId));
		mdOrder.setStatus(5);
		mdOrder.setCreatedDate(new Date());
		orderService.modifyOrder(mdOrder);

		rsInfo.setData(params);
		rsInfo.setMessage("获取准备ID成功！");
		return rsInfo;
	}



	@RequestMapping(value = "/wechat/receiveNotifyUrl")
	public @ResponseBody Object receiveNotifyUrl(HttpServletRequest request,
												 HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		BufferedReader br;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream()));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println("###  " + line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// sb为微信返回的xml

		Document doc = null;
		try {
			doc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String outTradeNo = "";
		Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
		String returnCode = (String) objMap.get("return_code");
		String resultCode = (String) objMap.get("result_code");
		if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
			outTradeNo = (String) objMap.get("out_trade_no");
		}

		List<Deal> dealList;

		HashMap<String, Object> params = new HashMap<>(16);


		if (outTradeNo != null && !"".equals(outTradeNo)) {
			params.put("outTradeNo", outTradeNo);
		}

		PagingTool pageTool = new PagingTool(1,100);
		pageTool.setParams(params);


		try {
			dealList = dealService.getDealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取新闻列表失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (dealList.size() == 0) {
			rsInfo.setMessage("新闻列表为空！");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		for (Deal deal : dealList) {

			if (deal.getDealStatus() == 1) {
				deal.setDealStatus(3);

				int count;
				try {
					count = dealService.modifyDeal(deal);
				} catch (Exception e) {
					rsInfo.setMessage("参与计划失败！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("参与计划失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}
			}

			Order ord;
			try {
				ord = orderService.getOrderByOrderId(Integer.parseInt(deal.getOrderId()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("错误");
			}
			if (ord.getStatus() == 1) {
				Order order = new Order();
				order.setOrderId(Integer.parseInt(deal.getOrderId()));
				order.setStatus(2);
				order.setPayDate(new Date());
				int count = 0;
				try {
					count = orderService.modifyOrder(order);
				} catch (Exception e) {
					rsInfo.setMessage("参与计划失败！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("参与计划失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}


			}

		}

		rsInfo.setData(dealList);
		rsInfo.setMessage("参与计划成功！");
		return rsInfo;
	}

	/**
	 * 活动支付回调接口
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/weChat/receiveActivityNotifyUrl")
	public ResultInfo receiveActivityNotifyUrl(HttpServletRequest request,
											   HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();
		log.info("微信回调进来了--------------------");
		BufferedReader br;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println("###  " + line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// sb为微信返回的xml
		if (null == sb) {
			rsInfo.setMessage("微信回调，返回空数据！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		log.info("微信回调参数为：{}", sb.toString());
		Document doc = null;
		try {

			doc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e1) {

			e1.printStackTrace();
		}
		String outTradeNo = "";
		Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
		String returnCode = (String) objMap.get("return_code");
		String resultCode = (String) objMap.get("result_code");
		if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
			outTradeNo = (String) objMap.get("out_trade_no");
		}

		List<Deal> dealList;

		HashMap<String, Object> params = new HashMap<>(16);


		if (!StringUtil.isEmpty(outTradeNo)) {
			params.put("outTradeNo", outTradeNo);
		}
		params.put("dealType", 3);

		PagingTool pageTool = new PagingTool(1, 100);
		pageTool.setParams(params);


		try {
			//查询活动支付订单
			dealList = dealService.getDealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易记录失败！");
			log.info("获取交易记录失败");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (dealList.size() == 0) {
			rsInfo.setMessage("交易记录为空！");
			log.info("交易记录为空");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		for (Deal deal : dealList) {

			//将正在将进行中的交易改为完成
			if (deal.getDealStatus() == 1) {
				deal.setDealStatus(3);

				int count;
				try {
					count = dealService.modifyDeal(deal);
				} catch (Exception e) {

					rsInfo.setMessage("修改交易记录异常！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("修改交易记录失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}
			}

			log.info("deal信息为------：{}",JSON.toJSONString(deal));

			ActivityOrder activityOrder;
			try {
				activityOrder = activityOrderService.getActivityOrderInfo(deal.getOrderId());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("错误");
			}

			log.info("activityOrder信息为------：{}",JSON.toJSONString(activityOrder));

			if (activityOrder.getPayStatus() == 3) {
				activityOrder.setPayStatus(1);
				activityOrder.setOrderStatus(1);

				int count;
				try {
					count = activityOrderService.modifyActivityOrder(activityOrder);
				} catch (Exception e) {
					rsInfo.setMessage("修改活动订单信息异常！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("修改活动订单信息失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}

			}
			redisUtils.del("prepayId:" + activityOrder.getActivityOrderId());

		}

		rsInfo.setData(dealList);
		rsInfo.setMessage("回调成功！");
		log.info("回调成功！！");
		return rsInfo;
	}

	/**
	 * 大师订单回调接口
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/weChat/receiveTechnologyNotify")
	public ResultInfo receiveTechnologyNotify(HttpServletRequest request,
											   HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();
		log.info("微信回调进来了--------------------");
		BufferedReader br;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println("###  " + line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// sb为微信返回的xml
		if (null == sb) {
			rsInfo.setMessage("微信回调，返回空数据！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		log.info("微信回调参数为：{}", sb.toString());
		Document doc = null;
		try {

			doc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e1) {

			e1.printStackTrace();
		}
		String outTradeNo = "";
		Map<String, Object> objMap = WechatUtil.Dom2Map(doc);
		String returnCode = (String) objMap.get("return_code");
		String resultCode = (String) objMap.get("result_code");
		if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
			outTradeNo = (String) objMap.get("out_trade_no");
		}

		List<Deal> dealList;

		HashMap<String, Object> params = new HashMap<>(16);


		if (!StringUtil.isEmpty(outTradeNo)) {
			params.put("outTradeNo", outTradeNo);
		}
		params.put("dealType", 6);

		PagingTool pageTool = new PagingTool(1, 100);
		pageTool.setParams(params);


		try {
			//查询活动支付订单
			dealList = dealService.getDealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易记录失败！");
			log.info("获取交易记录失败");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (dealList.size() == 0) {
			rsInfo.setMessage("交易记录为空！");
			log.info("交易记录为空");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		for (Deal deal : dealList) {

			//将正在将进行中的交易改为完成
			if (deal.getDealStatus() == 1) {
				deal.setDealStatus(3);

				int count;
				try {
					count = dealService.modifyDeal(deal);
				} catch (Exception e) {

					rsInfo.setMessage("修改交易记录异常！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("修改交易记录失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}
			}

			log.info("deal信息为------：{}", JSON.toJSONString(deal));

			TechnologyOrder technologyOrder;
			try {
				technologyOrder = technologyOrderService.getTechnologyOrderInfo(Integer.parseInt(deal.getOrderId()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("错误");
			}

			log.info("technologyOrder信息为------：{}", JSON.toJSONString(technologyOrder));

			if (technologyOrder.getPayStatus() == 3) {
				technologyOrder.setPayStatus(1);
				technologyOrder.setOrderStatus(1);

				int count;
				try {
					count = technologyOrderService.modifyTechnologyOrder(technologyOrder);
				} catch (Exception e) {
					rsInfo.setMessage("修改大师订单信息异常！");
					rsInfo.setCode("23211");
					return rsInfo;
				}
				if (count == 0) {
					rsInfo.setMessage("修改大师订单信息失败！");
					rsInfo.setCode("28121");
					return rsInfo;
				}

			}
			redisUtils.del("prepayId:" + technologyOrder.getOrderId());

		}

		rsInfo.setData(dealList);
		rsInfo.setMessage("回调成功！");
		log.info("回调成功！！");
		return rsInfo;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);

	}


	@RequestMapping(value = "/wechat/getMpAccessToken")
	public @ResponseBody Object getMpAccessToken(HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();


		String code = request.getParameter("code");

		if (code == null || code.equals("")) {
			rsInfo.setMessage("码号不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		JSONObject result = null;
		try {

			result = (JSONObject) WechatUtil.getMpAccessToken(code);

		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;

		}

		rsInfo.setData(result);
		rsInfo.setMessage("获取token成功！");

		return rsInfo;
	}


}
