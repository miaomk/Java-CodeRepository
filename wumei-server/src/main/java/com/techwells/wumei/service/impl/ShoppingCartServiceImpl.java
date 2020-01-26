package com.techwells.wumei.service.impl;

import java.util.List;

import com.techwells.wumei.dao.ShoppingCartMapper;
import com.techwells.wumei.domain.ShoppingCart;
import com.techwells.wumei.domain.ShoppingCartKey;
import com.techwells.wumei.domain.rs.RsShoppingCart;
import com.techwells.wumei.service.ShoppingCartService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;


@Service("ShoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private ShoppingCartMapper shoppingCartMapper;

	public ShoppingCartMapper getShoppingCartMapper() {
		return shoppingCartMapper;
	}

	@Autowired
	public void setShoppingCartMapper(ShoppingCartMapper shoppingCartMapper) {
		this.shoppingCartMapper = shoppingCartMapper;
	}

	@Override
	public int addShoppingCart(ShoppingCart shoppingCart) {
		int count = 0;
		try {
			count = shoppingCartMapper.insertSelective(shoppingCart);
			if (count < 0) {
				throw new Exception("添加购物车失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加购物车异常！");
		}
		return count;
	}

	@Override
	public int delShoppingCart(ShoppingCartKey key) {
		int count = 0;
		try {
			count = shoppingCartMapper.deleteByPrimaryKey(key);
			if (count < 0) {
				throw new Exception("删除购物车失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除购物车异常！");
		}
		return count;
	}

	@Override
	public int modifyShoppingCart(ShoppingCart shoppingCart) {
		int count = 0;
		try {
			count = shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
			if (count < 0) {
				throw new Exception("修改购物车失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改购物车异常！");
		}
		return count;
	}

	@Override
	public ShoppingCart getShoppingCartByShoppingCartId(ShoppingCartKey key) {
		ShoppingCart shoppingCart = null;
		try {
			shoppingCart = shoppingCartMapper.selectByPrimaryKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取购物车详情异常！");
		}
		return shoppingCart;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = shoppingCartMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取购物车总数异常！");
		}
		return count;
	}

	@Override
	public List<RsShoppingCart> getShoppingCartList(PagingTool pagingTool) {
		List<RsShoppingCart> shoppingCartList = null;

		try {
			shoppingCartList = shoppingCartMapper.selectShoppingCartList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取购物车列表异常");
		}
		return shoppingCartList;
	}

	@Override
	public int deleteBatch(List<ShoppingCartKey> shoppingCartKeyList) {
		int count = 0;
		try {
			count = shoppingCartMapper.batchDelete(shoppingCartKeyList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取购物车总数异常！");
		}
		return count;
	}


}
