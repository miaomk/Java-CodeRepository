package com.techwells.wumei.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 技术人员案例实体类
 *
 * @author miao
 */
@Data
public class TechnologyCase {
    /**
     * 案例id
     */
    private Integer caseId;
    /**
     * 案例名
     */
    private String caseName;
    /**
     * 大师id
     */
    private Integer technologyId;
    /**
     * 案例介绍
     */
    private String caseIntroduce;
    /**
     * 案例图片
     */
    private String imageUrl;
    /**
     * 服务城市
     */
    private String caseCity;
    /**
     * 服务时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate caseTime;

    private Integer activated;

    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

}