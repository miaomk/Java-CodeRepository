package com.techwells.wumei.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 用户发布需求实体类
 *
 * @author miao
 */
@Data
public class DemandBO {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 需求标题
     */
    private String demandTitle;
    /**
     * 需求城市
     */
    private String demandLocation;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endTime;
    /**
     * 技术人员类型
     */
    private Integer technologyType;
    /**
     * 薪资
     */
    private BigDecimal salary;
    /**
     * 联系人姓名
     */
    private String contacts;
    /**
     * 联系人手机号
     */
    private String contact;
    /**
     * 需求描述
     */
    private String detail;
    /**
     * 需求图
     */
    private String demandImage;
}
