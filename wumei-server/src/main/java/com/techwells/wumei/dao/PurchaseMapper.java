package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Purchase;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface PurchaseMapper {
    int deleteByPrimaryKey(Integer purchaseId);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Integer purchaseId);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Purchase> selectPurchaseList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}