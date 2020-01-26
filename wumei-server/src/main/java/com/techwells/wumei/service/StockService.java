package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Stock;
import com.techwells.wumei.util.PagingTool;

public interface StockService {

	// 增删改查
	public int addStock(Stock stock);

	public int delStock(int stockId);

	public int modifyStock(Stock stock);

	Stock getStockByStockId(int stockId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Stock> getStockList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	public int modifyStockList(List<Stock> stockList);

	public int deleteByProductId(Integer productId);
	
	Stock getStockByStock(Stock st);

	List<Stock> getStockListByProductId(PagingTool pagingTool);
}
