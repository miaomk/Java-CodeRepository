package com.techwells.wumei.dao;

import com.techwells.wumei.domain.TechnologyOrder;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface TechnologyOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TechnologyOrder record);

    int insertSelective(TechnologyOrder record);

    TechnologyOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TechnologyOrder record);

    int updateByPrimaryKey(TechnologyOrder record);

    /**
     * 分页查询大师订单列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页查询大师订单列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyOrder> selectTechnologyOrderList(PagingTool pagingTool);

    /**
     * 批量更新大师订单列表
     *
     * @param updateList 活动订单
     * @return int
     */
    int batchUpdateOrder(List<TechnologyOrder> updateList);
}