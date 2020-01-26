package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.TechnologyEvaluate;
import com.techwells.wumei.domain.bo.TechnologyEvaluateBO;
import com.techwells.wumei.service.DemandApplyService;
import com.techwells.wumei.service.DemandService;
import com.techwells.wumei.service.TechnologyEvaluateService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


/**
 * 客户评价大师Controller
 *
 * @author miao
 */
@RestController
@RequestMapping("/technologyEvaluate/")
public class TechnologyEvaluateController {

    @Resource
    private TechnologyEvaluateService technologyEvaluateService;
    @Resource
    private DemandApplyService demandApplyService;
    @Resource
    private DemandService demandService;

    /**
     * 添加客户评价
     *
     * @param technologyEvaluateBo 客户评价信息
     * @return ResultInfo
     */
    @RequestMapping(value = "addTechnologyEvaluate")
    public ResultInfo addTechnologyEvaluate(@RequestBody TechnologyEvaluateBO technologyEvaluateBo) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.technologyEvaluateBoCheck(technologyEvaluateBo, resultInfo).getCode())) {
            return resultInfo;
        }
        //查询需求是否已经评价过
        TechnologyEvaluate isEvaluate = technologyEvaluateService.selectIsEvaluate(technologyEvaluateBo.getUserId(),
                technologyEvaluateBo.getTechnologyId(),
                technologyEvaluateBo.getDemandId());
        if (null != isEvaluate) {
            resultInfo.setMessage("您已经评价过该用户！");
            resultInfo.setCode("10004");
            return resultInfo;
        }
        TechnologyEvaluate technologyEvaluate = new TechnologyEvaluate();
        BeanUtils.copyProperties(technologyEvaluateBo, technologyEvaluate);
        if (null != technologyEvaluateBo.getImageUrl() && technologyEvaluateBo.getImageUrl().length > 0) {
            //处理图片数据
            StringBuilder imageUrlBuilder = new StringBuilder();
            for (String imageUrl : technologyEvaluateBo.getImageUrl()) {
                imageUrlBuilder.append(imageUrl).append(",");
            }
            technologyEvaluate.setImageUrl(imageUrlBuilder.toString().substring(0, imageUrlBuilder.length() - 1));
        }


        int count;
        try {
            count = technologyEvaluateService.addTechnologyEvaluate(technologyEvaluate);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加客户评价异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加客户评价成功！");
            resultInfo.setData(count);
            try {
                //更新申请状态
                demandApplyService.updateActivated(technologyEvaluate.getDemandId(), technologyEvaluate.getTechnologyId());
                Demand demand = new Demand();
                demand.setDemandId(technologyEvaluate.getDemandId());
                //已评价
                demand.setActivated(6);
                demandService.modifyDemand(demand);

            } catch (Exception e) {
                e.printStackTrace();
                resultInfo.setMessage("设置已评论状态异常！");
                resultInfo.setCode("10002");
                return resultInfo;
            }


        } else {
            resultInfo.setMessage("添加客户评价失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改客户评价信息
     *
     * @param technologyEvaluate 客户评价信息
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyTechnologyEvaluate")
    public ResultInfo modifyTechnologyEvaluate(@RequestBody TechnologyEvaluate technologyEvaluate) {
        ResultInfo rsInfo = new ResultInfo();


        if (technologyEvaluate.getEvaluateId() == null) {
            rsInfo.setMessage("评价id不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }


        int count;
        try {
            count = technologyEvaluateService.modifyTechnologyEvaluate(technologyEvaluate);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改客户评价异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改客户评价失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改客户评价成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除客户评价
     *
     * @param technologyEvaluateId 客户评价id
     * @return ResultInfo
     */
    @RequestMapping(value = "deleteTechnologyEvaluate")
    public ResultInfo deleteTechnologyEvaluate(@RequestParam("technologyEvaluateId") Integer technologyEvaluateId) {
        ResultInfo rsInfo = new ResultInfo();

        int count;
        try {
            count = technologyEvaluateService.delTechnologyEvaluate(technologyEvaluateId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除客户评价异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除客户评价成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除客户评价失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看客户评价详情
     *
     * @param technologyEvaluateId 查看客户评价详情
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyEvaluateById")
    public ResultInfo getTechnologyEvaluateById(@RequestParam("technologyEvaluateId") Integer technologyEvaluateId) {
        ResultInfo rsInfo = new ResultInfo();


        TechnologyEvaluate technologyEvaluate;
        try {
            technologyEvaluate = technologyEvaluateService.getTechnologyEvaluateByTechnologyEvaluateId(technologyEvaluateId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取客户评价信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (technologyEvaluate == null) {
            rsInfo.setMessage("客户评价信息不存在！");
            rsInfo.setData(new TechnologyEvaluate());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(technologyEvaluate);
        rsInfo.setMessage("获取客户评价成功！");
        return rsInfo;
    }

    /**
     * 获取客户评价列表
     *
     * @param pageNum      页数
     * @param pageSize     页大小
     * @param userId       客户id
     * @param technologyId 大师id
     * @param demandId     需求id
     * @return ResultInfo
     */
    @RequestMapping(value = "getEvaluateList")
    public ResultInfo getEvaluateList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "userId", required = false) Integer userId,
                                      @RequestParam(value = "technologyId", required = false) Integer technologyId,
                                      @RequestParam(value = "demandId", required = false) Integer demandId) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        if (null != userId) {
            params.put("userId", userId);
        }
        if (null != technologyId) {
            params.put("technologyId", technologyId);
        }
        if (null != demandId) {
            params.put("demandId", demandId);
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyEvaluateService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取客户评价总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("客户评价总数量为0！");
            return resultInfo;
        }

        List<TechnologyEvaluate> technologyEvaluateList;
        try {
            technologyEvaluateList = technologyEvaluateService.getTechnologyEvaluateList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取客户评价列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setData(technologyEvaluateList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取客户评价列表成功！");
        return resultInfo;
    }


    /**
     * 获取大师评价列表
     *
     * @param pageNum      页数
     * @param pageSize     页大小
     * @param userId       客户id
     * @param technologyId 大师id
     * @param demandId     需求id
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyEvaluateList")
    public ResultInfo getTechnologyEvaluateList(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize,
                                                @RequestParam(value = "userId", required = false) Integer userId,
                                                @RequestParam(value = "technologyId", required = false) Integer technologyId,
                                                @RequestParam(value = "demandId", required = false) Integer demandId) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        if (null != userId) {
            params.put("userId", userId);
        }
        if (null != technologyId) {
            params.put("technologyId", technologyId);
        }
        if (null != demandId) {
            params.put("demandId", demandId);
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyEvaluateService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取客户评价总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("客户评价总数量为0！");
            return resultInfo;
        }

        List<TechnologyEvaluate> technologyEvaluateList;
        try {
            technologyEvaluateList = technologyEvaluateService.getTechnologyEvaluateList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取客户评价列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setData(technologyEvaluateList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取客户评价列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除客户评价
     *
     * @param evaluateIds 客户评价id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "batchDeleteTechnologyEvaluate")
    public ResultInfo deleteBatch(@RequestParam("evaluateIds") String evaluateIds) {
        ResultInfo rsInfo = new ResultInfo();


        if (StringUtil.isEmpty(evaluateIds)) {
            rsInfo.setMessage("客户评价id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = technologyEvaluateService.deleteBatch(evaluateIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("批量删除异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (count > 0) {
            rsInfo.setMessage("批量删除成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("批量删除失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }
}
