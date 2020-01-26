package com.techwells.wumei.dao;

import com.techwells.wumei.domain.DemandApply;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.domain.vo.TechnologyApplyVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DemandApplyMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(DemandApply record);

    int insertSelective(DemandApply record);

    DemandApply selectByPrimaryKey(Integer applyId);

    int updateByPrimaryKeySelective(DemandApply record);

    int updateByPrimaryKey(DemandApply record);

    List<DemandApply> selectDemandApplyList(PagingTool pagingTool);

    int countTotal(PagingTool pagingTool);

    Integer applyCount(@Param("demandId") int demandId);

    /**
     * 分页查询我的申请列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<TechnologyApplyVO> getMyApplyList(PagingTool pagingTool);

    /**
     * 分页查询我的申请列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int myApplyCount(PagingTool pagingTool);

    /**
     * 修改申请状态
     *
     * @param demandId     需求id
     * @param technologyId 大师id
     * @return int
     */
    int updateActivated(@Param("demandId") int demandId, @Param("technologyId") int technologyId);

    /**
     * 查询大师是否已经申请该需求
     *
     * @param userId   大师id
     * @param demandId 需求id
     * @return DemandApply
     */
    DemandApply selectIsApply(@Param("technologyId") Integer userId, @Param("demandId") Integer demandId);

    /**
     * 查询申请者列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<HotTechnologyVo> getApplicantsList(PagingTool pagingTool);
}