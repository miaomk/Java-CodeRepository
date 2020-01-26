package com.techwells.wumei.controller;

import com.techwells.wumei.domain.TechnologyOrder;
import com.techwells.wumei.service.TechnologyOrderService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


/**
 * 大师订单Controller
 *
 * @author miao
 */
@RestController
public class TechnologyOrderController {

    @Resource
    private TechnologyOrderService technologyOrderService;

    /**
     * 用户下单大师
     *
     * @param technologyOrder 大师订单信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/addTechnologyOrder")
    public ResultInfo addTechnologyOrder(@RequestBody TechnologyOrder technologyOrder) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.technologyOrderCheck(technologyOrder, resultInfo).getCode())) {
            return resultInfo;
        }

        int count;
        try {
            count = technologyOrderService.addTechnologyOrder(technologyOrder);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加大师订单异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加大师订单成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加大师订单失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改大师订单信息
     *
     * @param technologyOrder 大师订单信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/modifyTechnologyOrder")
    public ResultInfo modifyTechnologyOrder(@RequestBody TechnologyOrder technologyOrder) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == technologyOrder.getOrderId()) {
            resultInfo.setMessage("大师订单id不能为空!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {
            count = technologyOrderService.modifyTechnologyOrder(technologyOrder);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改大师订单异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改大师订单失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改大师订单成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除大师订单
     *
     * @param technologyOrderId 大师订单id
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/deleteTechnologyOrder")
    public ResultInfo deleteTechnologyOrder(@RequestParam("technologyOrderId") Integer technologyOrderId) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == technologyOrderId) {
            resultInfo.setMessage("大师订单id不能为空!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {
            count = technologyOrderService.delTechnologyOrder(technologyOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除大师订单异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除大师订单成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除大师订单失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 查看大师订单详情
     *
     * @param technologyOrderId 大师订单id
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/getTechnologyOrderById")
    public ResultInfo getTechnologyOrderById(@RequestParam("technologyOrderId") Integer technologyOrderId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == technologyOrderId) {
            resultInfo.setMessage("大师订单id不能为空!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        TechnologyOrder technologyOrder;
        try {
            technologyOrder = technologyOrderService.getTechnologyOrderInfo(technologyOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("获取大师订单信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (technologyOrder == null) {
            resultInfo.setMessage("大师订单信息不存在！");
            resultInfo.setData(new TechnologyOrder());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        resultInfo.setData(technologyOrder);
        resultInfo.setMessage("获取大师订单成功！");
        return resultInfo;
    }

    /**
     * 获取大师订单列表
     *
     * @param pageNum      页数
     * @param pageSize     页大小
     * @param userId       用户id
     * @param technologyId 技术人员id
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/getTechnologyOrderList")
    public ResultInfo getTechnologyOrderList(@RequestParam(value = "pageNum") Integer pageNum,
                                             @RequestParam(value = "pageSize") Integer pageSize,
                                             @RequestParam(value = "userId", required = false) Integer userId,
                                             @RequestParam(value = "technologyId", required = false) Integer technologyId) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        HashMap<String, Object> params = new HashMap<>(16);
        if (null != userId) {
            params.put("userId", userId);
        }
        if (null != technologyId) {
            params.put("technologyId", technologyId);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyOrderService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取大师订单总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("大师订单总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }

        List<TechnologyOrder> technologyOrderList;
        try {
            technologyOrderList = technologyOrderService.getTechnologyOrderList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取大师订单列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (technologyOrderList.size() == 0) {
            resultInfo.setMessage("大师订单列表为空！");
            resultInfo.setData(new ArrayList<TechnologyOrder>());
            return resultInfo;
        }
        resultInfo.setData(technologyOrderList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取大师订单列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除大师订单
     *
     * @param technologyIds 大师订单id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/technologyOrder/batchDeleteTechnologyOrder")
    public ResultInfo deleteBatch(@RequestParam("technologyIds") String technologyIds) {
        ResultInfo resultInfo = new ResultInfo();


        if (StringUtil.isEmpty(technologyIds)) {
            resultInfo.setMessage("大师订单ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = technologyOrderService.deleteBatch(technologyIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("批量删除异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("批量删除成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("批量删除失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }
}
