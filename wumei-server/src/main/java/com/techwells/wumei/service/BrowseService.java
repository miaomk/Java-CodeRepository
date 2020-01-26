package com.techwells.wumei.service;

import com.techwells.wumei.domain.Browse;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface BrowseService {

	// 增删改查
	public int addBrowse(Browse browse);

	public int delBrowse(int browseId);

	public int modifyBrowse(Browse browse);

	Browse getBrowseByBrowseId(int browseId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Browse> getBrowseList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
