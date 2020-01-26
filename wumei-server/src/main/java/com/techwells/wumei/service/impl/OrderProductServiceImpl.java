package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.OrderProductMapper;
import com.techwells.wumei.domain.OrderProduct;
import com.techwells.wumei.domain.rs.VoMerchantOrderProduct;
import com.techwells.wumei.service.OrderProductService;
import com.techwells.wumei.util.PagingTool;


@Service("OrderProductService")
public class OrderProductServiceImpl implements OrderProductService {

	private OrderProductMapper orderProductMapper;

	public OrderProductMapper getOrderProductMapper() {
		return orderProductMapper;
	}

	@Autowired
	public void setOrderProductMapper(OrderProductMapper orderProductMapper) {
		this.orderProductMapper = orderProductMapper;
	}

	@Override
	public int addOrderProduct(OrderProduct orderProduct) {
		int count = 0;
		try {
			count = orderProductMapper.insertSelective(orderProduct);
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
	public int delOrderProduct(int orderProductId) {
		int count = 0;
		try {
			count = orderProductMapper.deleteByPrimaryKey(orderProductId);
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
	public int modifyOrderProduct(OrderProduct orderProduct) {
		int count = 0;
		try {
			count = orderProductMapper.updateByPrimaryKeySelective(orderProduct);
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
	public OrderProduct getOrderProductByOrderProductId(int orderProductId) {
		OrderProduct orderProduct = null;
		try {
			orderProduct = orderProductMapper.selectByPrimaryKey(orderProductId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return orderProduct;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = orderProductMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<OrderProduct> getOrderProductList(PagingTool pagingTool) {
		List<OrderProduct> orderProductList = null;

		try {
			orderProductList = orderProductMapper.selectOrderProductList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return orderProductList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VoMerchantOrderProduct> getMerchantOrderProductList(PagingTool pagingTool) {
		List<VoMerchantOrderProduct> orderProductList = null;

		try {
			orderProductList = orderProductMapper.selectMerchantOrderProductList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return orderProductList;
	}
	
	
	// 获取列表
	@Override
	public int countTotalMop(PagingTool pagingTool) {
		int count = 0;

		try {
			count = orderProductMapper.countTotalMop(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

}
