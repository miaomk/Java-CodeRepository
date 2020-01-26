package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.LevelMapper;
import com.techwells.wumei.domain.Level;
import com.techwells.wumei.service.LevelService;
import com.techwells.wumei.util.PagingTool;


@Service("LevelService")
public class LevelServiceImpl implements LevelService {

	private LevelMapper levelMapper;

	public LevelMapper getLevelMapper() {
		return levelMapper;
	}

	@Autowired
	public void setLevelMapper(LevelMapper levelMapper) {
		this.levelMapper = levelMapper;
	}

	@Override
	public int addLevel(Level level) {
		int count = 0;
		try {
			count = levelMapper.insertSelective(level);
			if (count < 0) {
				throw new Exception("添加模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加模板异常！");
		}
		return count;
	}

	@Override
	public int delLevel(int levelId) {
		int count = 0;
		try {
			count = levelMapper.deleteByPrimaryKey(levelId);
			if (count < 0) {
				throw new Exception("删除模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除模板异常！");
		}
		return count;
	}

	@Override
	public int modifyLevel(Level level) {
		int count = 0;
		try {
			count = levelMapper.updateByPrimaryKeySelective(level);
			if (count < 0) {
				throw new Exception("修改模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改模板异常！");
		}
		return count;
	}

	@Override
	public Level getLevelByLevelId(int levelId) {
		Level level = null;
		try {
			level = levelMapper.selectByPrimaryKey(levelId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return level;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = levelMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Level> getLevelList(PagingTool pagingTool) {
		List<Level> levelList = null;

		try {
			levelList = levelMapper.selectLevelList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return levelList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
