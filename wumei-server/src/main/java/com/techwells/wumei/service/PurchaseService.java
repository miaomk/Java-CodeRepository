package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Purchase;
import com.techwells.wumei.util.PagingTool;

public interface PurchaseService {

	// 增删改查
	public int addPurchase(Purchase purchase);

	public int delPurchase(int purchaseId);

	public int modifyPurchase(Purchase purchase);

	Purchase getPurchaseByPurchaseId(int purchaseId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Purchase> getPurchaseList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
