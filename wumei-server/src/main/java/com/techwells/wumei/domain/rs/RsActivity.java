package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.ActivityForm;
import lombok.Data;

/**
 * @author miao
 */
@Data
public class RsActivity extends Activity {

    /**
     * 例: 2019/10/25 周五
     */
    private String activityTime;

    private RsCompany rsCompany;

    /**
     * 报名人数
     */
    private int signUpCount;
    /**
     * 收藏活动人数
     */
    private int collectCount;

    /**
     * 收藏id
     */
    private Integer collectId;
    /**
     * 关注id
     */
    private Integer focusId;
    /**
     * 报名表单
     */
    private ActivityForm activityForm;

}
