package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Balance;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer balanceId);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Integer balanceId);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Balance> selectBalanceList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}