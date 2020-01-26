package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.ProductType;
import com.techwells.wumei.domain.rs.BoProductType;
import com.techwells.wumei.util.PagingTool;

public interface ProductTypeService {

	// 增删改查
	public int addProductType(ProductType productType);

	public int delProductType(int productTypeId);

	public int modifyProductType(ProductType productType);

	ProductType getProductTypeByProductTypeId(int productTypeId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<ProductType> getProductTypeList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	public List<ProductType> queryL1();

	public List<ProductType> queryByPid(Integer typeId);

	List<BoProductType> listWithChildren();
}
