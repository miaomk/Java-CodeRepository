package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.bo.DemandBO;
import com.techwells.wumei.domain.vo.DemandVO;
import com.techwells.wumei.domain.vo.MyDemandVO;
import com.techwells.wumei.service.DemandService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


/**
 * 需求Controller
 *
 * @author miao
 */
@RestController
public class DemandController {

    @Resource
    private DemandService demandService;

    /**
     * 用户发布需求
     *
     * @param demandBo 发布需求实体类
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/addDemand", method = RequestMethod.POST)
    public ResultInfo addDemand(@RequestBody DemandBO demandBo) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.demandBoCheck(demandBo, resultInfo).getCode())) {
            return resultInfo;
        }

        Demand isRepeat = demandService.getIsRepeat(demandBo);
        if (null != isRepeat) {
            resultInfo.setMessage("请勿发布重复需求！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        Demand demand = new Demand();

        BeanUtils.copyProperties(demandBo, demand);
        //默认发布
        demand.setActivated(1);

        int count;
        try {
            count = demandService.addDemand(demand);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("发布用户需求异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("发布用户需求成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("发布用户需求失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改需求信息
     *
     * @param demand 需求信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/modifyDemand", method = RequestMethod.POST)
    public ResultInfo modifyDemand(@RequestBody Demand demand) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == demand.getDemandId()) {
            resultInfo.setMessage("需求id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null != demand.getStartTime() && null != demand.getEndTime()) {
            resultInfo.setMessage("需求id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }



        int count;
        try {
            count = demandService.modifyDemand(demand);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改需求异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改需求失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改需求成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除需求
     *
     * @param demandId 需求id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/deleteDemand", method = RequestMethod.GET)
    public ResultInfo deleteDemand(@RequestParam("demandId") Integer demandId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == demandId) {
            resultInfo.setMessage("需求id不能为空!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        int count;
        try {
            count = demandService.delDemand(demandId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除需求异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除需求成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除需求失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 查看需求详情
     *
     * @param demandId 需求id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/getDemandInfo", method = RequestMethod.GET)
    public ResultInfo getDemandInfo(@RequestParam("demandId") Integer demandId) {
        ResultInfo rsInfo = new ResultInfo();

        DemandVO demand;
        try {
            demand = demandService.getDemandInfo(demandId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取需求信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (demand == null) {
            rsInfo.setMessage("需求信息不存在！");
            rsInfo.setData(new Demand());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(demand);
        rsInfo.setMessage("获取需求成功！");
        return rsInfo;
    }

    /**
     * 获取需求列表
     *
     * @param pageNum        页数
     * @param pageSize       页大小
     * @param demandTitle    需求标题
     * @param city           地点
     * @param technologyType 技术人员类型
     * @param activated      0 待审核 1 发布中 2 已接单 3 已完成 4 已评价
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/getDemandList", method = RequestMethod.GET)
    public ResultInfo getDemandList(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam(value = "demandTitle", required = false) String demandTitle,
                                    @RequestParam(value = "city", required = false) String city,
                                    @RequestParam(value = "technologyType", required = false) Integer technologyType,
                                    @RequestParam(value = "activated", defaultValue = "1") Integer activated) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        HashMap<String, Object> params = new HashMap<>(16);

        if (!StringUtil.isEmpty(demandTitle)) {
            params.put("demandTitle", demandTitle);
        }

        if (!StringUtil.isEmpty(city)) {
            params.put("city", city);
        }
        if (null != activated) {
            params.put("activated", activated);
        }
        if (null != technologyType) {
            params.put("technologyType", technologyType);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = demandService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("需求总数量为0！");
            return resultInfo;
        }

        List<DemandVO> demandList;
        try {
            demandList = demandService.getDemandList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (demandList.size() == 0) {
            resultInfo.setMessage("需求列表为空！");
            resultInfo.setData(new ArrayList<Demand>());
            return resultInfo;
        }
        resultInfo.setData(demandList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取需求列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除需求
     *
     * @param demandIds 需求id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/demand/batchDeleteDemand", method = RequestMethod.POST)
    public ResultInfo deleteBatch(@RequestParam("demandIds") String demandIds) {
        ResultInfo rsInfo = new ResultInfo();

        if (StringUtil.isEmpty(demandIds)) {
            rsInfo.setMessage("需求id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {

            count = demandService.deleteBatch(demandIds.split(","));
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

    /**
     * 我的发布接口
     *
     * @param userId 用户id
     * @return ResultInfo
     */
    @RequestMapping("/demand/getMyDemand")
    public ResultInfo getMyDemand(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("userId") Integer userId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == userId) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        PagingTool pagingTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(8);
        map.put("userId", userId);
        pagingTool.setParams(map);

        int myDemandCount;

        try {
            myDemandCount = demandService.countTotal(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取我的需求申请总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (myDemandCount == 0) {
            resultInfo.setMessage("我的需求申请总数量为0！");
            return resultInfo;
        }

        List<MyDemandVO> myDemandList;
        try {
            myDemandList = demandService.getMyDemandList(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取我的发布列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        resultInfo.setData(myDemandList);
        resultInfo.setTotal(myDemandCount);
        resultInfo.setMessage("获取我的发布列表成功！");

        return resultInfo;
    }
}
