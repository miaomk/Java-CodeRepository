package com.techwells.wumei.service;

import com.techwells.wumei.domain.DemandApply;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.domain.vo.TechnologyApplyVO;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface DemandApplyService {

    int addDemandApply(DemandApply demandApply);

    int delDemandApply(int demandApplyId);

    int modifyDemandApply(DemandApply demandApply);

    DemandApply getDemandApplyByDemandApplyId(int demandApplyId);

    int countTotal(PagingTool pagingTool);

    List<DemandApply> getDemandApplyList(PagingTool pagingTool);

    int deleteBatch(String[] idArr);

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
    int updateActivated(int demandId, int technologyId);

    /**
     * 查询大师是否已经申请该需求
     *
     * @param userId   大师id
     * @param demandId 需求id
     * @return DemandApply
     */
    DemandApply selectIsApply(Integer userId, Integer demandId);

    /**
     * 分页查询申请者信息
     *
     * @param pagingTool 分页
     * @return List
     */
    List<HotTechnologyVo> getApplicantsList(PagingTool pagingTool);
}
