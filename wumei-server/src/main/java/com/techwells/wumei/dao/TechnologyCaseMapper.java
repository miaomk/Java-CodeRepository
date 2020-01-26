package com.techwells.wumei.dao;

import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.vo.CaseVO;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author miao
 */
public interface TechnologyCaseMapper {
    /**
     * 通过id删除技术人员工作案例信息
     *
     * @param caseId 技术人员工作案例id
     * @return TechnologyCase
     */
    int deleteByPrimaryKey(Integer caseId);

    /**
     * 添加技术人员工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return int
     */
    int insert(TechnologyCase technologyCase);

    /**
     * 有选择的添加技术人员工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return int
     */
    int insertSelective(TechnologyCase technologyCase);

    /**
     * 通过id查询技术人员工作案例信息
     *
     * @param caseId 技术人员工作案例id
     * @return TechnologyCase
     */
    TechnologyCase selectByPrimaryKey(Integer caseId);

    /**
     * 有选择的更新技术人员工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return int
     */
    int updateByPrimaryKeySelective(TechnologyCase technologyCase);

    /**
     * 更新技术人员工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return int
     */
    int updateByPrimaryKey(TechnologyCase technologyCase);

    /**
     * 分页查询工作案例列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页查询技术人员工作案例列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyCase> selectTechnologyCaseList(PagingTool pagingTool);

    /**
     * 批量删除工作案例
     *
     * @param idArr 工作案例id数组
     * @return int
     */
    int batchDelete(String[] idArr);

    /**
     * 批量添加大师工作案例
     *
     * @param caseList 大师工作案例list
     * @return int
     */
    int batchAddCase(List<TechnologyCase> caseList);

    /**
     * 案例管理，获取案例详情
     *
     * @param caseId 案例id
     * @return CaseVo
     */
    CaseVO getCaseInfo(@Param("caseId") Integer caseId);
    /**
     * 分页查询技术人员工作案例列表总数
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