package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.OrderProduct;
import com.techwells.wumei.domain.rs.VoMerchantOrderProduct;
import com.techwells.wumei.util.PagingTool;

public interface OrderProductService {

	// 增删改查
	public int addOrderProduct(OrderProduct orderProduct);

	public int delOrderProduct(int orderProductId);

	public int modifyOrderProduct(OrderProduct orderProduct);

	OrderProduct getOrderProductByOrderProductId(int orderProductId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<OrderProduct> getOrderProductList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	
	List<VoMerchantOrderProduct> getMerchantOrderProductList(PagingTool pagingTool);
	
	int countTotalMop(PagingTool pagingTool);

}
