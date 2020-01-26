package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.ProductImage;
import com.techwells.wumei.domain.SpecificationsProduct;
import com.techwells.wumei.domain.Stock;
import lombok.Data;

import java.util.List;

/**
 * 小程序商品详情实体类
 *
 * @author miao
 */
@Data
public class ProductVo {

    private Integer productId;

    private String productName;

    private String specification;

    private Double salePrice;
    /**
     * 已售个数
     */
    private int saleCount;
    /**
     * 发货地点
     */
    private String location;
    /**
     * 邮费
     */
    private Double postage;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 店铺名
     */
    private String merchantName;
    /**
     * 店铺id
     */
    private int merchantId;

    private String merchantIcon;
    /**
     * 商品图片列表
     */
    private List<ProductImage> imageList;
    /**
     * 商品评价列表
     */
    private List<RsEvaluation> evaluationList;
    /**
     * 商品评价总数
     */
    private int evaluationCount;
    /**
     * 商品规格列表
     */
    private List<SpecificationsProduct> specificationsList;
}
