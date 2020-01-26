package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.CouponMapper;
import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.service.CouponService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 优惠券业务实现类
 *
 * @author Administrator
 */
@Service("CouponService")
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponMapper couponMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCoupon(Coupon coupon) {
        int count;
        try {
            coupon.setEnableTime(coupon.getEndTime());
            count = couponMapper.insertSelective(coupon);
            if (count < 0) {
                throw new Exception("添加优惠券失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加优惠券异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delCoupon(int couponId) {
        int count;
        try {
            count = couponMapper.deleteByPrimaryKey(couponId);
            if (count < 0) {
                throw new Exception("删除优惠券失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除优惠券异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCoupon(Coupon coupon) {
        int count;
        try {
            count = couponMapper.updateByPrimaryKeySelective(coupon);
            if (count < 0) {
                throw new Exception("修改优惠券失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改优惠券异常！");
        }
        return count;
    }

    @Override
    public Coupon getCouponByCouponId(int couponId) {
        Coupon coupon;
        try {
            coupon = couponMapper.selectByPrimaryKey(couponId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券详情异常！");
        }
        return coupon;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = couponMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券总数异常！");
        }
        return count;
    }

    @Override
    public List<Coupon> getCouponList(PagingTool pagingTool) {
        List<Coupon> couponList;

        try {
            couponList = couponMapper.selectCouponList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取优惠券列表异常");
        }
        return couponList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        int count;
        try {
            count = couponMapper.batchDelete(idArr);
            if (count < 0) {
                throw new Exception("批量删除优惠券失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量删除优惠券异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateCouponStatus(List<Coupon> couponList) {
        int count;

        try {
            count = couponMapper.batchUpdateCouponStatus(couponList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量修改优惠券状态异常！");
        }
        return count;
    }

}
