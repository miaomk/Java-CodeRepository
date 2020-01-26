package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.PurchaseRule;
import com.techwells.wumei.util.PagingTool;

public interface PurchaseRuleService {

	// 增删改查
	public int addPurchaseRule(PurchaseRule purchaseRule);

	public int delPurchaseRule(int purchaseRuleId);

	public int modifyPurchaseRule(PurchaseRule purchaseRule);

	PurchaseRule getPurchaseRuleByPurchaseRuleId(int purchaseRuleId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<PurchaseRule> getPurchaseRuleList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
