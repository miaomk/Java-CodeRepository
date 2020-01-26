package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.HotSearch;
import com.techwells.wumei.util.PagingTool;

public interface HotSearchService {

	// 增删改查
	public int addHotSearch(HotSearch hotSearch);

	public int delHotSearch(int hotSearchId);

	public int modifyHotSearch(HotSearch hotSearch);

	HotSearch getHotSearchByHotSearchId(int hotSearchId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<HotSearch> getHotSearchList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
