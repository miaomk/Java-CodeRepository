package com.techwells.wumei.dao;

import com.techwells.wumei.domain.ActivityOrder;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.domain.rs.OrderUserInfoVO;
import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.rs.RsActivityUser;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public interface ActivityOrderMapper {
    int deleteByPrimaryKey(String activityOrderId);

    int insert(ActivityOrder record);

    int insertSelective(ActivityOrder record);

    ActivityOrder selectByPrimaryKey(String activityOrderId);

    int updateByPrimaryKeySelective(ActivityOrder record);

    int updateByPrimaryKey(ActivityOrder record);

    /**
     * 通过活动id查询售出的票
     *
     * @param activityId 活动id
     * @return int
     */
    int getSaleTicketCount(@Param("activityId") Integer activityId);

    /**
     * 通过活动id查询所有售出的免费票数量
     *
     * @param activityId 活动id
     * @return int
     */
    int getFreeCount(@Param("activityId") Integer activityId);

    /**
     * 分页查询订单列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<ActivityOrder> getOrderList(PagingTool pagingTool);


    /**
     * 查询是否已经存在票号
     *
     * @param ticketNo 票号
     * @return ActivityOrder
     */
    ActivityOrder selectByTicketNo(String ticketNo);

    /**
     * 查询用户活动订单列表
     *
     * @param userId 用户id
     * @return List
     */
    List<ActivityOrder> getUserOrderList(@Param("userId") int userId);

    /**
     * 批量删除活动订单
     *
     * @param orderIdArrays 活动订单id数组
     * @return int
     */
    int batchDeleteOrder(@Param("ids") String[] orderIdArrays);

    /**
     * 查询活动列表报名人数
     *
     * @param activityManageList 活动列表
     * @return List
     */
    List<RsActivityManage> getSignUpCount(@Param("activityManageList") List<RsActivityManage> activityManageList);

    /**
     * 查询报名活动用户列表
     *
     * @param activityId 活动id
     * @return List
     */
    List<ActivityOrder> getAllUserInfo(@Param("activityId") Integer activityId);

    /**
     * 获取报名活动用户列表详细信息
     *
     * @param activityId 活动id
     * @return List
     */
    List<OrderUserInfoVO> getUserInfo(@Param("activityId") Integer activityId);

    /**
     * 查询公司卖门票的收入
     *
     * @param companyId 公司id
     * @return BigDecimal
     */
    BigDecimal getTicketIncome(@Param("companyId") Integer companyId);

    /**
     * 查询公司卖门票的收入列表
     *
     * @param companyId 公司id
     * @return List
     */
    List<BillVO> getTicketIncomeList(@Param("companyId") Integer companyId);

    /**
     * 分页询活动订单列表总数
     *
     * @param pageTool 分页
     * @return int
     */
    int getOrderCount(PagingTool pageTool);

    /**
     * 查询用户活动列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsActivityUser> userActivityList(PagingTool pagingTool);

    /**
     * 查询用户活动列表
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
    ActivityOrder checkRepeatPurchase(@Param("activityId") Integer activityId,
                                      @Param("userId") Integer userId);

    /**
     * 批量更新活动订单
     *
     * @param updateList 活动订单
     * @return int
     */
    int batchUpdateOrder(@Param("list") List<ActivityOrder> updateList);
}