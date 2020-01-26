package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface CouponMapper {
    int deleteByPrimaryKey(Integer couponId);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer couponId);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

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
    List<Coupon> selectCouponList(PagingTool pagingTool);

    /**
     * 批量删除优惠券列表
     *
     * @param ids 优惠券id数组
     * @return int
     */
    int batchDelete(@Param("ids") String[] ids);

    /**
     * 查询店铺下的优惠券
     *
     * @param merchantId 店铺id
     * @return List
     */
    List<Coupon> getCouponListByMerchantId(String merchantId);

    /**
     * 批量更新失效的优惠券列表
     *
     * @param couponList 失效的优惠券列表
     * @return int
     */
    int batchUpdateCouponStatus(@Param("list") List<Coupon> couponList);
}