package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Search;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface SearchMapper {
    int deleteByPrimaryKey(Integer searchId);

    int insert(Search record);

    int insertSelective(Search record);

    Search selectByPrimaryKey(Integer searchId);

    int updateByPrimaryKeySelective(Search record);

    int updateByPrimaryKey(Search record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Search> selectSearchList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}