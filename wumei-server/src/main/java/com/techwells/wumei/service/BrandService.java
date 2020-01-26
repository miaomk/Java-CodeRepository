package com.techwells.wumei.service;

import com.techwells.wumei.domain.Brand;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface BrandService {

	// 增删改查
	public int addBrand(Brand brand);

	public int delBrand(int brandId);

	public int modifyBrand(Brand brand);

	Brand getBrandByBrandId(int brandId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Brand> getBrandList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	/**
	 * 启用开始轮播图
	 *
	 * @return int
	 */
	int updateStartBrand();

	/**
	 * 禁用到期轮播图
	 *
	 * @return int
	 */
	int updateEndBrand();
}
