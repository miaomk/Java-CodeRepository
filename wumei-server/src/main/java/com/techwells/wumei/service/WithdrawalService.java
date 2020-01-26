package com.techwells.wumei.service;

import com.techwells.wumei.domain.WithdrawalRecord;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface WithdrawalService {
    /**
     * 申请提现
     *
     * @param withdrawalRecord 提现信息
     * @return int
     */
    int addWithdrawal(WithdrawalRecord withdrawalRecord);

    /**
     * 审核提现申请
     *
     * @param recordId 提现申请id(可能是数组)
     * @param status   审核状态
     * @return int
     */
    int auditWithdrawal(String recordId, String status);

    List<BillVO> getWithdrawalList(PagingTool pageTool);

    int countTotal(PagingTool pageTool);
}
