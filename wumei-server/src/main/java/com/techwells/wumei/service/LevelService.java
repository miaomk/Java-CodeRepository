package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Level;
import com.techwells.wumei.util.PagingTool;

public interface LevelService {

	// 增删改查
	public int addLevel(Level level);

	public int delLevel(int levelId);

	public int modifyLevel(Level level);

	Level getLevelByLevelId(int levelId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Level> getLevelList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
