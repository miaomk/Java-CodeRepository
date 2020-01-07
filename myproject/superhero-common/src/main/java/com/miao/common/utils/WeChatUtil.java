package com.miao.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信工具类
 *
 * @author miao
 */
public class WeChatUtil {

    /**
     * 获取微信接口调用凭证
     *
     * @return access_token 获取到的凭证 expires_in 凭证有效时间，单位：秒
     */
    public String getAccessToken() {
        //请求地址
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        //参数
        Map<String, String> map = new HashMap<>(16);
        map.put("grant_type", "client_credential");
        map.put("appid", MPWXConfig.APPID);
        map.put("secret", MPWXConfig.SECRET);
        //
        return HttpClientUtil.doGet(url, map);
    }
}
