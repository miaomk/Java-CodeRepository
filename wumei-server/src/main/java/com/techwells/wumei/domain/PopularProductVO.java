package com.techwells.wumei.domain;

import lombok.Data;

/**
 * @author miao
 */
@Data
public class PopularProductVO {

    private Integer merchantId;

    private String merchantName;

    private Integer productId;

    private String productName;

    private String productIcon;

    private Double salePrice;

    private String unit;

    private Integer productType;


}
