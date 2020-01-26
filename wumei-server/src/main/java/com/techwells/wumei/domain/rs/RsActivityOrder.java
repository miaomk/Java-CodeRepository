package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techwells.wumei.domain.ActivityOrder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动订单详情实体类
 *
 * @author miao
 */
@Data
public class RsActivityOrder extends ActivityOrder {


    private Integer activityId;

    private String activityName;

    private Integer activityType;

    private String activityLogo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityEndTime;
    /**
     * 例如： 10/24 12:00
     */
    private String activityTime;

    private String activityLocation;

    private String name;

    private String phone;

    private String ticketName;
}
