package com.techwells.wumei.domain.bo;

import lombok.Data;

/**
 * 实名认证BO
 *
 * @author miao
 */
@Data
public class AuthenticationBO {
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 性别
     */
    private String sex;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String bankNumber;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 实名认证的用户ID
     */
    private Integer userId;
    /**
     * 主办方id/技术人员id
     */
    private Integer relationId;
    /**
     * 认证类型 1主办方实名 2 技术人员实名
     */
    private Integer authenticationType;
}
