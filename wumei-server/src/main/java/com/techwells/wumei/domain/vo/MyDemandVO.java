package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 我的发布需求实体类
 *
 * @author miao
 */
@Data
public class MyDemandVO implements Serializable {
    /**
     * 需求id
     */
    private Integer demandId;
    /**
     * 需求标题
     */
    private String demandTitle;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
    /**
     * 需求时间 例：2020-01-08至2020-01-09
     */
    private String demandTime;
    /**
     * 需求城市
     */
    private String demandCity;
    /**
     * 技术人员类型
     */
    private String technologyType;
    /**
     * 需求状态 0 待审核 1 发布中 2 有人申请 3 已接单 4 进行中 5 已完成 6已评价
     */
    private Integer activated;
    /**
     * 是否评价
     */
    private Integer isEvaluate;
    /**
     * 申请人数
     */
    private Integer applyCount;
}
