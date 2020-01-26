package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TechnologyEvaluateMapper;
import com.techwells.wumei.domain.TechnologyEvaluate;
import com.techwells.wumei.service.TechnologyEvaluateService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 客户评价业务实现类
 *
 * @author miao
 */
@Service("TechnologyEvaluateService")
public class TechnologyEvaluateServiceImpl implements TechnologyEvaluateService {
    @Resource
    private TechnologyEvaluateMapper technologyEvaluateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTechnologyEvaluate(TechnologyEvaluate technologyEvaluate) {
        int count;
        try {
            count = technologyEvaluateMapper.insertSelective(technologyEvaluate);
            if (count < 0) {
                throw new Exception("添加客户评价失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加客户评价异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delTechnologyEvaluate(int technologyEvaluateId) {
        int count;
        try {
            count = technologyEvaluateMapper.deleteByPrimaryKey(technologyEvaluateId);
            if (count < 0) {
                throw new Exception("删除客户评价失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除客户评价异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyTechnologyEvaluate(TechnologyEvaluate technologyEvaluate) {
        int count;
        try {
            count = technologyEvaluateMapper.updateByPrimaryKeySelective(technologyEvaluate);
            if (count < 0) {
                throw new Exception("修改客户评价失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改客户评价异常！");
        }
        return count;
    }

    @Override
    public TechnologyEvaluate getTechnologyEvaluateByTechnologyEvaluateId(int technologyEvaluateId) {
        TechnologyEvaluate technologyEvaluate;
        try {
            technologyEvaluate = technologyEvaluateMapper.selectByPrimaryKey(technologyEvaluateId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取客户评价详情异常！");
        }
        return technologyEvaluate;
    }

    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = technologyEvaluateMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取客户评价总数异常！");
        }
        return count;
    }

    @Override
    public List<TechnologyEvaluate> getTechnologyEvaluateList(PagingTool pagingTool) {
        List<TechnologyEvaluate> technologyEvaluateList = null;

        try {
            technologyEvaluateList = technologyEvaluateMapper.selectTechnologyEvaluateList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取客户评价列表异常");
        }
        return technologyEvaluateList;
    }

    @Override
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public TechnologyEvaluate selectIsEvaluate(Integer userId, Integer technologyId, Integer demandId) {
        TechnologyEvaluate technologyEvaluate;
        try {
            technologyEvaluate = technologyEvaluateMapper.selectIsEvaluate(userId, technologyId, demandId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取客户评价详情异常！");
        }
        return technologyEvaluate;
    }

}
