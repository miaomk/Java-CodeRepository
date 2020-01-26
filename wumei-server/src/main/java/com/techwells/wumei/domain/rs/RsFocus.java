package com.techwells.wumei.domain.rs;

import lombok.Data;

import java.util.List;

/**
 * @author miao
 */
@Data
public class RsFocus {

    private Integer focusId;

    private Integer userId;

    private Integer companyId;

    private String companyName;

    private String companyIntroduction;

    private String userIcon;

    private String nickName;

    private Integer activityCount;

    private Integer fansCount;

    List<RsActivity> activity;

}
