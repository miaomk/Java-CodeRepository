package com.techwells.wumei.huanxin.api.impl;


import com.techwells.wumei.huanxin.api.SendMessageAPI;
import com.techwells.wumei.huanxin.comm.EasemobAPI;
import com.techwells.wumei.huanxin.comm.OrgInfo;
import com.techwells.wumei.huanxin.comm.ResponseHandler;
import com.techwells.wumei.huanxin.comm.TokenUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
