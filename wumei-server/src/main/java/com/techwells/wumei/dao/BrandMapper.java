package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Brand;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer brandId);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer brandId);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Brand> selectBrandList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);

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