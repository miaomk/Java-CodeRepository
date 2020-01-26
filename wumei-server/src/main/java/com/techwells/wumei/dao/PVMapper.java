package com.techwells.wumei.dao;

import com.techwells.wumei.domain.PV;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface PVMapper {
    int deleteByPrimaryKey(Integer pvId);

    int insert(PV record);

    int insertSelective(PV record);

    PV selectByPrimaryKey(Integer pvId);

    int updateByPrimaryKeySelective(PV record);

    int updateByPrimaryKey(PV record);

    /**
     * 获取浏览总数
     *
     * @param pagingTool 分页条件
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 查询被浏览次数
     *
     * @param relationId 被浏览id
     * @param pvType     浏览类型 1 活动 2 商品 3 严选租赁 4 工作案例 5 需求
     * @return int
     */
    int pvTotal(@Param("relationId") Integer relationId,
                @Param("pvType") Integer pvType);
}