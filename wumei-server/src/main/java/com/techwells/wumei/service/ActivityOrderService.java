package com.techwells.wumei.service;

import com.techwells.wumei.domain.ActivityOrder;
import com.techwells.wumei.domain.rs.OrderUserInfoVO;
import com.techwells.wumei.domain.rs.RsActivityOrder;
import com.techwells.wumei.domain.rs.RsActivityUser;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface ActivityOrderService {
    /**
     * 添加活动订单
     *
     * @param activityOrder 活动订单实体类
     * @return int
     */
    int addActivityOrder(ActivityOrder activityOrder);

    /**
     * 通过订单编号删除订单
     *
     * @param orderNo 订单编号
     * @return
     */
    int deleteActivityOrder(String orderNo);

    /**
     * 通过订单编号修改订单状态
     *
     * @param orderNo 订单状态
     * @param status  状态
     * @return int
     */
    int modifyActivityOrderStatus(Integer orderNo, Integer status);

    /**
     * 修改活动订单信息
     *
     * @param activityOrder 活动订单信息
     * @return int
     */
    int modifyActivityOrder(ActivityOrder activityOrder);

    /**
     * 取消订单状态
     *
     * @param orderNo 订单编号
     * @return int
     */
    int cancelActivityOrder(Integer orderNo);

    /**
     * 通过userId查询名下活动订单列表
     *
     * @param userId 用户id
     * @return List
     */
    List<ActivityOrder> getActivityOrderListByUserId(Integer userId);

    /**
     * 分页查询活动订单列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<ActivityOrder> getActivityOrderList(PagingTool pagingTool);

    /**
     * 通过订单编号查询订单信息
     *
     * @param orderNo 订单号
     * @return ActivityOrder
     */
    ActivityOrder getActivityOrderById(Integer orderNo);

    /**
     * 通过活动id查询活动剩余票数
     *
     * @param activityId 活动id
     * @return int
     */
    int getSurplusTicketCount(Integer activityId);

    /**
     * 查询报名活动人员列表
     *
     * @param activityId 活动id
     * @return List
     */
    List<OrderUserInfoVO> getActivityAllUser(Integer activityId);

    int countTotal(PagingTool pageTool);

    /**
     * 查询用户活动列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsActivityUser> getActivityList(PagingTool pagingTool);

    /**
     * 查询活动订单详细信息
     *
     * @param activityOrderId 活动订单id
     * @return RsActivityOrder
     */
    RsActivityOrder getActivityOrderInfo(String activityOrderId);

    /**
     * 查询用户购买的活动总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int getUserActivityCount(PagingTool pagingTool);

    /**
     * 查询用户是否重复购买活动门票
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return ActivityOrder
     */
    ActivityOrder checkRepeatPurchase(Integer activityId, Integer userId);

    /**
     * 批量更新活动订单
     *
     * @param updateList 活动订单
     * @return int
     */
    int updateOrderList(List<ActivityOrder> updateList);
}
