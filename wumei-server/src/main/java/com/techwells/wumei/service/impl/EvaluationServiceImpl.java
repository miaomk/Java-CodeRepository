package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.EvaluationMapper;
import com.techwells.wumei.dao.MerchantMapper;
import com.techwells.wumei.domain.Evaluation;
import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.domain.rs.RsMerchantEvaluation;
import com.techwells.wumei.service.EvaluationService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;


@Service("EvaluationService")
public class EvaluationServiceImpl implements EvaluationService {
	@Resource
	private EvaluationMapper evaluationMapper;
	@Resource
	private MerchantMapper merchantMapper;

	@Override
	public int addEvaluation(Evaluation evaluation) {
		int count;
		try {
			count = evaluationMapper.insertSelective(evaluation);
			if (count < 0) {
				throw new Exception("添加评价失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加评价异常！");
		}
		return count;
	}

	@Override
	public int delEvaluation(int evaluationId) {
		int count;
		try {
			count = evaluationMapper.deleteByPrimaryKey(evaluationId);
			if (count < 0) {
				throw new Exception("删除评价失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除评价异常！");
		}
		return count;
	}

	@Override
	public int modifyEvaluation(Evaluation evaluation) {
		int count;
		try {
			count = evaluationMapper.updateByPrimaryKeySelective(evaluation);
			if (count < 0) {
				throw new Exception("修改评价失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改评价异常！");
		}
		return count;
	}

	@Override
	public Evaluation getEvaluationByEvaluationId(int evaluationId) {
		Evaluation evaluation;
		try {
			evaluation = evaluationMapper.selectByPrimaryKey(evaluationId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取评价详情异常！");
		}
		return evaluation;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = evaluationMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取评价总数异常！");
		}
		return count;
	}

	@Override
	public List<RsEvaluation> getEvaluationList(PagingTool pagingTool) {
		List<RsEvaluation> evaluationList;

		try {
			evaluationList = evaluationMapper.selectEvaluationList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取评价列表异常");
		}
		return evaluationList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RsMerchantEvaluation getMerchantEvaluationList(PagingTool pagingTool) {
		RsMerchantEvaluation rsMerchantEvaluation = new RsMerchantEvaluation();
		try {
			Integer merchantId = (Integer) pagingTool.getParams().get("merchantId");
			Merchant merchantInfo = merchantMapper.selectByPrimaryKey(merchantId);

			List<RsEvaluation> merchantEvaluationList = evaluationMapper.getMerchantEvaluationList(pagingTool);
			rsMerchantEvaluation.setMerchantIcon(merchantInfo.getMerchantIcon());

			//计算好评率
			if (merchantEvaluationList.size() == 0) {
				rsMerchantEvaluation.setGoodRate((double) 0);
			} else {
				int goodCount = 0;
				for (RsEvaluation rsEvaluation : merchantEvaluationList) {

					if (rsEvaluation.getEvaluationLevel() == 1) {
						goodCount = goodCount + 1;
					}
				}
				BigDecimal goodRate = new BigDecimal(goodCount).divide
						(new BigDecimal(merchantEvaluationList.size()), 2, ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));

				rsMerchantEvaluation.setGoodRate(goodRate.doubleValue());
			}

			rsMerchantEvaluation.setMerchantName(merchantInfo.getMerchantName());
			rsMerchantEvaluation.setMerchantId(merchantId);
			rsMerchantEvaluation.setRsEvaluationList(merchantEvaluationList);
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException("获取店铺评价异常");
		}


		return rsMerchantEvaluation;
	}

}
