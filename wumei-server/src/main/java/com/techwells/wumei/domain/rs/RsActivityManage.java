package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsActivityManage implements Serializable {

    private int activityId;

    private int companyId;

    private String activityName;

    private String activityIcon;

    private String activityLocation;

    private String activityPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityEndTime;

    private String startTime;

    private Boolean isFree;
    /**
     * 票价
     */
    private double ticketPrice;
    /**
     * 报名费用
     */
    private double signUpPrice;
    /**
     * 报名人数
     */
    private int signUpCount;

    private int activityStatus;
}
