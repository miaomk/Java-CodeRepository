package com.techwells.wumei.domain.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techwells.wumei.domain.Receive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的优惠券实体类
 *
 * @author miao
 */
@Data
public class RsReceive extends Receive {
    /**
     * 发放优惠券商铺id
     */
    private Integer merchantId;
    /**
     * 发放优惠券商铺名
     */
    private String merchantName;

    /**
     * 发放优惠券商铺名
     */
    private String merchantLogo;

    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     */
    private Integer couponType;
    /**
     * 优惠券名臣买个
     */
    private String couponName;
    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 最低消费金额
     */
    private BigDecimal minPoint;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
