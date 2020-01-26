package com.techwells.wumei.controller;

import com.techwells.wumei.domain.WithdrawalRecord;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.service.WithdrawalService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miao
 */
@RestController
@RequestMapping("/withdrawal/")
public class WithdrawalController {

    @Resource
    private WithdrawalService withdrawalService;

    /**
     * 申请提现
     *
     * @param withdrawalRecord 提现信息
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "addWithdrawal")
    public ResultInfo addWithdrawal(@RequestBody WithdrawalRecord withdrawalRecord) {
        ResultInfo resultInfo = new ResultInfo();
        if (StringUtil.isEmpty(withdrawalRecord.getRealName())) {

            resultInfo.setMessage("申请人姓名不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == withdrawalRecord.getWithdrawalAmount() || 0.0 == withdrawalRecord.getWithdrawalAmount()) {

            resultInfo.setMessage("提现金额错误！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(withdrawalRecord.getWithdrawalAccount())) {

            resultInfo.setMessage("提现账号不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == withdrawalRecord.getUserId()) {

            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            count = withdrawalService.addWithdrawal(withdrawalRecord);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("申请提现异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("申请提现成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("申请提现失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 审核提现申请
     *
     * @param request 请求
     * @return ResultInfo
     */
    @RequestMapping(value = "/auditWithdrawal")
    @ResponseBody
    public ResultInfo auditWithdrawal(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String recordId = request.getParameter("recordId");
        String status = request.getParameter("status");

        if (StringUtil.isEmpty(recordId)) {
            resultInfo.setMessage("提现申请id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(status)) {
            resultInfo.setMessage("审核申请状态不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            count = withdrawalService.auditWithdrawal(recordId, status);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改模板异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改模板失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改模板成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    @RequestMapping(value = "/getWithdrawalList")
    public ResultInfo getWithdrawalList(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        ResultInfo resultInfo = new ResultInfo();
        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);
        pageTool.setParams(map);

        int totalCount;

        try {
            totalCount = withdrawalService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取用户提现总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("用户提现总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }


        List<BillVO> withdrawalRecordList;
        try {
            withdrawalRecordList = withdrawalService.getWithdrawalList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取用户提现列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (withdrawalRecordList.size() == 0) {
            resultInfo.setMessage("用户提现列表为空！");
            resultInfo.setData(new ArrayList<BillVO>());
            return resultInfo;
        }
        resultInfo.setData(withdrawalRecordList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取用户提现列表成功！");
        return resultInfo;
    }
}
