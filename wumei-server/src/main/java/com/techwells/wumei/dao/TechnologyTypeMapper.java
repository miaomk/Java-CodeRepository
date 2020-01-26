package com.techwells.wumei.dao;

import com.techwells.wumei.domain.TechnologyType;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface TechnologyTypeMapper {
    /**
     * 删除技术人员类型
     *
     * @param typeId 技术人员类型id
     * @return int
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * 添加技术人员类型
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int insert(TechnologyType technologyType);

    /**
     * 添加技术人员类型
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int insertSelective(TechnologyType technologyType);

    /**
     * 通过技术人员类型id获取技术人员信息
     *
     * @param typeId 技术人员类型id
     * @return TechnologyType
     */
    TechnologyType selectByPrimaryKey(Integer typeId);

    /**
     * 修改技术人员类型信息
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int updateByPrimaryKeySelective(TechnologyType technologyType);

    /**
     * 修改技术人员类型信息
     *
     * @param technologyType 技术人员类型信息
     * @return int
     */
    int updateByPrimaryKey(TechnologyType technologyType);

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
    List<TechnologyType> selectTechnologyTypeList(PagingTool pagingTool);

    /**
     * 批量删除技术人员类型
     *
     * @param idArr 技术人员id数组
     * @return int
     */
    int batchDelete(String[] idArr);
}