package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Specifications;
import com.techwells.wumei.domain.SpecificationsProduct;
import com.techwells.wumei.util.PagingTool;

public interface SpecificationsService {

	// 增删改查
	public int addSpecifications(Specifications specifications);

	public int delSpecifications(int specificationsId);

	public int modifySpecifications(Specifications specifications);

	Specifications getSpecificationsBySpecificationsId(int specificationsId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Specifications> getSpecificationsList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(List<SpecificationsProduct> specificationsProductList);

	Object getSpecificationVoList(Integer productId);

	List<Specifications> queryByProductid(Integer productId);

	int batchInsertSpecification(List<SpecificationsProduct> specificationsProductList);

	List<SpecificationsProduct> getSpecificationsList(Integer productId);

	int batchUpdateSpecification(List<SpecificationsProduct> specificationsProductList);

	int batchDelete(String[] idArry);
}
