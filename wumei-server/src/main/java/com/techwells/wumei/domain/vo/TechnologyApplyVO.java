package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 我的需求申请实体类
 *
 * @author miao
 */
@Data
public class TechnologyApplyVO implements Serializable {
    /**
     * 申请id
     */
    private Integer applyId;
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
     * 申请状态 0 等待聘用 1 已被聘用 2 未被聘用 3 确认聘用 4 拒绝聘用 5 进行中 6 已结束 7 已评价
     */
    private Integer activated;
    /**
     * 是否评价
     */
    private Integer isEvaluate;
}
