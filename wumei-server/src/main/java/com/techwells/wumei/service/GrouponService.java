package com.techwells.wumei.service;

import com.techwells.wumei.domain.Groupon;
import com.techwells.wumei.domain.rs.RsGroupon;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface GrouponService {

	// 增删改查
	public int addGroupon(Groupon groupon);

	public int delGroupon(int grouponId);

	public int modifyGroupon(Groupon groupon);

	Groupon getGrouponByGrouponId(int grouponId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsGroupon> getGrouponList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	
	List<Groupon> getGrouponListByProductId(Integer productId);
}
