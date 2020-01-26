package com.techwells.wumei.huanxin.api.impl;


import java.io.File;

import com.techwells.wumei.huanxin.api.FileAPI;
import com.techwells.wumei.huanxin.comm.EasemobAPI;
import com.techwells.wumei.huanxin.comm.OrgInfo;
import com.techwells.wumei.huanxin.comm.ResponseHandler;
import com.techwells.wumei.huanxin.comm.TokenUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.UploadAndDownloadFilesApi;


public class EasemobFile implements FileAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private UploadAndDownloadFilesApi api = new UploadAndDownloadFilesApi();
    @Override
    public Object uploadFile(final Object file) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatfilesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),(File)file,true);
             }
        });
    }

    @Override
    public Object downloadFile(final String fileUUID,final  String shareSecret,final Boolean isThumbnail) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
               return api.orgNameAppNameChatfilesUuidGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),fileUUID,shareSecret,isThumbnail);
            }
        });
    }
}
