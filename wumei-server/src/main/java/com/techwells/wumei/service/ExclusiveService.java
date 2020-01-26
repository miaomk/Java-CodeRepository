package com.techwells.wumei.service;

import com.techwells.wumei.domain.Exclusive;
import com.techwells.wumei.domain.rs.RsExclusive;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface ExclusiveService {

	// 增删改查
	public int addExclusive(Exclusive exclusive);

	public int delExclusive(int exclusiveId);

	public int modifyExclusive(Exclusive exclusive);

	Exclusive getExclusiveByExclusiveId(int exclusiveId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsExclusive> getExclusiveList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
