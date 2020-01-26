package com.techwells.wumei.service;

import com.techwells.wumei.domain.TechnologyEvaluate;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 客户评价业务层
 *
 * @author miao
 */
public interface TechnologyEvaluateService {

	int addTechnologyEvaluate(TechnologyEvaluate technologyEvaluate);

	int delTechnologyEvaluate(int technologyEvaluateId);

	int modifyTechnologyEvaluate(TechnologyEvaluate technologyEvaluate);

	TechnologyEvaluate getTechnologyEvaluateByTechnologyEvaluateId(int technologyEvaluateId);

	int countTotal(PagingTool pagingTool);

	List<TechnologyEvaluate> getTechnologyEvaluateList(PagingTool pagingTool);

	int deleteBatch(String[] idArr);

	TechnologyEvaluate selectIsEvaluate(Integer userId, Integer technologyId, Integer demandId);
}
