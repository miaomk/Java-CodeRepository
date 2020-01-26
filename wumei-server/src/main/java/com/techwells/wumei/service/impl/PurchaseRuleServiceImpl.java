package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.PurchaseRuleMapper;
import com.techwells.wumei.domain.PurchaseRule;
import com.techwells.wumei.service.PurchaseRuleService;
import com.techwells.wumei.util.PagingTool;


@Service("PurchaseRuleService")
public class PurchaseRuleServiceImpl implements PurchaseRuleService {

	private PurchaseRuleMapper purchaseRuleMapper;

	public PurchaseRuleMapper getPurchaseRuleMapper() {
		return purchaseRuleMapper;
	}

	@Autowired
	public void setPurchaseRuleMapper(PurchaseRuleMapper purchaseRuleMapper) {
		this.purchaseRuleMapper = purchaseRuleMapper;
	}

	@Override
	public int addPurchaseRule(PurchaseRule purchaseRule) {
		int count = 0;
		try {
			count = purchaseRuleMapper.insertSelective(purchaseRule);
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
	public int delPurchaseRule(int purchaseRuleId) {
		int count = 0;
		try {
			count = purchaseRuleMapper.deleteByPrimaryKey(purchaseRuleId);
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
	public int modifyPurchaseRule(PurchaseRule purchaseRule) {
		int count = 0;
		try {
			count = purchaseRuleMapper.updateByPrimaryKeySelective(purchaseRule);
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
	public PurchaseRule getPurchaseRuleByPurchaseRuleId(int purchaseRuleId) {
		PurchaseRule purchaseRule = null;
		try {
			purchaseRule = purchaseRuleMapper.selectByPrimaryKey(purchaseRuleId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return purchaseRule;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = purchaseRuleMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<PurchaseRule> getPurchaseRuleList(PagingTool pagingTool) {
		List<PurchaseRule> purchaseRuleList = null;

		try {
			purchaseRuleList = purchaseRuleMapper.selectPurchaseRuleList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return purchaseRuleList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
