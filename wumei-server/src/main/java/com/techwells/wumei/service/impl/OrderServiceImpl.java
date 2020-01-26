package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.OrderMapper;
import com.techwells.wumei.domain.Order;
import com.techwells.wumei.domain.rs.RsOrder;
import com.techwells.wumei.domain.rs.RsOrderInfo;
import com.techwells.wumei.service.OrderService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("OrderService")
public class OrderServiceImpl implements OrderService {

	private OrderMapper orderMapper;

	public OrderMapper getOrderMapper() {
		return orderMapper;
	}

	@Autowired
	public void setOrderMapper(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	@Override
	public int addOrder(Order order) {
		int count = 0;
		try {
			count = orderMapper.insertSelective(order);
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
	public int delOrder(int orderId) {
		int count = 0;
		try {
			count = orderMapper.deleteByPrimaryKey(orderId);
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
	public int modifyOrder(Order order) {
		int count = 0;
		try {
			count = orderMapper.updateByPrimaryKeySelective(order);
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
	public RsOrder getOrderByOrderId(int orderId) {
		RsOrder order;
		try {
			order = orderMapper.selectOrderByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取订单详情异常！");
		}
		return order;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = orderMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取订单总数异常！");
		}
		return count;
	}

	@Override
	public int getOrderCount(PagingTool pagingTool) {
		int count;

		try {
			count = orderMapper.getOrderCount(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	/**
	 * 订单状态 0表示全部订单 1表示待付款，2表示待发货，3表示待收货，4待评价，5表示交易完成
	 */
	@Override
	public List<RsOrder> getOrderList(PagingTool pagingTool) {
		List<RsOrder> orderList;

		try {
			orderList = orderMapper.selectOrderList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return orderList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOrderDescription(String orderId, String description) {
		int count;

		try {
			count = orderMapper.updateDescription(orderId,description);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("备注订单出现异常！");
		}
		return count;
	}

	@Override
	public int batchOperationOrder(String[] idArrays, String orderType) {
		int count;

		if ("2".equals(orderType)) {
			try {
				count = orderMapper.batchDelete(idArrays);
				if (count < 0) {
					throw new Exception("批量删除失败!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("批量删除异常！");
			}
		}else {
			try {

				count = orderMapper.batchOperationOrder(idArrays,orderType);
				if (count < 0) {
					throw new Exception("批量操作失败!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("批量操作异常！");
			}
		}
		return count;
	}

	@Override
	public int countSale(Integer productId) {
		int count;

		try {

			count = orderMapper.countSale(productId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取卖出商品总数异常！");
		}
		return count;
	}

	@Override
	public RsOrderInfo selectMerchantOrderInfo(Integer orderId) {
		RsOrderInfo order;
		try {
			order = orderMapper.selectMerchantOrderInfo(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取商家订单详情异常！");
		}
		return order;
	}
}
