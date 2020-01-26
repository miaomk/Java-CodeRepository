package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.StrategyMapper;
import com.techwells.wumei.domain.Strategy;
import com.techwells.wumei.service.StrategyService;
import com.techwells.wumei.util.PagingTool;


@Service("StrategyService")
public class StrategyServiceImpl implements StrategyService {

	private StrategyMapper strategyMapper;

	public StrategyMapper getStrategyMapper() {
		return strategyMapper;
	}

	@Autowired
	public void setStrategyMapper(StrategyMapper strategyMapper) {
		this.strategyMapper = strategyMapper;
	}

	@Override
	public int addStrategy(Strategy strategy) {
		int count = 0;
		try {
			count = strategyMapper.insertSelective(strategy);
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
	public int delStrategy(int strategyId) {
		int count = 0;
		try {
			count = strategyMapper.deleteByPrimaryKey(strategyId);
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
	public int modifyStrategy(Strategy strategy) {
		int count = 0;
		try {
			count = strategyMapper.updateByPrimaryKeySelective(strategy);
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
	public Strategy getStrategyByStrategyId(int strategyId) {
		Strategy strategy = null;
		try {
			strategy = strategyMapper.selectByPrimaryKey(strategyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return strategy;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = strategyMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Strategy> getStrategyList(PagingTool pagingTool) {
		List<Strategy> strategyList = null;

		try {
			strategyList = strategyMapper.selectStrategyList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return strategyList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
