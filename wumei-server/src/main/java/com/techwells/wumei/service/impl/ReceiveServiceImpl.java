package com.techwells.wumei.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.wumei.dao.CouponMapper;
import com.techwells.wumei.dao.ReceiveMapper;
import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Receive;
import com.techwells.wumei.domain.rs.RsReceive;
import com.techwells.wumei.service.ReceiveService;
import com.techwells.wumei.util.PagingTool;


@Service("ReceiveService")
public class ReceiveServiceImpl implements ReceiveService {

    private ReceiveMapper receiveMapper;

    @Resource
    private CouponMapper couponMapper;

    public ReceiveMapper getReceiveMapper() {
        return receiveMapper;
    }

    @Autowired
    public void setReceiveMapper(ReceiveMapper receiveMapper) {
        this.receiveMapper = receiveMapper;
    }

    @Override
    @Transactional
    public int addReceive(Receive receive) {
        int count = 0, i = 0;
        try {
            count = receiveMapper.insertSelective(receive);
            Coupon coupon = couponMapper.selectByPrimaryKey(receive.getCouponId());
            coupon.setCouponId(receive.getCouponId());
            //添加记录的同时优惠券领取数量加一
            coupon.setReceiveCount(coupon.getReceiveCount() + 1);
            coupon.setUpdatedDate(new Date());
            i = couponMapper.updateByPrimaryKeySelective(coupon);
            if (count < 0 && i < 0) {
                throw new Exception("添加模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加模板异常！");
        }
        return count;
    }

    @Override
    public int delReceive(int receiveId) {
        int count = 0;
        try {
            count = receiveMapper.deleteByPrimaryKey(receiveId);
            if (count < 0) {
                throw new Exception("删除模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除模板异常！");
        }
        return count;
    }

    @Override
    public int modifyReceive(Receive receive) {
        int count = 0;
        try {
            count = receiveMapper.updateByPrimaryKeySelective(receive);
            if (count < 0) {
                throw new Exception("修改模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改模板异常！");
        }
        return count;
    }

    @Override
    public Receive getReceiveByReceiveId(int receiveId) {
        Receive receive = null;
        try {
            receive = receiveMapper.selectByPrimaryKey(receiveId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板详情异常！");
        }
        return receive;
    }

    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = receiveMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券总数异常！");
        }
        return count;
    }

    @Override
    public int myReceiveCount(PagingTool pagingTool) {
        int count;

        try {
            count = receiveMapper.myReceiveCount(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券总数异常！");
        }
        return count;
    }

    @Override
    public List<RsReceive> getReceiveList(PagingTool pagingTool) {
        List<RsReceive> receiveList;

        try {
            receiveList = receiveMapper.selectReceiveList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板列表异常");
        }
        return receiveList;
    }

    @Override
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Integer countUserAndCoupon(int userId, int couponId) {
        int count;

        try {
            count = receiveMapper.countUserAndCoupon(userId, couponId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板总数异常！");
        }
        return count;
    }

    @Override
    public List<RsReceive> getMyReceiveList(PagingTool pageTool) {
        List<RsReceive> receiveList;

        try {
            receiveList = receiveMapper.getMyReceiveList(pageTool);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取我的优惠券列表异常");
        }
        return receiveList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateReceiveStatus(List<Coupon> couponList) {
        int count;

        try {
            count = receiveMapper.batchUpdateReceiveStatus(couponList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券总数异常！");
        }
        return count;
    }

}
