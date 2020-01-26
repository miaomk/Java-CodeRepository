package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ScoreMapper;
import com.techwells.wumei.domain.Score;
import com.techwells.wumei.service.ScoreService;
import com.techwells.wumei.util.PagingTool;


@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

	private ScoreMapper scoreMapper;

	public ScoreMapper getScoreMapper() {
		return scoreMapper;
	}

	@Autowired
	public void setScoreMapper(ScoreMapper scoreMapper) {
		this.scoreMapper = scoreMapper;
	}

	@Override
	public int addScore(Score score) {
		int count = 0;
		try {
			count = scoreMapper.insertSelective(score);
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
	public int delScore(int scoreId) {
		int count = 0;
		try {
			count = scoreMapper.deleteByPrimaryKey(scoreId);
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
	public int modifyScore(Score score) {
		int count = 0;
		try {
			count = scoreMapper.updateByPrimaryKeySelective(score);
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
	public Score getScoreByScoreId(int scoreId) {
		Score score = null;
		try {
			score = scoreMapper.selectByPrimaryKey(scoreId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return score;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = scoreMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Score> getScoreList(PagingTool pagingTool) {
		List<Score> scoreList = null;

		try {
			scoreList = scoreMapper.selectScoreList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return scoreList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
