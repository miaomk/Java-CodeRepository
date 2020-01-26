package com.techwells.wumei.service;

import com.techwells.wumei.domain.TechnologyType;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 技术人员类型业务层
 *
 * @author miao
 */
public interface TechnologyTypeService {

    /**
     * 添加技术人员类型
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int addTechnologyType(TechnologyType technologyType);

    /**
     * 删除技术人员类型
     *
     * @param technologyTypeId 技术人员类型id
     * @return int
     */
    int delTechnologyType(int technologyTypeId);

    /**
     * 修改技术人员类型信息
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int modifyTechnologyType(TechnologyType technologyType);

    /**
     * 通过技术人员类型id获取技术人员信息
     *
     * @param technologyTypeId 技术人员类型id
     * @return TechnologyType
     */
    TechnologyType getTechnologyTypeByTechnologyTypeId(int technologyTypeId);

    /**
     * 分页获取技术人员类型列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页获取技术人员类型列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyType> getTechnologyTypeList(PagingTool pagingTool);

    /**
     * 批量删除技术人员类型
     *
     * @param idArr 技术人员类型id数组
     * @return int
     */
    int deleteBatch(String[] idArr);
}
