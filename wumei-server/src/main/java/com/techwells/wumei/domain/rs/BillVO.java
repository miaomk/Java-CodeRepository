package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
public class BillVO {
    /**
     * 流水号
     */
    private String flowId;
    /**
     * 交易类型 1 收入 2 提现
     */
    private Integer billType;
    /**
     * 交易金额
     */
    private BigDecimal payment;
    /**
     * 交易时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

}
