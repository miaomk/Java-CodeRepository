package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 大师详情中的案例实体类
 *
 * @author miao
 */
@Data
public class TechnologyCaseVO implements Serializable {

    /**
     * 案例id
     */
    private Integer caseId;

    /**
     * 案例名
     */
    private String caseName;

    /**
     * 案例图片
     */
    private String imageUrl;

    /**
     * 案例图片封面
     */
    private String imageCover;

    /**
     * 案例城市
     */
    private String caseCity;

    /**
     * 案例时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate caseTime;

    /**
     * 案例介绍
     */
    private String caseIntroduce;

    /**
     * 浏览量
     */
    private Integer pvCount;
}
