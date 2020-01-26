package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 案例管理，案例信息实体类
 *
 * @author miao
 */
@Data
public class CaseVO implements Serializable {
    /**
     * 大师id
     */
    private Integer userId;
    /**
     * 大师姓名
     */
    private String userName;
    /**
     * 大师头像
     */
    private String userIcon;
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
    private String caseImageUrl;

    /**
     * 案例服务城市
     */
    private String caseCity;
    /**
     * 案例服务城市
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate caseTime;
    /**
     * 案例介绍
     */
    private String caseIntroduce;
}
