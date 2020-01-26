package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.ShoppingCart;
import com.techwells.wumei.domain.ShoppingCartKey;
import com.techwells.wumei.domain.rs.RsShoppingCart;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
    int deleteByPrimaryKey(ShoppingCartKey key);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(ShoppingCartKey key);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<RsShoppingCart> selectShoppingCartList(PagingTool pagingTool);
  	
	int batchDelete(@Param("ids") List<ShoppingCartKey> ids);
}