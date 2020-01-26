package com.techwells.wumei.dao;

import com.techwells.wumei.domain.ActivityForm;
import org.apache.ibatis.annotations.Param;

public interface ActivityFormMapper {
    int deleteByPrimaryKey(Integer activityId);

    int insert(ActivityForm record);

    int insertSelective(ActivityForm record);

    ActivityForm selectByPrimaryKey(Integer activityId);

    int updateByPrimaryKeySelective(ActivityForm record);

    int updateByPrimaryKey(ActivityForm record);

    /**
     * 批量删除报名表单
     *
     * @param activityIdArrays 活动id数组
     * @return int
     */
    int batchDelete(@Param("ids") String[] activityIdArrays);
}