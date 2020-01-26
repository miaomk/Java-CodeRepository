package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Blacklist;
import com.techwells.wumei.domain.BlacklistKey;
import com.techwells.wumei.util.PagingTool;

public interface BlacklistMapper {
    int deleteByPrimaryKey(BlacklistKey key);

    int insert(Blacklist record);

    int insertSelective(Blacklist record);

    Blacklist selectByPrimaryKey(BlacklistKey key);

    int updateByPrimaryKeySelective(Blacklist record);

    int updateByPrimaryKey(Blacklist record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Blacklist> selectBlacklistList(PagingTool pagingTool);
}