package com.techwells.wumei.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techwells.wumei.util.AuthUtil;

@WebServlet("/wxLogin")
public class LoginServletPC extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6009528858598592412L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("------------------>"+"开始授权"+"<-------------------");
		System.out.println("------------------>"+"授权中..."+"<-------------------");
		String backUrl="http://www.elantripchina.com:8080/elantrip-server/wxCallBack";
		//第一步获取code
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid="+ AuthUtil.APPID
				+ "&redirect_uri=" +URLEncoder.encode(backUrl,"UTF-8")
				+ "&response_type=code"
				+ "&scope=snsapi_login"
				+ "&state=STATE#wechat_redirect";
		resp.sendRedirect(url);
        System.out.println("------------------>"+"即将跳转到回调..."+"<-------------------");
	}
}
