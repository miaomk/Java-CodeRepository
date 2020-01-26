package com.techwells.wumei.service;

import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.vo.CaseVO;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 大师工作案例业务层
 *
 * @author miao
 */
public interface TechnologyCaseService {

    /**
     * 添加大师工作案例信息
     *
     * @param technologyCase 大师工作案例信息
     * @return int
     */
    int addTechnologyCase(TechnologyCase technologyCase);

    /**
     * 通过id删除大师工作案例信息
     *
     * @param technologyCaseId 大师工作案例id
     * @return TechnologyCase
     */
    int delTechnologyCase(int technologyCaseId);

    /**
     * 更新大师工作案例信息
     *
     * @param technologyCase 大师工作案例信息
     * @return int
     */
    int modifyTechnologyCase(TechnologyCase technologyCase);

    /**
     * 通过id查询大师工作案例信息
     *
     * @param technologyCaseId 大师工作案例id
     * @return TechnologyCase
     */
    TechnologyCase getTechnologyCaseByTechnologyCaseId(int technologyCaseId);

    /**
     * 分页查询工作案例列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页查询大师工作案例列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyCase> getCaseList(PagingTool pagingTool);

    /**
     * 批量删除大师工作案例
     *
     * @param idArr 大师工作案例id数组
     * @return int
     */
    int deleteBatch(String[] idArr);

    /**
     * 批量添加大师工作案例
     *
     * @param caseList 大师工作案例列表
     * @return int
     */
    int batchAddCase(List<TechnologyCase> caseList);

    /**
     * 案例管理，获取案例详情
     *
     * @param caseId 案例id
     * @return CaseVo
     */
    CaseVO getCaseInfo(Integer caseId);

    /**
     * 分页查询大师工作案例列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int technologyCaseTotal(PagingTool pagingTool);

    /**
     * 分页查询大师工作案例列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyCaseVO> getTechnologyCaseList(PagingTool pagingTool);
}
