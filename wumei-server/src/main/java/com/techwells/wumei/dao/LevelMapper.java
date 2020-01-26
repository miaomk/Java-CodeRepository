package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Level;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface LevelMapper {
    int deleteByPrimaryKey(Integer levelId);

    int insert(Level record);

    int insertSelective(Level record);

    Level selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(Level record);

    int updateByPrimaryKey(Level record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Level> selectLevelList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}