package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techwells.wumei.domain.Evaluation;
import com.techwells.wumei.domain.Join;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RsJoin extends Join {


    private String nickName;
    private String userIcon;

    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productIcon;
    private String description;

    private Integer merchantId;
    private String merchantName;
    private String merchantLogo;
    private int praiseRate;

    private Integer limitPersonNumber;
    /**
     * 日常价
     */
    private BigDecimal originalPrice;
    /**
     * 拼团价
     */
    private Double currentPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

	private List<RsEvaluation> evaluations;

	private List<RsJoin> joinList;

	private int evaluationCount;

	private String  joinUserId;
}