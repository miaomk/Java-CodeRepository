package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.PurchaseMapper;
import com.techwells.wumei.domain.Purchase;
import com.techwells.wumei.service.PurchaseService;
import com.techwells.wumei.util.PagingTool;


@Service("PurchaseService")
public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseMapper purchaseMapper;

	public PurchaseMapper getPurchaseMapper() {
		return purchaseMapper;
	}

	@Autowired
	public void setPurchaseMapper(PurchaseMapper purchaseMapper) {
		this.purchaseMapper = purchaseMapper;
	}

	@Override
	public int addPurchase(Purchase purchase) {
		int count = 0;
		try {
			count = purchaseMapper.insertSelective(purchase);
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
	public int delPurchase(int purchaseId) {
		int count = 0;
		try {
			count = purchaseMapper.deleteByPrimaryKey(purchaseId);
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
	public int modifyPurchase(Purchase purchase) {
		int count = 0;
		try {
			count = purchaseMapper.updateByPrimaryKeySelective(purchase);
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
	public Purchase getPurchaseByPurchaseId(int purchaseId) {
		Purchase purchase = null;
		try {
			purchase = purchaseMapper.selectByPrimaryKey(purchaseId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return purchase;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = purchaseMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Purchase> getPurchaseList(PagingTool pagingTool) {
		List<Purchase> purchaseList = null;

		try {
			purchaseList = purchaseMapper.selectPurchaseList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return purchaseList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
