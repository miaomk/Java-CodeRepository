package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityOrder {
    private String activityOrderId;

    private Integer activityId;

    private Integer userId;

    private String buyerInformation;

    private Integer paymentMethod;

    private String account;

    private Integer ticketCount;

    private Long ticketNo;

    private Double payAmount;

    private Integer payStatus;

    private Integer orderStatus;

    private String qrCode;

    private String remark;

    private Integer activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

}