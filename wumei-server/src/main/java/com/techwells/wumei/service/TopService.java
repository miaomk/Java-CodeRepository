package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Top;
import com.techwells.wumei.domain.rs.RsTop;
import com.techwells.wumei.util.PagingTool;

public interface TopService {

	// 增删改查
	public int addTop(Top top);

	public int delTop(int topId);

	public int modifyTop(Top top);

	Top getTopByTopId(int topId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsTop> getTopList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

    int batchAddTop(String[] topIdArrays,String startDate,String endDate);

	int updateSort(String topId, String sort);

	int batchUpdateStatus(String[] idArrays,String recommendStatus);
}
