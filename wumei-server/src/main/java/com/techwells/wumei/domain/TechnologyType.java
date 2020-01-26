package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 技术人员类型实体类
 *
 * @author miao
 */
@Data
public class TechnologyType implements Serializable {
    private Integer typeId;

    private String typeName;

    private BigDecimal minSalary;

    private BigDecimal maxSalary;

    private Integer activated;

    private Integer deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}