package com.techwells.wumei.service;

import com.techwells.wumei.domain.Appeal;
import com.techwells.wumei.domain.rs.RsAppeal;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface AppealService {

	// 增删改查
	public int addAppeal(Appeal appeal);

	public int delAppeal(int appealId);

	public int modifyAppeal(Appeal appeal);

	Appeal getAppealByAppealId(int appealId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsAppeal> getAppealList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
