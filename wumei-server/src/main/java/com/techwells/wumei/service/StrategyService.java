package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Strategy;
import com.techwells.wumei.util.PagingTool;

public interface StrategyService {

	// 增删改查
	public int addStrategy(Strategy strategy);

	public int delStrategy(int strategyId);

	public int modifyStrategy(Strategy strategy);

	Strategy getStrategyByStrategyId(int strategyId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Strategy> getStrategyList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
