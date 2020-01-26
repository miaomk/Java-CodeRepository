package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techwells.wumei.domain.Activity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户活动实体类
 *
 * @author miao
 */
@Data
public class RsActivityUser extends Activity implements Serializable {

    private String startTime;

    /**
     * 1 待举办 2 进行中 3 已结束
     */
    private int activityStatus;
    /**
     * 活动订单id
     */
    private String activityOrderId;
    /**
     * 活动订单创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime orderCreateDate;
    /**
     * 支付金额
     */
    private String payAmount;
    /**
     * 购票数量
     */
    private String ticketCount;


}
