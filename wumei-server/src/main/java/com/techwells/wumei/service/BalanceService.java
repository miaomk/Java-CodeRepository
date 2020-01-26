package com.techwells.wumei.service;

import com.techwells.wumei.domain.Balance;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface BalanceService {

	// 增删改查
	public int addBalance(Balance balance);

	public int delBalance(int balanceId);

	public int modifyBalance(Balance balance);

	Balance getBalanceByBalanceId(int balanceId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Balance> getBalanceList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
