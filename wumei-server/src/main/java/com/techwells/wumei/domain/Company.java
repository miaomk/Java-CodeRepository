package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author miao
 */
@Data
public class Company {
    private Integer companyId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 公司名称
     */
    private String companyName;

    private String companyIntroduction;
    /**
     * 公司税号
     */
    private String companyTaxnumber;
    /**
     * 公司证明材料
     */
    private String companyProve;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 是否实名 0 未实名 1 已实名
     */
    private Integer isAuthentication;
    /**
     * 审核状态 0 审核中 1审核已通过 2审核失败
     */
    private Integer activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;

}