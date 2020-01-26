package com.techwells.wumei.service;

import com.techwells.wumei.domain.Deal;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface DealService {

	// 增删改查
	public int addDeal(Deal deal);

	public int delDeal(int dealId);

	public int modifyDeal(Deal deal);

	Deal getDealByDealId(int dealId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Deal> getDealList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	/**
	 * 批量更新交易订单
	 *
	 * @param updateList 交易订单List
	 * @return int
	 */
	int updateDelList(List<Deal> updateList);
}
