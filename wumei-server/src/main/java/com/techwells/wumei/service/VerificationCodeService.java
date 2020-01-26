package com.techwells.wumei.service;

import com.techwells.wumei.domain.VerificationCode;

/**
 * 验证码业务接口
 *
 * @author miao
 */
public interface VerificationCodeService {
    /**
     * 生成验证码记录
     *
     * @param verificationCode 验证码信息
     * @return int
     */
    int addVerificationCode(VerificationCode verificationCode);

    /**
     * 通过手机号和类型查询相关信息
     *
     * @param mobile   手机号
     * @param codeType 验证码类型1 登录 2 注册 3 忘记密码 4 修改密码 5主办方实名认证
     * @return VerificationCode
     */
    VerificationCode getVerificationByMobile(String mobile, Integer codeType);

    int deleteVerification(String userName);
}
