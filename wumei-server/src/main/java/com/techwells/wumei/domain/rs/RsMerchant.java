package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Product;
import lombok.Data;

import java.util.List;

/**
 * @author miao
 */
@Data
public class RsMerchant {

    private Integer merchantId;

    private String merchantName;

    private String merchantIcon;

    private String merchantLogo;

    private String description;
    /**
     * 好评率
     */
    private Integer praiseRate;
    /**
     * 店铺优惠券列表
     */
    private List<Coupon> couponList;

    /**
     * 店铺商品列表
     */
    private List<Product> productList;

    /**
     * 待发货
     */
    private int waitSend;

    /**
     * 待付款
     */
    private int waitPay;

    /**
     * 退款售后
     */
    private int refundAfter;

    /**
     * 待评价
     */
    private int waitEvaluation;

}
