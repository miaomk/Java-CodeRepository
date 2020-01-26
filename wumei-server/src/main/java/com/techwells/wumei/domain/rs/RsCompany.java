package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Company;
import lombok.Data;

import java.util.List;

/**
 * @author miao
 */
@Data
public class RsCompany extends Company {

    private String userIcon;

    /**
     * 待举办活动数
     */
    private int activityCount;

    private int fansCount;

    private int focusStatus;

    private String position;

    List<RsActivity> rsActivityList;
}
