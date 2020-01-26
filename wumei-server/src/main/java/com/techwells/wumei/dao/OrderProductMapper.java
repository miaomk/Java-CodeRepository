package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.OrderProduct;
import com.techwells.wumei.domain.rs.VoMerchantOrderProduct;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface OrderProductMapper {
    int deleteByPrimaryKey(Integer opId);

    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    OrderProduct selectByPrimaryKey(Integer opId);

    int updateByPrimaryKeySelective(OrderProduct record);

    int updateByPrimaryKey(OrderProduct record);
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<OrderProduct> selectOrderProductList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
 	
 	List<VoMerchantOrderProduct> selectMerchantOrderProductList(PagingTool pagingTool);
 	
 	 // 获取列表
 	int countTotalMop(PagingTool pagingTool);

}