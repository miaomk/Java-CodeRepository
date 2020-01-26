package com.techwells.wumei.dao;

import com.techwells.wumei.domain.TechnologyEvaluate;
import com.techwells.wumei.domain.vo.TechnologyEvaluateVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TechnologyEvaluateMapper {
    int deleteByPrimaryKey(Integer evaluateId);

    int insert(TechnologyEvaluate record);

    int insertSelective(TechnologyEvaluate record);

    TechnologyEvaluate selectByPrimaryKey(Integer evaluateId);

    int updateByPrimaryKeySelective(TechnologyEvaluate record);

    int updateByPrimaryKey(TechnologyEvaluate record);

    int countTotal(PagingTool pagingTool);

    List<TechnologyEvaluate> selectTechnologyEvaluateList(PagingTool pagingTool);

    /**
     * 查询是否已经评价过
     *
     * @param userId       用户id
     * @param technologyId 大师id
     * @param demandId     需求id
     * @return TechnologyEvaluate
     */
    TechnologyEvaluate selectIsEvaluate(@Param("userId") Integer userId,
                                        @Param("technologyId") Integer technologyId,
                                        @Param("demandId") Integer demandId);

    /**
     * 查询大师案例列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyEvaluateVO> getTechnologyEvaluateList(PagingTool pagingTool);

}