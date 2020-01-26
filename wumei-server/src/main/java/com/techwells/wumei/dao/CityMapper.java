package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.City;
import com.techwells.wumei.util.PagingTool;

public interface CityMapper {
    int deleteByPrimaryKey(Integer cityId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cityId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<City> selectCityList(PagingTool pagingTool);
}