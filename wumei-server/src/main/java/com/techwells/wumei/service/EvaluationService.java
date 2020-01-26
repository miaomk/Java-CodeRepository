package com.techwells.wumei.service;

import com.techwells.wumei.domain.Evaluation;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.domain.rs.RsMerchantEvaluation;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface EvaluationService {

	// 增删改查
	public int addEvaluation(Evaluation evaluation);

	public int delEvaluation(int evaluationId);

	public int modifyEvaluation(Evaluation evaluation);

	Evaluation getEvaluationByEvaluationId(int evaluationId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsEvaluation> getEvaluationList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

    RsMerchantEvaluation getMerchantEvaluationList(PagingTool pagingTool);
}
