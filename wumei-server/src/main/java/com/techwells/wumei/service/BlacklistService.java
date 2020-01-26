package com.techwells.wumei.service;

import com.techwells.wumei.domain.Blacklist;
import com.techwells.wumei.domain.BlacklistKey;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface BlacklistService {

	// 增删改查
	public int addBlacklist(Blacklist blacklist);

	public int delBlacklist(BlacklistKey key);

	public int modifyBlacklist(Blacklist blacklist);

	Blacklist getBlacklistByBlacklistId(BlacklistKey key);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Blacklist> getBlacklistList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
