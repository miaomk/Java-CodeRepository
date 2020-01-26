package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Evaluation;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer evaluationId);

    int insert(Evaluation record);

    int insertSelective(Evaluation record);

    Evaluation selectByPrimaryKey(Integer evaluationId);

    int updateByPrimaryKeySelective(Evaluation record);

    int updateByPrimaryKeyWithBLOBs(Evaluation record);

    int updateByPrimaryKey(Evaluation record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<RsEvaluation> selectEvaluationList(PagingTool pagingTool);
 	int batchDelete(@Param("ids")String[] ids);

    List<RsEvaluation> getMerchantEvaluationList(PagingTool pagingTool);
}