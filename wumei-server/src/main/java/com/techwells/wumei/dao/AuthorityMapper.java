package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Authority;
import com.techwells.wumei.util.PagingTool;

public interface AuthorityMapper {
	int deleteByPrimaryKey(Integer authorityId);

	int insert(Authority record);

	int insertSelective(Authority record);

	Authority selectByPrimaryKey(Integer authorityId);

	int updateByPrimaryKeySelective(Authority record);

	int updateByPrimaryKey(Authority record);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Authority> selectAuthorityList(PagingTool pagingTool);

	List<Authority> selectAuthoritys();
}