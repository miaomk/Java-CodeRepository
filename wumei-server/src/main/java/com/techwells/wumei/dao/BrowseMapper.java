package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Browse;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface BrowseMapper {
    int deleteByPrimaryKey(Integer browseId);

    int insert(Browse record);

    int insertSelective(Browse record);

    Browse selectByPrimaryKey(Integer browseId);

    int updateByPrimaryKeySelective(Browse record);

    int updateByPrimaryKey(Browse record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Browse> selectBrowseList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}