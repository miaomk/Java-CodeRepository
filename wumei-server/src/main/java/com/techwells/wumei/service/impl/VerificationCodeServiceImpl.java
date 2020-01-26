package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.VerificationCodeMapper;
import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.service.VerificationCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 验证码业务实现类
 *
 * @author miao
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private VerificationCodeMapper verificationCodeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVerificationCode(VerificationCode verificationCode) {

        return verificationCodeMapper.insertSelective(verificationCode);
    }

    @Override
    public VerificationCode getVerificationByMobile(String mobile, Integer codeType) {
        VerificationCode verificationCode;
        try {
            verificationCode = verificationCodeMapper.selectByMobile(mobile, codeType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取验证码详情异常！");
        }
        return verificationCode;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteVerification(String userName) {
        return verificationCodeMapper.deleteByPrimaryKey(userName);
    }
}
