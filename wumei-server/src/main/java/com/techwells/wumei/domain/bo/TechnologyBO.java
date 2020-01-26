package com.techwells.wumei.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 大师认证实体类
 *
 * @author miao
 */
@Data
public class TechnologyBO implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 大师职业类型
     */
    private Integer technologyOccupation;
    /**
     * 工作年长
     */
    private Integer workExperience;
    /**
     * 个人经历介绍
     */
    private String personalIntroduce;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 年龄
     */
    private Integer technologyAge;
    /**
     * 薪资/天
     */
    private BigDecimal salary;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 工作案例列表
     */
    private List<TechnologyCaseBO> caseList;
}
