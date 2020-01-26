package com.techwells.wumei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * 用户评价列表
 *
 * @author miao
 */
@Data
public class TechnologyEvaluateVO implements Serializable {
    /**
     * 评价id
     */
    private Integer evaluateId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 态度分数
     */
    private BigDecimal attitudeScore;
    /**
     * 形象分数
     */
    private BigDecimal imageScore;
    /**
     * 效果分数
     */
    private BigDecimal effectScore;
    /**
     * 总体分数
     */
    private BigDecimal overallScore;
    /**
     * 浏览数
     */
    private Integer pvCount;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价图片
     */
    private String imageUrl;
    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate createDate;
}
