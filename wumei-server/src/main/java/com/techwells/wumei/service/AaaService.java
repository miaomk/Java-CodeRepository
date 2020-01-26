package com.techwells.wumei.service;

import com.techwells.wumei.domain.Aaa;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface AaaService {

	// 增删改查
	public int addAaa(Aaa aaa);

	public int delAaa(int aaaId);

	public int modifyAaa(Aaa aaa);

	Aaa getAaaByAaaId(int aaaId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Aaa> getAaaList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
