package com.techwells.wumei.domain.rs;

import lombok.Data;

/**
 * 商家订单详情实体类
 *
 * @author miao
 */
@Data
public class RsOrderInfo extends RsOrder {

    private Integer merchantId;

    private String merchantName;

}
