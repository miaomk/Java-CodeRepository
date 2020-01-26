package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
public class Activity {
    private Integer activityId;

    private Integer companyId;

    private String activityTheme;

    private String activityLogo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityEndTime;

    private Boolean activityFree;

    private Double activityFee;

    private String activityLocation;

    private String activityIntroduce;

    private String activityPhone;

    private Integer activated;

    private Boolean refundRule;

    private Boolean trafficPlan;

    private Integer activityType;

    private Integer activityPv;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;

    private Ticket ticket;
    /**
     * json 格式的报名表单
     */
    private String form;
}