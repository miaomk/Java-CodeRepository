package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TechnologyTypeMapper;
import com.techwells.wumei.domain.TechnologyType;
import com.techwells.wumei.service.TechnologyTypeService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 技术人员类型业务实现类
 *
 * @author miao
 */
@Service
public class TechnologyTypeServiceImpl implements TechnologyTypeService {

    @Resource
    private TechnologyTypeMapper technologyTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTechnologyType(TechnologyType technologyType) {
        int count;
        try {
            count = technologyTypeMapper.insertSelective(technologyType);
            if (count < 0) {
                throw new Exception("添加技术人员类型失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加技术人员类型异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delTechnologyType(int technologyTypeId) {
        int count;
        try {
            count = technologyTypeMapper.deleteByPrimaryKey(technologyTypeId);
            if (count < 0) {
                throw new Exception("删除技术人员类型失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除技术人员类型异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyTechnologyType(TechnologyType technologyType) {
        int count;
        try {
            count = technologyTypeMapper.updateByPrimaryKeySelective(technologyType);
            if (count < 0) {
                throw new Exception("修改技术人员类型失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改技术人员类型异常！");
        }
        return count;
    }

    @Override
    public TechnologyType getTechnologyTypeByTechnologyTypeId(int technologyTypeId) {
        TechnologyType technologyType;
        try {
            technologyType = technologyTypeMapper.selectByPrimaryKey(technologyTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取技术人员类型详情异常！");
        }
        return technologyType;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = technologyTypeMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取技术人员类型总数异常！");
        }
        return count;
    }

    @Override
    public List<TechnologyType> getTechnologyTypeList(PagingTool pagingTool) {
        List<TechnologyType> technologyTypeList;

        try {
            technologyTypeList = technologyTypeMapper.selectTechnologyTypeList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取技术人员类型列表异常");
        }
        return technologyTypeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        int count;
        try {
            count = technologyTypeMapper.batchDelete(idArr);
            if (count < 0) {
                throw new Exception("批量操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量删除技术人员类型异常！");
        }
        return count;
    }

}
