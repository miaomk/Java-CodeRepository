package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Receive;
import com.techwells.wumei.domain.rs.RsReceive;
import com.techwells.wumei.util.PagingTool;

public interface ReceiveService {

    // 增删改查
    int addReceive(Receive receive);

    int delReceive(int receiveId);

    int modifyReceive(Receive receive);

    Receive getReceiveByReceiveId(int receiveId);

    /**
     * 获取优惠券列表
     *
     * @param pagingTool
     * @return
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页获取我的优惠券列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int myReceiveCount(PagingTool pagingTool);

    List<RsReceive> getReceiveList(PagingTool pagingTool);

    // 批量删除
    int deleteBatch(String[] idArr);

    Integer countUserAndCoupon(int userId, int couponId);
    /**
     * 分页获取我的优惠券列表
     *
     * @param pageTool 分页
     * @return List
     */
    List<RsReceive> getMyReceiveList(PagingTool pageTool);

    /**
     * 批量更新已经过期的优惠券列表
     *
     * @param couponList 过去的优惠券列表
     */
    int batchUpdateReceiveStatus(List<Coupon> couponList);
}
