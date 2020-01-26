package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.domain.vo.TechnologyEvaluateVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author miao
 */
@Data
public class RsTechnology extends Technology {
    /**
     * 大师真实姓名
     */
    private String realName;
    /**
     * 大师头像
     */
    private String userIcon;
    /**
     * 大师职业类型
     */
    private String typeName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 态度评分
     */
    private BigDecimal attitudeScore;
    /**
     * 形象评分
     */
    private BigDecimal imageScore;
    /**
     * 效果评分
     */
    private BigDecimal effectScore;
    /**
     * 总体评分
     */
    private BigDecimal overallScore;

    /**
     * 入驻天数
     */
    private Integer enterDay;
    /**
     * 大师评价总数
     */
    private Integer evaluateCount;
    /**
     * 大师评价列表
     */
    private List<TechnologyEvaluateVO> evaluateList;
    /**
     * 用户案例总数
     */
    private Integer caseCount;
    /**
     * 用户案例列表
     */
    private List<TechnologyCaseVO> caseList;
    /**
     * 图片与视频总数
     */
    private Integer caseImageCount;
    /**
     * 工作案例图片
     */
    private String[] caseImage;
    /**
     * 是否收藏了大师 null为没收藏，有值则为收藏id
     */
    private Integer collectId;
}
