package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.DealMapper;
import com.techwells.wumei.domain.Deal;
import com.techwells.wumei.service.DealService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("DealService")
public class DealServiceImpl implements DealService {
	@Resource
	private DealMapper dealMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addDeal(Deal deal) {
		int count;
		try {
			count = dealMapper.insertSelective(deal);
			if (count < 0) {
				throw new Exception("添加交易记录失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加交易记录异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delDeal(int dealId) {
		int count;
		try {
			count = dealMapper.deleteByPrimaryKey(dealId);
			if (count < 0) {
				throw new Exception("删除交易记录失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除交易记录异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyDeal(Deal deal) {
		int count;
		try {
			count = dealMapper.updateByPrimaryKeySelective(deal);
			if (count < 0) {
				throw new Exception("修改交易记录失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改交易记录异常！");
		}
		return count;
	}

	@Override
	public Deal getDealByDealId(int dealId) {
		Deal deal;
		try {
			deal = dealMapper.selectByPrimaryKey(dealId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取交易记录详情异常！");
		}
		return deal;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = dealMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取交易记录总数异常！");
		}
		return count;
	}

	@Override
	public List<Deal> getDealList(PagingTool pagingTool) {
		List<Deal> dealList;

		try {
			dealList = dealMapper.selectDealList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取交易列表异常");
		}
		return dealList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDelList(List<Deal> updateList) {
		int count;

		try {
			count = dealMapper.batchUpdateOrder(updateList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("批量更新交易订单异常！");
		}
		return count;
	}

}
