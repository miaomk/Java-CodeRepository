package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Strategy;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface StrategyMapper {
    int deleteByPrimaryKey(Integer strategyId);

    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy selectByPrimaryKey(Integer strategyId);

    int updateByPrimaryKeySelective(Strategy record);

    int updateByPrimaryKey(Strategy record);
    
 // 获取列表
   	int countTotal(PagingTool pagingTool);

   	List<Strategy> selectStrategyList(PagingTool pagingTool);
   	
   	int batchDelete(@Param("ids")String[] ids);
}