package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户评价大师实体类
 *
 * @author miao
 */
@Data
public class TechnologyEvaluate {
    private Integer evaluateId;

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
     * 评价图片
     */
    private String imageUrl;
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

    private Integer activated;

    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;

}