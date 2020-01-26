package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Focus;
import com.techwells.wumei.domain.rs.RsFocus;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FocusMapper {
    int deleteByPrimaryKey(Integer focusId);

    int insert(Focus record);

    int insertSelective(Focus record);

    Focus selectByPrimaryKey(Integer focusId);

    int updateByPrimaryKeySelective(Focus record);

    int updateByPrimaryKey(Focus record);

    /**
     * 获取关注公司的详细信息
     *
     * @param userId 用户id
     * @return int
     */
    List<RsFocus> selectFocusListByUserId(Integer userId);

    /**
     * 查询关注总数
     *
     * @param relationId 被关注id
     * @return int
     */
    int focusCount(Integer relationId);

    /**
     * 取消关注
     *
     * @param userId     用户id
     * @param relationId 被关注id
     * @param focusType  关注类型
     * @return int
     */
    int cancelFocus(@Param("userId") Integer userId,
                    @Param("relationId") Integer relationId,
                    @Param("focusType") Integer focusType);

    /**
     * 根据用户id和公司id查询是否关注
     *
     * @param userId    用户id
     * @param relationId 被关注者id
     * @param focusType 关注类型 1公司 2 用户
     * @return int
     */
    int queryFocus(@Param("userId") Integer userId,
                   @Param("relationId") Integer relationId,
                   @Param("focusType") Integer focusType);

    /**
     * 分页查询关注总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 获取关注id 1公司 2 用户
     *
     * @param relationId 被关注id
     * @param userId     关注者id
     * @param focusType  关注id
     * @return int
     */
    Integer getFocusId(@Param("relationId") Integer relationId,
                   @Param("userId") String userId,
                   @Param("focusType") int focusType);
}