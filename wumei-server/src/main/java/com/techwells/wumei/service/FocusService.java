package com.techwells.wumei.service;

import com.techwells.wumei.domain.Focus;
import com.techwells.wumei.domain.rs.RsFocus;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface FocusService {
    /**
     * 增加关注
     *
     * @param focus 关注信息
     * @return int
     */
    int addFocus(Focus focus);

    /**
     * 查询关注公司列表详情
     *
     * @param userId
     * @return
     */
    List<RsFocus> getFocusCompanyList(Integer userId);

    /**
     * 删除关注
     *
     * @param focusId 关注表id
     * @return int
     */
    int deleteFocus(Integer focusId);

    /**
     * 查询用户是否关注公司
     *
     * @param userId    用户id
     * @param relationId 被关注者id
     * @param focusType 关注类型 1公司 2 用户
     * @return int
     */
    int queryFocusStatus(Integer userId, Integer relationId,Integer focusType);

    int countTotal(PagingTool pagingTool);

    /**
     * 取消关注
     *
     * @param userId     用户id
     * @param relationId 被关注id
     * @param focusType  关注类型
     * @return int
     */
    int cancelFocus(Integer userId, Integer relationId, Integer focusType);

    Integer getFocusId(Integer userId, Integer relationId, Integer focusType);
}
