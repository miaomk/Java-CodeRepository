package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TechnologyOrderMapper;
import com.techwells.wumei.domain.TechnologyOrder;
import com.techwells.wumei.service.TechnologyOrderService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 大师订单业务实现类
 *
 * @author miao
 */
@Service("TechnologyOrderService")
public class TechnologyOrderServiceImpl implements TechnologyOrderService {
    @Resource
    private TechnologyOrderMapper technologyOrderMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTechnologyOrder(TechnologyOrder technologyOrder) {
        int count;
        try {
            count = technologyOrderMapper.insertSelective(technologyOrder);
            if (count < 0) {
                throw new Exception("添加大师订单失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加大师订单异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delTechnologyOrder(int technologyOrderId) {
        int count;
        try {
            count = technologyOrderMapper.deleteByPrimaryKey(technologyOrderId);
            if (count < 0) {
                throw new Exception("删除大师订单失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除大师订单异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyTechnologyOrder(TechnologyOrder technologyOrder) {
        int count;
        try {
            count = technologyOrderMapper.updateByPrimaryKeySelective(technologyOrder);
            if (count < 0) {
                throw new Exception("修改大师订单失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改大师订单异常！");
        }
        return count;
    }

    @Override
    public TechnologyOrder getTechnologyOrderInfo(int technologyOrderId) {
        TechnologyOrder technologyOrder;
        try {
            technologyOrder = technologyOrderMapper.selectByPrimaryKey(technologyOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师订单详情异常！");
        }
        return technologyOrder;
    }

    // 获取列表
    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = technologyOrderMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师订单总数异常！");
        }
        return count;
    }

    @Override
    public List<TechnologyOrder> getTechnologyOrderList(PagingTool pagingTool) {
        List<TechnologyOrder> technologyOrderList;

        try {
            technologyOrderList = technologyOrderMapper.selectTechnologyOrderList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师订单列表异常");
        }
        return technologyOrderList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrderList(List<TechnologyOrder> updateList) {
        int count;

        try {
            count = technologyOrderMapper.batchUpdateOrder(updateList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量更新大师订单异常！");
        }
        return count;
    }

}
