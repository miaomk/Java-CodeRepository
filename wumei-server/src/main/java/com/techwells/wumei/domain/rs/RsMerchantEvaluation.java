package com.techwells.wumei.domain.rs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author miao
 */
@Data
public class RsMerchantEvaluation {
    private Integer merchantId;

    private String merchantName;

    private String merchantIcon;
    /**
     * 好评率
     */
    private Double goodRate;

    private List<RsEvaluation> rsEvaluationList;

}
