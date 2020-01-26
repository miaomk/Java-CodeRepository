package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.ProductType;
import com.techwells.wumei.domain.rs.BoProductType;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<ProductType> selectProductTypeList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);

	List<ProductType> queryL1();

	List<ProductType> queryByPid(Integer typeId);

	List<BoProductType> listWithChildren();
}