package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.HotSearch;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface HotSearchMapper {
    int deleteByPrimaryKey(Integer hotSearchId);

    int insert(HotSearch record);

    int insertSelective(HotSearch record);

    HotSearch selectByPrimaryKey(Integer hotSearchId);

    int updateByPrimaryKeySelective(HotSearch record);

    int updateByPrimaryKey(HotSearch record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<HotSearch> selectHotSearchList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}