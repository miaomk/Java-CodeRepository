package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
public class OrderUserInfoVO implements Serializable {

    private Long activityOrderId;

    private Integer activityId;

    private Integer userId;

    private String userName;

    private String userIcon;

    private String buyerInformation;

    private Double payAmount;

    private Integer phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    private Boolean isFree;

}
