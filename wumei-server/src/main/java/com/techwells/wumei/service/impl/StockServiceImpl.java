package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.StockMapper;
import com.techwells.wumei.domain.Stock;
import com.techwells.wumei.service.StockService;
import com.techwells.wumei.util.PagingTool;


@Service("StockService")
public class StockServiceImpl implements StockService {

	private StockMapper stockMapper;

	public StockMapper getStockMapper() {
		return stockMapper;
	}

	@Autowired
	public void setStockMapper(StockMapper stockMapper) {
		this.stockMapper = stockMapper;
	}

	@Override
	public int addStock(Stock stock) {
		int count = 0;
		try {
			count = stockMapper.insertSelective(stock);
			if (count < 0) {
				throw new Exception("添加模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加模板异常！");
		}
		return count;
	}

	@Override
	public int delStock(int stockId) {
		int count = 0;
		try {
			count = stockMapper.deleteByPrimaryKey(stockId);
			if (count < 0) {
				throw new Exception("删除模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除模板异常！");
		}
		return count;
	}

	@Override
	public int modifyStock(Stock stock) {
		int count = 0;
		try {
			count = stockMapper.updateByPrimaryKeySelective(stock);
			if (count < 0) {
				throw new Exception("修改模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改模板异常！");
		}
		return count;
	}

	@Override
	public Stock getStockByStockId(int stockId) {
		Stock stock = null;
		try {
			stock = stockMapper.selectByPrimaryKey(stockId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return stock;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = stockMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Stock> getStockList(PagingTool pagingTool) {
		List<Stock> stockList = null;

		try {
			stockList = stockMapper.selectStockList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return stockList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyStockList(List<Stock> stockList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByProductId(Integer productId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stock getStockByStock(Stock st) {
		Stock s = null;
		try {
			s = stockMapper.selectStockByStock(st);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return s;
	}

	@Override
	public List<Stock> getStockListByProductId(PagingTool pagingTool) {
		List<Stock> stockList = null;

		try {
			stockList = stockMapper.getStockListByProductId(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return stockList;
	}

}
