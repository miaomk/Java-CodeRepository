package com.techwells.wumei.service.impl;

import com.techwells.wumei.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import com.alibaba.fastjson.JSONObject;
import com.techwells.wumei.dao.ActivityMapper;
import com.techwells.wumei.dao.ActivityOrderMapper;
import com.techwells.wumei.dao.TicketMapper;
import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.ActivityOrder;
import com.techwells.wumei.domain.Ticket;
import com.techwells.wumei.domain.rs.OrderUserInfoVO;
import com.techwells.wumei.domain.rs.RsActivityOrder;
import com.techwells.wumei.domain.rs.RsActivityUser;
import com.techwells.wumei.service.ActivityOrderService;
import com.techwells.wumei.util.DateUtil;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author miao
 */
@Service(value = "ActivityOrderService")
public class ActivityOrderServiceImpl implements ActivityOrderService {

    private static final String SPLIT = ",";
    @Resource
    private ActivityOrderMapper activityOrderMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private TicketMapper ticketMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addActivityOrder(ActivityOrder activityOrder) {

        int count;
        try {
            String orderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String ticketNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            //生成活动编号
            do {

                orderId = orderId + (int) ((Math.random() * 9 + 1) * 1000);
            } while (null != activityOrderMapper.selectByPrimaryKey(orderId));
            //生成票号
            do {

                ticketNo = ticketNo + (int) ((Math.random() * 9 + 1) * 100);
            } while (null != activityOrderMapper.selectByTicketNo(ticketNo));

            activityOrder.setActivityOrderId(orderId);
            activityOrder.setTicketNo(Long.parseLong(ticketNo));

            //处理买家信息
            String buyerInformation = StringEscapeUtils.unescapeJava(activityOrder.getBuyerInformation().substring(1,activityOrder.getBuyerInformation().length()-1));

            activityOrder.setBuyerInformation(buyerInformation);

            //插入活动订单表
            count = activityOrderMapper.insertSelective(activityOrder);

            if (count < 0) {
                throw new Exception("增加活动订单失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加活动订单异常！");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteActivityOrder(String orderNo) {
        int count;
        try {
            //批量删除
            if (orderNo.contains(SPLIT)) {

                String[] orderIdArrays = orderNo.split(SPLIT);
                count = activityOrderMapper.batchDeleteOrder(orderIdArrays);

            } else {
                ActivityOrder activityOrder = new ActivityOrder();
                activityOrder.setActivityOrderId(orderNo);
                activityOrder.setDeleted(true);
                count = activityOrderMapper.updateByPrimaryKeySelective(activityOrder);
            }

            if (count < 0) {
                throw new Exception("删除订单失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除订单异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyActivityOrderStatus(Integer orderNo, Integer status) {
        int count;
        try {
            ActivityOrder activityOrder = new ActivityOrder();
            activityOrder.setActivityOrderId(String.valueOf(orderNo));
            activityOrder.setOrderStatus(status);
            count = activityOrderMapper.updateByPrimaryKeySelective(activityOrder);
            if (count < 0) {
                throw new Exception("修改订单状态失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改订单状态异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyActivityOrder(ActivityOrder activityOrder) {
        int count;
        try {

            count = activityOrderMapper.updateByPrimaryKeySelective(activityOrder);
            if (count < 0) {
                throw new Exception("修改订单信息失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改订单信息异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelActivityOrder(Integer orderNo) {
        int count;
        //更改状态
        try {
            ActivityOrder activityOrder = activityOrderMapper.selectByPrimaryKey(String.valueOf(orderNo));

            activityOrder.setOrderStatus(3);

            count = activityOrderMapper.updateByPrimaryKeySelective(activityOrder);
            if (count < 0) {
                throw new Exception("取消订单失败!");
            }

            //获取退票规则
            Activity activity = activityMapper.selectByPrimaryKey(activityOrder.getActivityId());
            //活动不免费而且退票收取手续费
            if (!activity.getActivityFree() && activity.getRefundRule()) {
                //TODO 退款
                //退款金额
                BigDecimal refundCount = new BigDecimal(activityOrder.getPayAmount()).multiply(new BigDecimal(0.09));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("取消订单异常");
        }


        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOrder> getActivityOrderListByUserId(Integer userId) {
        List<ActivityOrder> activityOrderList;

        try {

            activityOrderList = activityOrderMapper.getUserOrderList(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取活动订单列表异常");
        }
        return activityOrderList;
    }

    @Override
    public List<ActivityOrder> getActivityOrderList(PagingTool pagingTool) {
        List<ActivityOrder> activityOrderList;

        try {

            activityOrderList = activityOrderMapper.getOrderList(pagingTool);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取活动订单列表失败");
        }
        return activityOrderList;
    }

    @Override
    public ActivityOrder getActivityOrderById(Integer orderNo) {

        ActivityOrder activityOrder;
        try {

            activityOrder = activityOrderMapper.selectByPrimaryKey(String.valueOf(orderNo));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加活动订单异常！");
        }

        return activityOrder;
    }

    @Override
    public int getSurplusTicketCount(Integer activityId) {
        try {
            Ticket ticket = ticketMapper.selectByActivityId(activityId);

            if (null == ticket) {

                return -1;
            }
            //总活动票
            int ticketAmount = ticket.getAmount();
            //已售出活动票
            int saleTicketCount = activityOrderMapper.getSaleTicketCount(activityId);
            //计算剩余活动票
            if (ticketAmount < saleTicketCount) {

                return -2;
            } else if (ticketAmount == saleTicketCount) {

                return 0;
            } else {

                return ticketAmount - saleTicketCount;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询活动剩余票价出错");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderUserInfoVO> getActivityAllUser(Integer activityId) {
        List<OrderUserInfoVO> userInfo;
        try {
            userInfo = activityOrderMapper.getUserInfo(activityId);

            if (null == userInfo) {
                throw new Exception("查询活动报名人员列表失败");
            }
            for (OrderUserInfoVO orderUserInfoVO : userInfo) {
                if (orderUserInfoVO.getPayAmount() == 0) {
                    orderUserInfoVO.setIsFree(true);
                } else {
                    orderUserInfoVO.setIsFree(false);
                }

                //获取手机号
                String phone = (String) JSONObject.parseObject(orderUserInfoVO.getBuyerInformation()).get("phone");
                if (!StringUtil.isEmpty(phone)) {

                    orderUserInfoVO.setPhone(Integer.parseInt(phone));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("查询活动报名人员列表异常");

        }
        return userInfo;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = activityOrderMapper.getOrderCount(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取活动异常！");
        }
        return count;
    }

    @Override
    public List<RsActivityUser> getActivityList(PagingTool pagingTool) {
        List<RsActivityUser> userActivityList;
        try {
            userActivityList = activityOrderMapper.userActivityList(pagingTool);
            if (null != userActivityList) {
                for (RsActivityUser userActivity : userActivityList) {
                    LocalDateTime activityStartTime = userActivity.getActivityStartTime();
                    LocalDateTime activityEndTime = userActivity.getActivityEndTime();

                    if (activityStartTime.isAfter(LocalDateTime.now())) {
                        userActivity.setActivityStatus(1);
                    } else if (activityEndTime.isBefore(LocalDateTime.now())) {
                        userActivity.setActivityStatus(3);
                    } else {
                        userActivity.setActivityStatus(2);
                    }
                    //日期格式处理
                    String activityWeek = DateUtil.ActivityWeek(userActivity.getActivityStartTime());
                    userActivity.setStartTime(activityWeek);
                }
            } else {
                throw new Exception("查询活动报名人员列表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("查询活动报名人员列表异常");
        }
        return userActivityList;
    }

    @Override
    public RsActivityOrder getActivityOrderInfo(String activityOrderId) {
        ActivityOrder activityOrder;
        RsActivityOrder rsActivityOrder = new RsActivityOrder();
        try {

            activityOrder = activityOrderMapper.selectByPrimaryKey(activityOrderId);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(activityOrder.getBuyerInformation());

            Activity activity = activityMapper.selectByPrimaryKey(activityOrder.getActivityId());

            //赋值

            BeanUtils.copyProperties(activity, rsActivityOrder);

            BeanUtils.copyProperties(activityOrder, rsActivityOrder);

            //查询订单票名称
            Ticket ticket = ticketMapper.selectByActivityId(activity.getActivityId());

            rsActivityOrder.setTicketName(ticket.getTicketName());
            rsActivityOrder.setActivityName(activity.getActivityTheme());
            rsActivityOrder.setName( jsonObject.getString("name"));
            rsActivityOrder.setPhone( jsonObject.getString("telphone"));
            //处理时间 yyyy-MM-dd HH:mm:ss -> MM/dd HH:mm
            String activityTime = activity.getActivityStartTime().format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
            rsActivityOrder.setActivityTime(activityTime);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询活动订单详情异常！");
        }

        return rsActivityOrder;
    }

    @Override
    public int getUserActivityCount(PagingTool pagingTool) {
        int count;

        try {
            count = activityOrderMapper.getUserActivityCount(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取公司名下活动总数异常！");
        }
        return count;

    }

    @Override
    public ActivityOrder checkRepeatPurchase(Integer activityId, Integer userId) {
        ActivityOrder activityOrder;
        try {

            activityOrder = activityOrderMapper.checkRepeatPurchase(activityId, userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加活动订单异常！");
        }

        return activityOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrderList(List<ActivityOrder> updateList) {
        int count;

        try {
            count = activityOrderMapper.batchUpdateOrder(updateList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量更新活动订单异常！");
        }
        return count;
    }
}
