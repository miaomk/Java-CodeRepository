package com.techwells.wumei.service;

import com.techwells.wumei.domain.Authority;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface AuthorityService {

	// 增删改查
	public int addAuthority(Authority authority);

	public int delAuthority(int authorityId);

	public int modifyAuthority(Authority authority);

	Authority getAuthorityByAuthorityId(int authorityId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Authority> getAuthorityList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	public List<Authority> getAuthoritys();
}
