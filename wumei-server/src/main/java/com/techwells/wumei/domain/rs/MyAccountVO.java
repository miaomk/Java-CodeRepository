package com.techwells.wumei.domain.rs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author miao
 */
@Data
public class MyAccountVO {

    private Integer userId;
    /**
     * 总收入
     */
    private BigDecimal totalIncome;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 已提现金额
     */
    private BigDecimal withdrawalCount;

    /**
     * 账单记录
     */
    List<BillVO> billList;

}
