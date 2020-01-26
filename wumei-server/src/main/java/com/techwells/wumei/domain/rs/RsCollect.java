package com.techwells.wumei.domain.rs;


import com.techwells.wumei.domain.Collect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RsCollect extends Collect {
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productIcon;
    /**
     * 商品价格
     */
    private Double salePrice;

    /**
     * 活动id
     */
    private String activityId;
    /**
     * 活动主题
     */
    private String activityTheme;
    /**
     * 活动费用
     */
    private String activityFee;
    /**
     * 活动是否免费
     */
    private String activityFree;
    /**
     * 活动地点
     */
    private String activityLocation;
    /**
     * 活动时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime activityStartTime;
    /**
     * 活动时间 10/25 周五
     */
    private String activityTime;
    /**
     * 活动封面
     */
    private String activityLogo;
    /**
     * 严选租赁商品id
     */
    private String commodityId;
    /**
     * 严选租赁商品名
     */
    private String commodityName;
    /**
     * 严选租赁商品图片
     */
    private String commodityIcon;
    /**
     * 严选租赁商品租赁价格
     */
    private String rent;
    /**
     * 严选租赁商品租赁单位 小时/天
     */
    private String unit;
}
