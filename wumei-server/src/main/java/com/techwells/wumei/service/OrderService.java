package com.techwells.wumei.service;

import com.techwells.wumei.domain.Order;
import com.techwells.wumei.domain.rs.RsOrder;
import com.techwells.wumei.domain.rs.RsOrderInfo;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface OrderService {

	// 增删改查
	public int addOrder(Order order);

	public int delOrder(int orderId);

	public int modifyOrder(Order order);

	RsOrder getOrderByOrderId(int orderId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	int getOrderCount(PagingTool pagingTool);

	List<RsOrder> getOrderList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	/**
	 * 更新订单备注信息
	 * @param orderId 订单 id
	 * @param description 备注详情
	 * @return int
	 */
	int updateOrderDescription(String orderId,String description);

	/***
	 * 批量操作订单
	 * @param idArrays 订单id 数组
	 * @param orderType 更改后的状态
	 * @return int
	 */
	int batchOperationOrder(String[] idArrays,String orderType);

	/**
	 * 查询售出商品数
	 *
	 * @param productId 商品id
	 * @return int
	 */
	int countSale(Integer productId);

	RsOrderInfo selectMerchantOrderInfo(Integer orderId);
}
