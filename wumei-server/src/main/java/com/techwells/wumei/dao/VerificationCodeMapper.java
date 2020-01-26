package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface VerificationCodeMapper {
    int deleteByPrimaryKey(String userName);

    int insert(VerificationCode record);

    int insertSelective(VerificationCode record);

    VerificationCode selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(VerificationCode record);

    int updateByPrimaryKey(VerificationCode record);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<VerificationCode> selectVerificationCodeList(PagingTool pagingTool);

    /**
     * 通过手机号和类型查询相关信息
     *
     * @param mobile   手机号
     * @param codeType 验证码类型1 登录 2 注册 3 忘记密码 4 修改密码 5主办方实名认证
     * @return VerificationCode
     */
    VerificationCode selectByMobile(@Param("mobile") String mobile, @Param("codeType") Integer codeType);
}