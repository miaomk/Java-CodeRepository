package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TechnologyCaseMapper;
import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.vo.CaseVO;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.service.TechnologyCaseService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 技术人员工作案例业务实现类
 *
 * @author miao
 */
@Service("TechnologyCaseService")
public class TechnologyCaseServiceImpl implements TechnologyCaseService {
    @Resource
    private TechnologyCaseMapper technologyCaseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTechnologyCase(TechnologyCase technologyCase) {
        int count;
        try {
            count = technologyCaseMapper.insertSelective(technologyCase);
            if (count < 0) {
                throw new Exception("添加案例失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加案例异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delTechnologyCase(int technologyCaseId) {
        int count;
        try {
            count = technologyCaseMapper.deleteByPrimaryKey(technologyCaseId);
            if (count < 0) {
                throw new Exception("删除案例失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除案例异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyTechnologyCase(TechnologyCase technologyCase) {
        int count;
        try {
            count = technologyCaseMapper.updateByPrimaryKeySelective(technologyCase);
            if (count < 0) {
                throw new Exception("修改案例失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改案例异常！");
        }
        return count;
    }

    @Override
    public TechnologyCase getTechnologyCaseByTechnologyCaseId(int technologyCaseId) {
        TechnologyCase technologyCase;
        try {
            technologyCase = technologyCaseMapper.selectByPrimaryKey(technologyCaseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例详情异常！");
        }
        return technologyCase;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = technologyCaseMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例总数异常！");
        }
        return count;
    }

    @Override
    public int technologyCaseTotal(PagingTool pagingTool) {
        int count;

        try {
            count = technologyCaseMapper.technologyCaseTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例总数异常！");
        }
        return count;
    }

    @Override
    public List<TechnologyCaseVO> getTechnologyCaseList(PagingTool pagingTool) {
        List<TechnologyCaseVO> technologyCaseList;

        try {
            technologyCaseList = technologyCaseMapper.getTechnologyCaseList(pagingTool);
            if (CollectionUtils.isNotEmpty(technologyCaseList)) {
                technologyCaseList.forEach(data -> data.setImageCover(data.getImageUrl().split(",")[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例列表异常");
        }
        return technologyCaseList;
    }

    @Override
    public List<TechnologyCase> getCaseList(PagingTool pagingTool) {
        List<TechnologyCase> technologyCaseList;

        try {
            technologyCaseList = technologyCaseMapper.selectTechnologyCaseList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例列表异常");
        }
        return technologyCaseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        int count;
        try {
            count = technologyCaseMapper.batchDelete(idArr);
            if (count < 0) {
                throw new Exception("批量删除工作案例失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量删除工作案例异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddCase(List<TechnologyCase> caseList) {
        int count;
        try {
            count = technologyCaseMapper.batchAddCase(caseList);
            if (count < 0) {
                throw new Exception("批量添加案例失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量添加案例异常！");
        }
        return count;
    }

    @Override
    public CaseVO getCaseInfo(Integer caseId) {
        CaseVO technologyCase;
        try {
            technologyCase = technologyCaseMapper.getCaseInfo(caseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取案例详情异常！");
        }
        return technologyCase;
    }

}
