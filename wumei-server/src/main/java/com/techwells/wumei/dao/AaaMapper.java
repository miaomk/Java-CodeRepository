package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Aaa;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AaaMapper {
	int deleteByPrimaryKey(Integer aaaId);

	int insert(Aaa record);

	int insertSelective(Aaa record);

	Aaa selectByPrimaryKey(Integer aaaId);

	int updateByPrimaryKeySelective(Aaa record);

	int updateByPrimaryKey(Aaa record);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Aaa> selectAaaList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}