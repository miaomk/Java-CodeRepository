package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Exclusive;
import com.techwells.wumei.domain.rs.RsExclusive;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ExclusiveMapper {
    int deleteByPrimaryKey(Integer exclusiveId);

    int insert(Exclusive record);

    int insertSelective(Exclusive record);

    Exclusive selectByPrimaryKey(Integer exclusiveId);

    int updateByPrimaryKeySelective(Exclusive record);

    int updateByPrimaryKey(Exclusive record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsExclusive> selectExclusiveList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}