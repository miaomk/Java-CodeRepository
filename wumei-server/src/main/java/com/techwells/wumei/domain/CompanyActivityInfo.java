package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author miao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyActivityInfo implements Serializable {
    /**
     * 活动id
     */
    private Integer activityId;

    private Integer companyId;
    /**
     * 活动主题
     */
    private String activityTheme;
    /**
     * 活动logo
     */
    private String activityLogo;
    /**
     * 是否免费
     */
    private Boolean activityFree;
    /**
     * 活动类型
     */
    private Integer activityType;
    /**
     * 活动地址
     */
    private String activityLocation;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityStartTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityEndTime;

    /**
     * 2019/11/19 周二
     */
    private String activityTime;
    /**
     * 收藏活动人数
     */
    private int collectCount;
    /**
     * 活动状态
     */
    private int activated;
    /**
     * 浏览人数
     */
    private int pvCount;

    /**
     * 活动票列表
     */
    private List<Ticket> ticket;
    /**
     * 活动介绍
     */
    private String activityIntroduce;
}
