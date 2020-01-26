package com.techwells.wumei.service;

import com.techwells.wumei.domain.City;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface CityService {

	// 增删改查
	public int addCity(City city);

	public int delCity(int cityId);

	public int modifyCity(City city);

	City getCityByCityId(int cityId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<City> getCityList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
