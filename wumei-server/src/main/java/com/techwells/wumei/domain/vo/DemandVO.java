package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户需求详情实体类
 *
 * @author miao
 */
@Data
public class DemandVO implements Serializable {
    /**
     * 需求id
     */
    private Integer demandId;
    /**
     * 需求标题
     */
    private String demandTitle;
    /**
     * 浏览人数
     */
    private Integer pvCount;
    /**
     * 申请人数
     */
    private Integer applyCount;
    /**
     * 薪资
     */
    private BigDecimal salary;
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
     * 需求时间 例：2020-01-08至2020-01-08
     */
    private String demandTime;
    /**
     * 需求天数
     */
    private Integer demandDayCount;
    /**
     * 需求地点
     */
    private String demandLocation;
    /**
     * 技术人员类型
     */
    private String technologyType;
    /**
     * 技术人员类型
     */
    private Integer technologyOccupation;
    /**
     * 发布需求用户id
     */
    private Integer userId;
    /**
     * 发布需求用户头像
     */
    private String userIcon;
    /**
     * 发布需求用户姓名
     */
    private String contact;
    /**
     * 联系方式
     */
    private String contacts;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 需求详情
     */
    private String detail;
    /**
     * 需求图片
     */
    private String demandImage;
}
