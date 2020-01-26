package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Search;
import com.techwells.wumei.util.PagingTool;

public interface SearchService {

	// 增删改查
	public int addSearch(Search search);

	public int delSearch(int searchId);

	public int modifySearch(Search search);

	Search getSearchBySearchId(int searchId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Search> getSearchList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
