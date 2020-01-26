package com.techwells.wumei.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techwells.wumei.util.AuthUtil;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/wxCallBack")
public class CallBackServletPC extends HttpServlet{
	
	private static final long serialVersionUID = 6651214488788230639L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		System.out.println("------------------>"+"回调开始..."+"<-------------------");
        String code=req.getParameter("code");
        System.out.println("------------------>"+"获取网页授权的code="+code+"<-------------------");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID+
                "&secret=" +AuthUtil.APPSECRET+
                "&code=" +code+
                "&grant_type=authorization_code";
			JSONObject jsonObject;
			try {
				jsonObject = AuthUtil.doGetJson(url);
				String openid=jsonObject.getString("openid");
				String token=jsonObject.getString("access_token");
				String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
						"&openid=" +openid+
						"&lang=zh_CN";
				JSONObject userInfo=AuthUtil.doGetJson(infoUrl);
				System.out.println(userInfo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
        
}
