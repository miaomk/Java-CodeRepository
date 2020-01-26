package com.techwells.wumei.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 评价技术人员实体类
 *
 * @author miao
 */
@Data
public class TechnologyEvaluateBO implements Serializable {
    /**
     * 评价者id
     */
    private Integer userId;
    /**
     * 大师id
     */
    private Integer technologyId;
    /**
     * 需求id
     */
    private Integer demandId;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价图片数组
     */
    private String[] imageUrl;
    /**
     * 态度评分
     */
    private Integer attitudeScore;
    /**
     * 形象评分
     */
    private Integer imageScore;
    /**
     * 效果评分
     */
    private Integer effectScore;
    /**
     * 总体评分
     */
    private String overallScore;
}
