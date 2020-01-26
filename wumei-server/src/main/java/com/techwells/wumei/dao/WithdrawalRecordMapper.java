package com.techwells.wumei.dao;

import com.techwells.wumei.domain.WithdrawalRecord;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawalRecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(WithdrawalRecord record);

    int insertSelective(WithdrawalRecord record);

    WithdrawalRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(WithdrawalRecord record);

    int updateByPrimaryKey(WithdrawalRecord record);

    /**
     * 批量审核提现申请
     *
     * @param recordIds 提现申请id数组
     * @param status    审核状态
     * @return int
     */
    int batchAudit(@Param("recordIds") String[] recordIds, @Param("activated") String status);

    /**
     * 查询用户提现金额
     *
     * @param userId 用户id
     * @param withdrawalType 提现类型 1 技术人员与公司提现 2 商户提现
     * @return BigDecimal
     */
    BigDecimal getWithdrawalCount(@Param("userId") String userId,@Param("withdrawalType") Integer withdrawalType);

    /**
     * 查询用户提现列表
     * @param userId 用户id
     * @param withdrawalType 提现类型 1 技术人员与公司提现 2 商户提现
     * @return
     */
    List<BillVO> getWithdrawalList(@Param("userId") String userId,@Param("withdrawalType") Integer withdrawalType);

    int countTotal(@Param("userId") String userId,@Param("withdrawalType") Integer withdrawalType);
}