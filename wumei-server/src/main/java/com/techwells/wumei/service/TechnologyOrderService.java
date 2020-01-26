package com.techwells.wumei.service;

import com.techwells.wumei.domain.TechnologyOrder;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface TechnologyOrderService {

    // 增删改查
    int addTechnologyOrder(TechnologyOrder technologyOrder);

    int delTechnologyOrder(int technologyOrderId);

    int modifyTechnologyOrder(TechnologyOrder technologyOrder);

    TechnologyOrder getTechnologyOrderInfo(int technologyOrderId);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<TechnologyOrder> getTechnologyOrderList(PagingTool pagingTool);

    // 批量删除
    int deleteBatch(String[] idArr);

    /**
     * 批量更新大师订单列表
     *
     * @param updateList 大师订单列表
	 * @return int
     */
    int updateOrderList(List<TechnologyOrder> updateList);
}
