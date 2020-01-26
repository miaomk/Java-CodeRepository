package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.ShoppingCart;
import com.techwells.wumei.domain.ShoppingCartKey;
import com.techwells.wumei.domain.rs.RsShoppingCart;
import com.techwells.wumei.util.PagingTool;

public interface ShoppingCartService {

	// 增删改查
	public int addShoppingCart(ShoppingCart shoppingCart);

	public int delShoppingCart(ShoppingCartKey key);

	public int modifyShoppingCart(ShoppingCart shoppingCart);

	ShoppingCart getShoppingCartByShoppingCartId(ShoppingCartKey key);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsShoppingCart> getShoppingCartList(PagingTool pagingTool);

	
	int deleteBatch(List<ShoppingCartKey> shoppingCartKeyList);
}
