package com.techwells.wumei.util.wechat;


import lombok.Data;

import java.util.List;

/**
 * 通过网页授权获取的用户信息
 *
 * @author shone
 * @date 2016年7月23日15:09:29
 */
@Data
public class UserInfo {
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 性别（1是男性，2是女性，0是未知
     */
    private int sex;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 用户头像链接
     */
    private String headimgurl;
    /**
     * 用户特权信息
     */
    private List<String> privilege;
    /**
     * 语言
     */
    private String language;
}
