package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.DemandApplyMapper;
import com.techwells.wumei.dao.DemandMapper;
import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.DemandApply;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.domain.vo.TechnologyApplyVO;
import com.techwells.wumei.service.DemandApplyService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("DemandApplyService")
public class DemandApplyServiceImpl implements DemandApplyService {

    @Resource
    private DemandApplyMapper demandApplyMapper;
    @Resource
    private DemandMapper demandMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addDemandApply(DemandApply demandApply) {
        int count;
        try {
            count = demandApplyMapper.insertSelective(demandApply);
            if (count < 0) {
                throw new Exception("添加需求申请失败!");
            }
            Demand demand = new Demand();
            demand.setDemandId(demandApply.getDemandId());
            //有人申请
            demand.setActivated(2);
            demandMapper.updateByPrimaryKeySelective(demand);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加需求申请异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delDemandApply(int demandApplyId) {
        int count;
        try {
            count = demandApplyMapper.deleteByPrimaryKey(demandApplyId);
            if (count < 0) {
                throw new Exception("删除需求申请失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除需求申请异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyDemandApply(DemandApply demandApply) {
        int count;
        try {
            count = demandApplyMapper.updateByPrimaryKeySelective(demandApply);
            if (count < 0) {
                throw new Exception("修改需求申请失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改需求申请异常！");
        }
        return count;
    }

    @Override
    public DemandApply getDemandApplyByDemandApplyId(int demandApplyId) {
        DemandApply demandApply;
        try {
            demandApply = demandApplyMapper.selectByPrimaryKey(demandApplyId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取需求申请详情异常！");
        }
        return demandApply;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = demandApplyMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取需求申请总数异常！");
        }
        return count;
    }

    @Override
    public List<DemandApply> getDemandApplyList(PagingTool pagingTool) {
        List<DemandApply> demandApplyList;

        try {
            demandApplyList = demandApplyMapper.selectDemandApplyList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取需求申请列表异常");
        }
        return demandApplyList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public List<TechnologyApplyVO> getMyApplyList(PagingTool pagingTool) {
        List<TechnologyApplyVO> myApplyList;

        try {
            myApplyList = demandApplyMapper.getMyApplyList(pagingTool);
            StringBuilder demandTimeBuilder;
            for (TechnologyApplyVO technologyApplyVO : myApplyList) {
                demandTimeBuilder = new StringBuilder();
                technologyApplyVO.setDemandTime(demandTimeBuilder
                        .append(technologyApplyVO.getStartDate())
                        .append("至")
                        .append(technologyApplyVO.getEndDate())
                        .toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取我的需求申请列表异常");
        }
        return myApplyList;
    }

    @Override
    public int myApplyCount(PagingTool pagingTool) {
        int count;

        try {
            count = demandApplyMapper.myApplyCount(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取我的需求申请列表总数异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateActivated(int demandId, int technologyId) {
        int count;
        try {
            count = demandApplyMapper.updateActivated(demandId, technologyId);
            if (count < 0) {
                throw new Exception("修改需求申请失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改需求申请异常！");
        }
        return count;
    }

    @Override
    public DemandApply selectIsApply(Integer userId, Integer demandId) {
        DemandApply demandApply;
        try {
            demandApply = demandApplyMapper.selectIsApply(userId, demandId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("查询大师是否申请过需求异常！");
        }
        return demandApply;
    }

    @Override
    public List<HotTechnologyVo> getApplicantsList(PagingTool pagingTool) {
        List<HotTechnologyVo> technologyList;

        try {

            technologyList = demandApplyMapper.getApplicantsList(pagingTool);
            if (CollectionUtils.isNotEmpty(technologyList)) {
                technologyList.forEach(data -> {
                    if (CollectionUtils.isNotEmpty(data.getCaseList())) {
                        StringBuilder caseImageBuilder = new StringBuilder();
                        data.getCaseList().forEach(caseData -> {
                            //拼接案例图片
                            caseImageBuilder.append(caseData.getImageUrl()).append(",");
                        });
                        //有几个案例图
                        String[] imageArray = caseImageBuilder.toString().split(",");
                        StringBuilder imageBuilder = new StringBuilder();
                        if (imageArray.length >= 3) {
                            for (int i = 0; i < 3; i++) {
                                imageBuilder.append(imageArray[i]).append(",");
                            }
                        } else {
                            for (String s : imageArray) {
                                imageBuilder.append(s).append(",");
                            }
                        }
                        String caseImage = imageBuilder.toString().substring(0, imageBuilder.length() - 1);
                        data.setCaseImage(caseImage.split(","));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("查询申请者列表异常！");
        }
        return technologyList;
    }
}
