package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.bo.DemandBO;
import com.techwells.wumei.domain.vo.DemandVO;
import com.techwells.wumei.domain.vo.MyDemandVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DemandMapper {
    /**
     * 发布需求
     *
     * @param demand 需求信息
     * @return int
     */
    int insert(Demand demand);

    /**
     * 发布需求
     *
     * @param demand 需求信息
     * @return int
     */
    int insertSelective(Demand demand);

    /**
     * 删除需求
     *
     * @param demandId 需求id
     * @return int
     */
    int deleteByPrimaryKey(Integer demandId);

    /**
     * 获取需求详情
     *
     * @param demandId 需求id
     * @return Demand
     */
    Demand selectByPrimaryKey(Integer demandId);

    /**
     * 修改需求
     *
     * @param demand 需求信息
     * @return int
     */
    int updateByPrimaryKeySelective(Demand demand);

    /**
     * 修改需求
     *
     * @param demand 需求信息
     * @return int
     */
    int updateByPrimaryKey(Demand demand);

    /**
     * 获取需求总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 获取需求列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<DemandVO> selectDemandList(PagingTool pagingTool);

    /**
     * 获取需求详情
     *
     * @param demandId 需求id
     * @return DemandVo
     */
    DemandVO getDemandInfo(@Param("demandId") int demandId);

    /**
     * 更新时间已经结束的需求
     *
     * @return int
     */
    int updateOverActivated();

    /**
     * 更新时间到未有人接单的需求
     * @return int
     */
    int updateActivated();

    /**
     * 获取我的发布需求列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<MyDemandVO> getMyDemandList(PagingTool pagingTool);

    /**
     * 查询是否有重复的需求
     *
     * @param demandBo 需求信息
     * @return Demand
     */
    Demand getIsRepeat(DemandBO demandBo);
}