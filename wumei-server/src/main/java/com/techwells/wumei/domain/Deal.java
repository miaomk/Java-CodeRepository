package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Deal {
    private Integer dealId;

    private Integer userId;

    private String orderId;

    private String dealCode;

    private Integer dealType;

    private Integer strategyId;

    private Double money;

    private Integer score;

    private Integer dealStatus;

    private Integer payType;

    private String payCode;

    private String account;

    private String realName;

    private Boolean activated;

    private Boolean deleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;
}