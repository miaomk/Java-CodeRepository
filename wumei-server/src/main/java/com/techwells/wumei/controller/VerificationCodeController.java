package com.techwells.wumei.controller;

import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.service.VerificationCodeService;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.SendSmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 验证码Controller
 *
 * @author miao
 */
@Controller
public class VerificationCodeController {

    @Resource
    private VerificationCodeService verificationCodeService;

    /**
     * 生成验证码
     *
     * @param mobile   手机号
     * @param codeType 验证码类型1 登录 2 注册 3 忘记密码 4 修改密码 5主办方实名认证 6 技术人员认证
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/verificationCode/addVerificationCode")
    public ResultInfo addVerificationCode(@RequestParam(value = "mobile") String mobile,
                                          @RequestParam(value = "codeType") Integer codeType) {
        ResultInfo rsInfo = new ResultInfo();

        if (null == codeType) {
            rsInfo.setMessage("验证码类型不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (StringUtils.isBlank(mobile)) {
            rsInfo.setMessage("手机号不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        //先查询是否已经发送验证码，如果有，清空验证码
        VerificationCode verificationByMobile = verificationCodeService.getVerificationByMobile(mobile, codeType);
        if (null != verificationByMobile) {
            //清空验证码
            try {
                verificationCodeService.deleteVerification(verificationByMobile.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
                rsInfo.setMessage("清空验证码异常！");
                rsInfo.setCode("10001");
                return rsInfo;
            }
        }

        //生成六位随机数验证码
        String verCode = SendSmsUtil.getRandom();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUserName(mobile);
        verificationCode.setVerCode(verCode);
        verificationCode.setCodeType(codeType);
        try {

            SendSmsUtil.sendCodeByAuthentication(mobile, verCode);

        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("发送验证码出错！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        int count;
        try {

            count = verificationCodeService.addVerificationCode(verificationCode);

        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("生成验证码异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {

            rsInfo.setMessage("生成验证码成功！");
            rsInfo.setData(verCode);
        } else {

            rsInfo.setMessage("生成验证码失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        return rsInfo;
    }
}
