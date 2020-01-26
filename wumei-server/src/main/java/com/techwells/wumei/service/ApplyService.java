package com.techwells.wumei.service;

import com.techwells.wumei.domain.Apply;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface ApplyService {

	// 增删改查
	public int addApply(Apply apply);

	public int delApply(int applyId);

	public int modifyApply(Apply apply);

	Apply getApplyByApplyId(int applyId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Apply> getApplyList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
