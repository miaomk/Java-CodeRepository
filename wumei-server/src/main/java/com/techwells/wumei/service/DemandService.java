package com.techwells.wumei.service;

import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.bo.DemandBO;
import com.techwells.wumei.domain.vo.DemandVO;
import com.techwells.wumei.domain.vo.MyDemandVO;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface DemandService {

    /**
     * 发布需求
     *
     * @param demand 需求信息
     * @return int
     */
    int addDemand(Demand demand);

    /**
     * 删除需求
     *
     * @param demandId 需求id
     * @return int
     */
    int delDemand(int demandId);

    /**
     * 修改需求
     *
     * @param demand 需求信息
     * @return int
     */
    int modifyDemand(Demand demand);

    /**
     * 获取需求详情
     *
     * @param demandId 需求id
     * @return DemandVO
     */
    DemandVO getDemandInfo(int demandId);

    /**
     * 获取需求详情
     *
     * @param demandId 需求id
     * @return Demand
     */
    Demand getDemandByDemandId(int demandId);

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
    List<DemandVO> getDemandList(PagingTool pagingTool);

    /**
     * 批量删除需求
     *
     * @param idArr 需求id数组
     * @return int
     */
    int deleteBatch(String[] idArr);

    /**
     * 更新订单需求
     *
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

    /**
     * 查询收藏的需求信息
     *
     * @param relationIdList 需求id列表
     * @param pageTool       分页
     * @return List
     */
    List<MyDemandVO> getCollectDemandList(List<Integer> relationIdList, PagingTool pageTool);
}
