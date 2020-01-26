package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Receive {
    private Integer receiveId;

    private Integer couponId;

    private Integer userId;

    private Integer receiveType;

    private Integer useStatus;

    private Date useTime;

    private Integer orderId;

    private Boolean activated;

    private Boolean deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedDate;

}