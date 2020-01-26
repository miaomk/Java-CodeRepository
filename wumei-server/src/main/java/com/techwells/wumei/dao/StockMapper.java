package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Stock;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    int deleteByPrimaryKey(Integer stockId);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer stockId);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Stock> selectStockList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);
  	
  	
    Stock selectStockByStock(Stock st);


    List<Stock> getStockListByProductId(PagingTool pagingTool);
}