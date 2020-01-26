package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Apply;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer applyId);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Apply> selectApplyList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}