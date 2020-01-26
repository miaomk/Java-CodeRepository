package com.techwells.wumei.job;

import com.techwells.wumei.domain.*;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 执行定时任务
 *
 * @author Administrator
 */
@Component
public class WuMeiJob {

    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityOrderService activityOrderService;
    @Resource
    private DealService dealService;
    @Resource
    private CouponService couponService;
    @Resource
    private ReceiveService receiveService;
    @Resource
    private RecommendService recommendService;
    @Resource
    private BrandService brandService;
    @Resource
    private DemandService demandService;
    @Resource
    private TechnologyOrderService technologyOrderService;

    private PagingTool pageTool = new PagingTool(1, 999);
    private Map<String, Object> map = new HashMap<>(16);


    /**
     * 每分钟执行一次
     * 查询活动是否已经结束
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateActivityStatus() {
        List<Activity> activityList = activityService.getActivityList(pageTool);

        if (activityList.size() > 0) {
            activityList.forEach(data -> {
                LocalDateTime activityStartTime = data.getActivityStartTime();
                LocalDateTime activityEndTime = data.getActivityEndTime();

                //如果活动结束时间已过，设置活动已结束
                if (activityEndTime.isBefore(LocalDateTime.now())) {

                    data.setActivated(2);
                } else if (activityStartTime.isBefore(LocalDateTime.now()) && activityEndTime.isAfter(LocalDateTime.now())) {
                    //活动已经开始未结束，设置活动正在进行
                    data.setActivated(1);
                }
            });
            activityService.batchUpdateActivity(activityList);
        }
    }


    /**
     * 每分钟执行一次
     * 更新推荐商品状态
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateRecommendStatus() {

        recommendService.updateStartRecommend();
        recommendService.updateEndRecommend();
    }

    /**
     * 每分钟执行一次
     * 更新轮播图状态
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateBrandStatus() {

        brandService.updateStartBrand();
        brandService.updateEndBrand();
    }

    /**
     * 每分钟执行一次
     * 查询活动开始时间
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateActivityActivated() {
        List<Activity> activityList = activityService.getActivityList(pageTool);
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                LocalDateTime activityStartTime = activity.getActivityStartTime();
                //计算时差
                Duration duration = Duration.between(LocalDateTime.now(), activityStartTime);
                //如果时差小于360分钟即6个小时之内。则截止报名
                if (duration.toMinutes() <= 360) {
                    activity.setActivated(3);
                }
            }
            activityService.batchUpdateActivity(activityList);
        }
    }


    /**
     * 每分钟执行一次
     * 查询活动开始时间
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateCouponStatus() {
        map.put("endTime", LocalDateTime.now().toString());
        map.put("activated", "0");
        pageTool.setParams(map);

        //查询出已经是失效的优惠券列表
        List<Coupon> couponList = couponService.getCouponList(pageTool);
        if (CollectionUtils.isNotEmpty(couponList)) {
            //更新优惠券信息
            couponService.batchUpdateCouponStatus(couponList);
            //更新用户领取的优惠券状态
            receiveService.batchUpdateReceiveStatus(couponList);
        }
    }


    /**
     * 5秒执行一次
     * 更新活动表，交易表订单状态
     */
    @Scheduled(cron = "*/5 * * * *  ?")
    public void updateActivityOrderStatus() {
        map.put("orderStatus", 2);
        map.put("payStatus", 3);
        pageTool.setParams(map);

        List<ActivityOrder> activityOrderList = activityOrderService.getActivityOrderList(pageTool);
        if (activityOrderList.size() > 0) {
            //过滤出超过2小时不下单的订单
            List<ActivityOrder> updateList =
                    activityOrderList.stream().filter(data ->
                            Duration.between(data.getCreateDate(), LocalDateTime.now()).toMillis() >= 7200000)
                            .collect(Collectors.toList());
            if (updateList.size() > 0) {
                for (ActivityOrder activityOrder : updateList) {
                    activityOrder.setPayStatus(2);
                    activityOrder.setOrderStatus(3);

                    activityOrder.setDeleted(true);
                }

                activityOrderService.updateOrderList(updateList);
            }

        }

        map.put("dealType", 5);
        map.put("dealStatus", 1);
        pageTool.setParams(map);

        List<Deal> dealList = dealService.getDealList(pageTool);
        if (dealList.size() > 0) {
            //过滤出超过2小时不下单的订单
            List<Deal> updateList =
                    dealList.stream().filter(data ->
                            Duration.between(data.getCreatedDate(), LocalDateTime.now()).toMillis() >= 7200000)
                            .collect(Collectors.toList());

            if (updateList.size() > 0) {
                for (Deal deal : updateList) {
                    deal.setDealStatus(2);

                    deal.setDeleted(true);
                }

                dealService.updateDelList(updateList);
            }

        }
    }

    /**
     * 5秒执行一次
     * 更新活动表，交易表订单状态
     */
    @Scheduled(cron = "*/5 * * * *  ?")
    public void updateTechnologyOrderStatus() {
        map.put("orderStatus", 2);
        map.put("payStatus", 3);
        pageTool.setParams(map);

        List<TechnologyOrder> technologyOrderList = technologyOrderService.getTechnologyOrderList(pageTool);
        if (technologyOrderList.size() > 0) {
            //过滤出超过2小时不下单的订单
/*            List<TechnologyOrder> updateList =
                    technologyOrderList.stream().filter(data ->
                            Duration.between(data.getCreateDate(), LocalDateTime.now()).toMillis() >= 7200000)
                            .collect(Collectors.toList());
            if (updateList.size() > 0) {
                for (TechnologyOrder technologyOrder : updateList) {
                    technologyOrder.setPayStatus(2);
                    technologyOrder.setOrderStatus(3);

                    technologyOrder.setDeleted(1);
                }

                technologyOrderService.updateOrderList(updateList);
            }*/

        }

        map.put("dealType", 6);
        map.put("dealStatus", 1);
        pageTool.setParams(map);

        List<Deal> dealList = dealService.getDealList(pageTool);
        if (dealList.size() > 0) {
            //过滤出超过2小时不下单的订单
            List<Deal> updateList =
                    dealList.stream().filter(data ->
                            Duration.between(data.getCreatedDate(), LocalDateTime.now()).toMillis() >= 7200000)
                            .collect(Collectors.toList());

            if (updateList.size() > 0) {
                for (Deal deal : updateList) {
                    deal.setDealStatus(2);

                    deal.setDeleted(true);
                }

                dealService.updateDelList(updateList);
            }

        }
    }


    /**
     * 每天凌晨00:00:01执行一次
     * 更新Demand表需求状态
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void updateDemandStatus() {
        demandService.updateActivated();
    }

}
