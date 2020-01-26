package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.CityMapper;
import com.techwells.wumei.domain.City;
import com.techwells.wumei.service.CityService;
import com.techwells.wumei.util.PagingTool;


@Service("CityService")
public class CityServiceImpl implements CityService {

	private CityMapper cityMapper;

	public CityMapper getCityMapper() {
		return cityMapper;
	}

	@Autowired
	public void setCityMapper(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	@Override
	public int addCity(City city) {
		int count = 0;
		try {
			count = cityMapper.insertSelective(city);
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
	public int delCity(int cityId) {
		int count = 0;
		try {
			count = cityMapper.deleteByPrimaryKey(cityId);
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
	public int modifyCity(City city) {
		int count = 0;
		try {
			count = cityMapper.updateByPrimaryKeySelective(city);
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
	public City getCityByCityId(int cityId) {
		City city = null;
		try {
			city = cityMapper.selectByPrimaryKey(cityId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return city;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = cityMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取城市总数异常！");
		}
		return count;
	}

	@Override
	public List<City> getCityList(PagingTool pagingTool) {
		List<City> cityList;

		try {
			cityList = cityMapper.selectCityList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取城市列表异常");
		}
		return cityList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
