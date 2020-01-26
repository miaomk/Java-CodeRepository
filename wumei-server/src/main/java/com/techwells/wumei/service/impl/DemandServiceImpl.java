package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.DemandApplyMapper;
import com.techwells.wumei.dao.DemandMapper;
import com.techwells.wumei.dao.PVMapper;
import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.bo.DemandBO;
import com.techwells.wumei.domain.vo.DemandVO;
import com.techwells.wumei.domain.vo.MyDemandVO;
import com.techwells.wumei.service.DemandService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * 用户发布需求业务实现类
 *
 * @author miao
 */
@Service("DemandService")
public class DemandServiceImpl implements DemandService {
    @Resource
    private DemandMapper demandMapper;
    @Resource
    private PVMapper pvMapper;
    @Resource
    private DemandApplyMapper demandApplyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addDemand(Demand demand) {
        int count;
        try {
            count = demandMapper.insertSelective(demand);
            if (count < 0) {
                throw new Exception("发布用户需求失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("发布用户需求异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delDemand(int demandId) {
        int count;
        try {
            count = demandMapper.deleteByPrimaryKey(demandId);
            if (count < 0) {
                throw new Exception("删除用户需求失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除用户需求异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyDemand(Demand demand) {
        int count;
        try {
            count = demandMapper.updateByPrimaryKeySelective(demand);
            if (count < 0) {
                throw new Exception("修改用户需求失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改用户需求异常！");
        }
        return count;
    }

    @Override
    public DemandVO getDemandInfo(int demandId) {
        DemandVO demandVo;
        try {
            demandVo = demandMapper.getDemandInfo(demandId);
            StringBuilder timeBuilder = new StringBuilder();
            //封装时间 例：2020-01-08至2020-01-09
            demandVo.setDemandTime(timeBuilder.append(demandVo.getStartTime()).append("至").
                    append(demandVo.getEndTime()).toString());
            //计算需求天数
            int demandDayCount = (int) demandVo.getStartTime().until(demandVo.getEndTime(), ChronoUnit.DAYS) + 1;
            demandVo.setDemandDayCount(demandDayCount);
            //查询浏览人数
            demandVo.setPvCount(pvMapper.pvTotal(demandId, 6));
            //查询申请人数
            demandVo.setApplyCount(demandApplyMapper.applyCount(demandId));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取用户需求详情异常！");
        }
        return demandVo;
    }

    @Override
    public Demand getDemandByDemandId(int demandId) {
        Demand demand;
        try {
            demand = demandMapper.selectByPrimaryKey(demandId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取用户需求详情异常！");
        }
        return demand;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = demandMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取用户需求总数异常！");
        }
        return count;
    }

    @Override
    public List<DemandVO> getDemandList(PagingTool pagingTool) {
        List<DemandVO> demandList;

        try {
            demandList = demandMapper.selectDemandList(pagingTool);
            //拼接时间
            if (CollectionUtils.isNotEmpty(demandList)) {
                StringBuilder timeBuilder;
                for (DemandVO demandVo : demandList) {
                    timeBuilder = new StringBuilder();
                    demandVo.setDemandTime(timeBuilder.append(demandVo.getStartTime()).append("至").append(demandVo.getEndTime()).toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取用户需求列表异常");
        }
        return demandList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateActivated() {

        int count;
        try {
            //更新时间结束的需求
            count = demandMapper.updateOverActivated();
            //更新需求开始未接单的需求状态
            count += demandMapper.updateActivated();

            if (count < 0) {
                throw new Exception("修改用户需求状态失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改用户需求状态异常！");
        }
        return count;


    }

    @Override
    public List<MyDemandVO> getMyDemandList(PagingTool pagingTool) {
        List<MyDemandVO> myDemandList;

        try {
            myDemandList = demandMapper.getMyDemandList(pagingTool);
            StringBuilder demandTimeBuilder;
            for (MyDemandVO demandVO : myDemandList) {
                demandTimeBuilder = new StringBuilder();
                demandVO.setDemandTime(demandTimeBuilder
                        .append(demandVO.getStartDate())
                        .append("至")
                        .append(demandVO.getEndDate())
                        .toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取我的需求申请列表异常");
        }
        return myDemandList;
    }

    @Override
    public Demand getIsRepeat(DemandBO demandBo) {
        Demand demand;
        try {
            demand = demandMapper.getIsRepeat(demandBo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取需求是否重复异常！");
        }
        return demand;
    }

    @Override
    public List<MyDemandVO> getCollectDemandList(List<Integer> relationIdList, PagingTool pageTool) {

        return null;
    }

}
