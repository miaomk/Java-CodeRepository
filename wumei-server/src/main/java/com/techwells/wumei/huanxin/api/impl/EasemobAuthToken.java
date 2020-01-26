package com.techwells.wumei.huanxin.api.impl;

import com.techwells.wumei.huanxin.api.AuthTokenAPI;
import com.techwells.wumei.huanxin.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
