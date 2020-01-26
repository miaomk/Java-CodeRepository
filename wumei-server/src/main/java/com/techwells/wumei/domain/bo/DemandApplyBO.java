package com.techwells.wumei.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 大师申请需求实体类
 *
 * @author miao
 */
@Data
public class DemandApplyBO implements Serializable {
    /**
     * 需求id
     */
    private Integer demandId;
    /**
     * 大师id
     */
    private Integer userId;
}
