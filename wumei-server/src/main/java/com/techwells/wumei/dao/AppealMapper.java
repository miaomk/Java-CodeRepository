package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.rs.RsAppeal;
import com.techwells.wumei.domain.Appeal;
import com.techwells.wumei.util.PagingTool;

public interface AppealMapper {
    int deleteByPrimaryKey(Integer opId);

    int insert(Appeal record);

    int insertSelective(Appeal record);

    Appeal selectByPrimaryKey(Integer opId);

    int updateByPrimaryKeySelective(Appeal record);

    int updateByPrimaryKey(Appeal record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<RsAppeal> selectAppealList(PagingTool pagingTool);
 	
 	int batchDelete(String[] ids);
}