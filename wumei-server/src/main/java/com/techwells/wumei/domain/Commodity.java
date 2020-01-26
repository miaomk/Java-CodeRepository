package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
public class Commodity {
    private Integer commodityId;

    private Integer companyId;

    private String commodityName;

    private String commodityIcon;

    private Integer commodityType;

    private BigDecimal rent;

    private String unit;

    private String location;

    private String introduce;

    private String specification;

    private String model;
    /**
     * 库存
     */
    private Integer stock;

    private String brand;

    private String power;

    private Integer recommend;
    /**
     * 租赁状态 0 未租出 1 已租出
     */
    private Integer rentStatus;
    /**
     * 上下架  0 未上架 1 上架 2 下架
     */
    private Integer activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;
}