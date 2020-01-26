package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.ActivityOrder;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.OrderUserInfoVO;
import com.techwells.wumei.domain.rs.RsActivityOrder;
import com.techwells.wumei.service.ActivityOrderService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动订单Controller
 *
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/activityOrder")
public class ActivityOrderController {

    @Resource
    private ActivityOrderService activityOrderService;
    @Resource
    private UserService userService;

    /**
     * 增加活动订单
     *
     * @param activityOrder 活动订单实体类
     * @return ResultInfo
     */
    @RequestMapping(value = "/addActivityOrder")
    @ResponseBody
    public ResultInfo addActivityOrder(@RequestBody ActivityOrder activityOrder) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == activityOrder.getActivityId()) {
            resultInfo.setMessage("活动id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activityOrder.getUserId()) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(activityOrder.getBuyerInformation())) {
            resultInfo.setMessage("购买人信息不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activityOrder.getPayAmount()) {
            resultInfo.setMessage("支付金额不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activityOrder.getPaymentMethod()) {
            resultInfo.setMessage("支付方式不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(activityOrder.getAccount())) {
            resultInfo.setMessage("支付账号不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activityOrder.getTicketCount()) {
            resultInfo.setMessage("购票数不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        int count;
        try {
            log.info("添加活动订单开始,活动订单信息为:{}", JSON.toJSONString(activityOrder));
            count = activityOrderService.addActivityOrder(activityOrder);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("添加活动订单异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count < 0) {
            resultInfo.setMessage("添加活动订单失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("添加活动订单成功");
        resultInfo.setMessage("添加活动订单成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteActivityOrder")
    public ResultInfo deleteActivityOrder(@RequestParam String orderNo) {
        ResultInfo resultInfo = new ResultInfo();
        int count;
        try {
            log.info("删除订单状态开始,订单编号为:{}", orderNo);
            count = activityOrderService.deleteActivityOrder(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除订单状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("删除订单状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("删除订单状态成功");
        resultInfo.setMessage("删除订单状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    /**
     * 修改订单状态
     *
     * @param orderNo 订单编号
     * @param status  状态
     * @return ResultInfo
     */
    @RequestMapping(value = "/modifyActivityOrder")
    @ResponseBody
    public ResultInfo modifyActivityOrderStatus(@RequestParam(value = "orderNo") String orderNo,
                                                @RequestParam(value = "status") String status) {
        ResultInfo resultInfo = new ResultInfo();
        int count;
        try {
            log.info("修改订单状态开始,订单编号为:{},状态为:{}", orderNo, status);
            count = activityOrderService.modifyActivityOrderStatus(Integer.parseInt(orderNo), Integer.parseInt(status));
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改订单状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改订单状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("修改订单状态成功");
        resultInfo.setMessage("修改订单状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    /**
     * 取消订单状态
     *
     * @param orderNo 订单编号
     * @return ResultInfo
     */
    @RequestMapping(value = "/cancelActivityOrder")
    @ResponseBody
    public ResultInfo cancelActivityOrder(@RequestParam(value = "orderNo") String orderNo) {
        ResultInfo resultInfo = new ResultInfo();
        int count;
        try {
            int activityOrderId = Integer.parseInt(orderNo);
            ActivityOrder activityOrder = activityOrderService.getActivityOrderById(activityOrderId);
            //活动订单状态
            int cancelStatus = 3;
            if ((null == activityOrder) || activityOrder.getDeleted() || (cancelStatus == activityOrder.getOrderStatus())) {
                resultInfo.setMessage("订单不存在！");
                resultInfo.setCode("10001");
                return resultInfo;
            }

            log.info("取消订单状态开始,订单编号为:{}", activityOrderId);
            count = activityOrderService.cancelActivityOrder(activityOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("取消订单状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("取消订单状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("取消订单状态成功");
        resultInfo.setMessage("取消订单状态成功！");
        resultInfo.setData(count);
        return resultInfo;


    }

    /**
     * 查询活动余票数量
     *
     * @param activityId 活动id
     * @return ResultInfo
     */
    @RequestMapping(value = "/getSurplusTicketCount")
    @ResponseBody
    public ResultInfo getSurplusTicketCount(@RequestParam("activityId") String activityId) {
        ResultInfo resultInfo = new ResultInfo();
        int count;
        try {

            count = activityOrderService.getSurplusTicketCount(Integer.parseInt(activityId));
            switch (count) {
                case 0:
                    resultInfo.setMessage("票已经卖完");
                    break;
                case -1:
                    resultInfo.setMessage("活动已经删除");
                    break;
                case -2:
                    resultInfo.setMessage("票数不正常");
                    break;
                default:
                    resultInfo.setMessage("还有余票");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("查询余票异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 获取活动订单列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderList")
    public ResultInfo getOrderList(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        if (StringUtil.isEmpty(pageNum)) {
            resultInfo.setMessage("页码不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(pageSize)) {
            resultInfo.setMessage("页数不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        Map<String, Object> params = new HashMap<>(16);

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = activityOrderService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取订单总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        List<ActivityOrder> activityOrderList;
        try {
            activityOrderList = activityOrderService.getActivityOrderList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取订单列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (activityOrderList.size() == 0) {
            resultInfo.setMessage("获取订单列表为空！");
            resultInfo.setData(new ArrayList<ActivityOrder>());
            return resultInfo;
        }


        resultInfo.setData(activityOrderList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取订单列表成功！");
        return resultInfo;
    }

    /**
     * 获取活动订单详情
     *
     * @param activityOrderId 活动订单id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping("/getActivityOrderInfo")
    public ResultInfo getActivityOrderInfo(@RequestParam("activityOrderId") String activityOrderId) {
        ResultInfo resultInfo = new ResultInfo();

        RsActivityOrder rsActivityOrder;
        try {
            rsActivityOrder = activityOrderService.getActivityOrderInfo(activityOrderId);
        } catch (Exception e) {
            resultInfo.setMessage("获取活动订单详情异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(rsActivityOrder);
        return resultInfo;
    }


    /**
     * 获取用户的活动订单列表
     *
     * @param userId 用户id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getUserActivityOrderList")
    public ResultInfo getUserActivityOrderList(@RequestParam("userId") String userId) {
        ResultInfo resultInfo = new ResultInfo();
        List<ActivityOrder> userOrderList;
        try {

            User user = userService.getUserByUserId(Integer.parseInt(userId));
            if (null == user) {

                resultInfo.setMessage("用户不存在！");
                resultInfo.setCode("10001");
                return resultInfo;
            }

            userOrderList = activityOrderService.getActivityOrderListByUserId(Integer.parseInt(userId));
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("查询用户活动订单列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (userOrderList.size() == 0) {

            resultInfo.setMessage("用户活动订单列表为空！");
        } else {

            resultInfo.setMessage("查询用户活动订单列表成功！");
        }
        resultInfo.setData(userOrderList);
        resultInfo.setTotal(userOrderList.size());
        return resultInfo;
    }

    /**
     * 获取活动报名人员列表
     *
     * @param activityId 活动id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getActivityAllUser")
    public ResultInfo getActivityAllUser(@RequestParam("activityId") String activityId) {
        ResultInfo resultInfo = new ResultInfo();
        List<OrderUserInfoVO> orderUserInfoVOS;
        try {

            orderUserInfoVOS = activityOrderService.getActivityAllUser(Integer.parseInt(activityId));
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("查询活动报名人员列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (orderUserInfoVOS.size() == 0) {

            resultInfo.setMessage("活动报名人员为空！");
        } else {

            resultInfo.setMessage("查询活动报名人员列表成功！");
        }
        resultInfo.setData(orderUserInfoVOS);
        resultInfo.setTotal(orderUserInfoVOS.size());
        return resultInfo;
    }
}
