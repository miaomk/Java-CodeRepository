package com.techwells.wumei.service;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface CouponService {

    // 增删改查
    int addCoupon(Coupon coupon);

    int delCoupon(int couponId);

    int modifyCoupon(Coupon coupon);

    Coupon getCouponByCouponId(int couponId);

    /**
     * 分页获取优惠券列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页获取优惠券列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<Coupon> getCouponList(PagingTool pagingTool);

    /**
     * 批量删除
     *
     * @param idArr 优惠券id数组
     * @return int
     */
    int deleteBatch(String[] idArr);

    /**
     * 批量更新失效的优惠券列表
     *
     * @param couponList 失效的优惠券列表
     * @return int
     */
    int batchUpdateCouponStatus(List<Coupon> couponList);
}
