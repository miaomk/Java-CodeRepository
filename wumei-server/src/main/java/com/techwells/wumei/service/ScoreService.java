package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Score;
import com.techwells.wumei.util.PagingTool;

public interface ScoreService {

	// 增删改查
	public int addScore(Score score);

	public int delScore(int scoreId);

	public int modifyScore(Score score);

	Score getScoreByScoreId(int scoreId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Score> getScoreList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
