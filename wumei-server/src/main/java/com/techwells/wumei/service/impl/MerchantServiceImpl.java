package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.*;
import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.domain.rs.RsMerchant;
import com.techwells.wumei.service.MerchantService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;


@Service("MerchantService")
public class MerchantServiceImpl implements MerchantService {
	@Resource
	private MerchantMapper merchantMapper;
	@Resource
	private CouponMapper couponMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private WithdrawalRecordMapper withdrawalRecordMapper;
	@Resource
	private EvaluationMapper evaluationMapper;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addMerchant(Merchant merchant) {
		int count;
		try {
			count = merchantMapper.insertSelective(merchant);
			if (count < 0) {
				throw new Exception("添加商户失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加商户异常！");
		}
		return count;
	}

	@Override
	public int delMerchant(int merchantId) {
		int count;
		try {
			count = merchantMapper.deleteByPrimaryKey(merchantId);
			if (count < 0) {
				throw new Exception("删除商户失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除商户异常！");
		}
		return count;
	}

	@Override
	public int modifyMerchant(Merchant merchant) {
		int count;
		try {
			count = merchantMapper.updateByPrimaryKeySelective(merchant);
			if (count < 0) {
				throw new Exception("修改商户失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改商户异常！");
		}
		return count;
	}

	@Override
	public Merchant getMerchantByMerchantId(int merchantId) {
		Merchant merchant;
		try {
			merchant = merchantMapper.selectByPrimaryKey(merchantId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取商户详情异常！");
		}
		return merchant;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = merchantMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取商户总数异常！");
		}
		return count;
	}

	@Override
	public List<Merchant> getMerchantList(PagingTool pagingTool) {
		List<Merchant> merchantList;

		try {
			merchantList = merchantMapper.selectMerchantList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取商户列表异常");
		}
		return merchantList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public RsMerchant getMerchantInfo(String merchantId) {
		RsMerchant rsMerchant;

		try {
			rsMerchant = merchantMapper.getMerchantInfo(merchantId);

			if (null == rsMerchant) {
				throw new Exception("查询店铺信息失败");
			}
			//查询店铺优惠券列表
			List<Coupon> couponList = couponMapper.getCouponListByMerchantId(merchantId);
			if (CollectionUtils.isNotEmpty(couponList)) {
				rsMerchant.setCouponList(couponList);
			}
			//查询好评率
			PagingTool pagingTool = new PagingTool(1, 999);
			Map<String, Object> map = new HashMap<>(12);
			map.put("merchantId", merchantId);
			pagingTool.setParams(map);
			List<RsEvaluation> merchantEvaluationList = evaluationMapper.getMerchantEvaluationList(pagingTool);
			int goodCount = (int) merchantEvaluationList.stream().filter(data -> data.getEvaluationLevel() == 1).count();
			BigDecimal goodRate = new BigDecimal(goodCount).divide
					(new BigDecimal(merchantEvaluationList.size()), 2, ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
			rsMerchant.setPraiseRate(goodRate.intValue());

		} catch (Exception e) {
			throw new RuntimeException("查询店铺信息异常");
		}

		return rsMerchant;
	}

	@Override
	public RsMerchant getMerchantCenterInfo(Integer merchantId) {

		RsMerchant rsMerchant = new RsMerchant();
		try {
			Merchant merchant = merchantMapper.selectByPrimaryKey(merchantId);

			if (null == merchant) {
				throw new RuntimeException("商家不存在");
			}

			//订单状态 1表示待付款，2表示待发货，3表示待收货，4待评价，5表示交易完成
			//查询待付款
			int waitSend = orderMapper.countOrder(merchantId, 1);
			//查询待发货
			int waitPay = orderMapper.countOrder(merchantId, 2);
			//查询待评论
			int refundAfter = orderMapper.countOrder(merchantId, 4);
			//查询退货售后
			int waitEvaluation = orderMapper.countOrder(merchantId, 6);

			rsMerchant.setMerchantId(merchantId);
			rsMerchant.setMerchantName(merchant.getMerchantName());
			rsMerchant.setDescription(merchant.getDescription());
			rsMerchant.setMerchantIcon(merchant.getMerchantIcon());
			rsMerchant.setWaitSend(waitSend);
			rsMerchant.setWaitPay(waitPay);
			rsMerchant.setRefundAfter(refundAfter);
			rsMerchant.setWaitEvaluation(waitEvaluation);
		}
		catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("查询商家中心信息异常");
		}

		return rsMerchant;
	}

    @Override
    public MyAccountVO getMerchantAccountInfo(Integer merchantId) {
		MyAccountVO myAccountVO = new MyAccountVO();
		try {
			List<BillVO> billList = new ArrayList<>();
			//提现金额
			BigDecimal withdrawalCount = withdrawalRecordMapper.getWithdrawalCount(String.valueOf(merchantId), 1);
			if (withdrawalCount == null) {
				withdrawalCount = BigDecimal.ZERO;
			}
			//提现记录
			List<BillVO> withdrawalList = withdrawalRecordMapper.getWithdrawalList(String.valueOf(merchantId), 1);
			for (BillVO billVO : withdrawalList) {
				billVO.setBillType(1);

				billList.add(billVO);
			}
			BigDecimal income = BigDecimal.ZERO;
			//查询商品收入和收入金额
			List<BillVO> orderList = orderMapper.merchantOrderList(merchantId);
			for (BillVO billVO : orderList) {
				income = income.add(billVO.getPayment());

				billVO.setBillType(2);
				billList.add(billVO);
			}


			myAccountVO.setUserId(merchantId);
			myAccountVO.setWithdrawalCount(withdrawalCount);
			myAccountVO.setTotalIncome(income);
			myAccountVO.setBalance(income.subtract(withdrawalCount));
			myAccountVO.setBillList(billList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("查询商户账户信息异常");
		}

		return myAccountVO;
	}

	@Override
	public List<Merchant> getRecommendMerchant(PagingTool pagingTool) {
		List<Merchant> merchantList;
		//查询推荐厂家列表
		Map<String, Object> map = new HashMap<>(12);
		map.put("isRecommend", 1);
		pagingTool.setParams(map);

		try {
			merchantList = merchantMapper.selectMerchantList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取商户列表异常");
		}
		return merchantList;
	}

}
