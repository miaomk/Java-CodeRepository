package com.techwells.wumei.service.impl;

import java.util.List;

import com.techwells.wumei.dao.BalanceMapper;
import com.techwells.wumei.domain.Balance;
import com.techwells.wumei.service.BalanceService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;


@Service("BalanceService")
public class BalanceServiceImpl implements BalanceService {

	private BalanceMapper balanceMapper;

	public BalanceMapper getBalanceMapper() {
		return balanceMapper;
	}

	@Autowired
	public void setBalanceMapper(BalanceMapper balanceMapper) {
		this.balanceMapper = balanceMapper;
	}

	@Override
	public int addBalance(Balance balance) {
		int count = 0;
		try {
			count = balanceMapper.insertSelective(balance);
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
	public int delBalance(int balanceId) {
		int count = 0;
		try {
			count = balanceMapper.deleteByPrimaryKey(balanceId);
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
	public int modifyBalance(Balance balance) {
		int count = 0;
		try {
			count = balanceMapper.updateByPrimaryKeySelective(balance);
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
	public Balance getBalanceByBalanceId(int balanceId) {
		Balance balance = null;
		try {
			balance = balanceMapper.selectByPrimaryKey(balanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return balance;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = balanceMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Balance> getBalanceList(PagingTool pagingTool) {
		List<Balance> balanceList = null;

		try {
			balanceList = balanceMapper.selectBalanceList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return balanceList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
