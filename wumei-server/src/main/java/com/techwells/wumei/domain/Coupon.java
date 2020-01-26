package com.techwells.wumei.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Coupon {
    private Integer couponId;

    private Integer merchantId;

    private Integer couponType;

    private String couponName;

    private Integer platform;

    private Integer count;

    private BigDecimal amount;

    private Integer perLimit;

    private BigDecimal minPoint;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer useType;

    private Integer productType;

    private String productIds;

    private String description;

    private Integer publishCount;

    private Integer useCount;

    private Integer receiveCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enableTime;

    private String code;

    private Integer userLevel;

    private Boolean activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;

}