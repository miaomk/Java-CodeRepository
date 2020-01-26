package com.techwells.wumei.domain.vo;

import lombok.Data;

import java.io.Serializable;

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
     * 浏览量
     */
    private Integer pvCount;
}
