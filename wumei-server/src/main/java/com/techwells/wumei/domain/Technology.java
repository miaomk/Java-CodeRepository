package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 技术人员实体类
 * @author miao
 */
@Data
public class Technology {

    private Integer userId;
    /**
     * 技术人员职业类型
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
     * 性别 1 男 2 女
     */
    private Integer technologyGender;
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
     * 荣耀值 默认 50 技术人员每完成一笔订单荣耀值+1
     */
    private Integer glory;

    private String certificateImage;

    private Integer isRecommend;

    private String workCase;

    private Integer activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;
}