package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Join;
import com.techwells.wumei.domain.rs.RsJoin;
import com.techwells.wumei.util.PagingTool;

public interface JoinService {

	// 增删改查
	public int addJoin(Join join);

	public int delJoin(int joinId);

	public int modifyJoin(Join join);

	RsJoin getJoinByJoinId(int joinId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsJoin> getJoinList(PagingTool pagingTool);
	/**
	 * 分页获取商城首页拼团采购列表
	 *
	 * @param pagingTool 分页
	 * @return List
	 */
	List<RsJoin> getHomeJoinList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
