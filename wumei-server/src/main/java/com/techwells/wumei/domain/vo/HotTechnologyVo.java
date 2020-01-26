package com.techwells.wumei.domain.vo;

import com.techwells.wumei.domain.TechnologyCase;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 首页热门热门大师实体类
 *
 * @author miao
 */
@Data
public class HotTechnologyVo implements Serializable {
    /**
     * 用户id（技术人员id）
     */
    private Integer userId;
    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 技术人员职业类型id
     */
    private Integer technologyOccupation;
    /**
     * 技术人员职业类型
     */
    private String typeName;
    /**
     * 工作年长
     */
    private Integer workExperience;
    /**
     * 薪资/天
     */
    private BigDecimal salary;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 荣耀值 默认 50 技术人员每完成一笔订单荣耀值+1
     */
    private Integer glory;
    /**
     * 工作案例列表
     */
    List<TechnologyCase> caseList;
    /**
     * 工作案例图片
     */
    private String[] caseImage;
}
