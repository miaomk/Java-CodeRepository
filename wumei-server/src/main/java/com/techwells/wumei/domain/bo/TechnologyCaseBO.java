package com.techwells.wumei.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 大师认证案例实体类
 *
 * @author miao
 */
@Data
public class TechnologyCaseBO implements Serializable {
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
    /**
     * 案例图片数组
     */
    private String[] imageUrlArray;
}
