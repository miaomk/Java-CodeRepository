package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Demand;
import com.techwells.wumei.domain.DemandApply;
import com.techwells.wumei.domain.bo.DemandApplyBO;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.domain.vo.TechnologyApplyVO;
import com.techwells.wumei.service.DemandApplyService;
import com.techwells.wumei.service.DemandService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techwells.wumei.util.ConstantUtil.DEMANDOVER;
import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


/**
 * 申请需求Controller
 *
 * @author miao
 */
@RestController
public class DemandApplyController {

    @Resource
    private DemandApplyService demandApplyService;
    @Resource
    private DemandService demandService;


    /**
     * 大师申请需求
     *
     * @param demandApplyBo 大师申请需求BO
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/addDemandApply", method = RequestMethod.POST)
    public ResultInfo addDemandApply(@RequestBody DemandApplyBO demandApplyBo) {
        ResultInfo rsInfo = new ResultInfo();
        Integer userId = demandApplyBo.getUserId();
        Integer demandId = demandApplyBo.getDemandId();
        if (null == userId) {
            rsInfo.setMessage("大师id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (null == demandId) {
            rsInfo.setMessage("需求id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        Demand demandInfo = demandService.getDemandByDemandId(demandId);
        if (null == demandInfo) {
            rsInfo.setMessage("该需求不存在！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        //需求>=3代表已有人接单，无法申请需求
        if (demandInfo.getActivated() >= DEMANDOVER) {
            rsInfo.setMessage("该需求已被接走，请选则别的需求申请！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        DemandApply isApply = demandApplyService.selectIsApply(userId, demandId);

        if (null != isApply) {
            rsInfo.setMessage("您已经申请过了该需求，请勿重复申请！");
            rsInfo.setCode("10002");
            return rsInfo;
        }

        DemandApply demandApply = new DemandApply();
        demandApply.setDemandId(demandId);
        demandApply.setUserId(userId);
        int count;
        try {
            count = demandApplyService.addDemandApply(demandApply);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("大师申请需求异常！");
            rsInfo.setCode("10002");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("大师申请需求成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("大师申请需求失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 修改需求申请信息
     *
     * @param demandApply 需求申请信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/modifyDemandApply", method = RequestMethod.POST)
    public ResultInfo modifyDemandApply(@RequestBody DemandApply demandApply) {
        ResultInfo rsInfo = new ResultInfo();


        if (null == demandApply.getApplyId()) {
            rsInfo.setMessage("申请id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = demandApplyService.modifyDemandApply(demandApply);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改需求申请异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改需求申请失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改需求申请成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除需求申请
     *
     * @param demandApplyId 需求申请id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/deleteDemandApply", method = RequestMethod.POST)
    public ResultInfo deleteDemandApply(@RequestParam("demandApplyId") Integer demandApplyId) {
        ResultInfo rsInfo = new ResultInfo();

        int count;
        try {
            count = demandApplyService.delDemandApply(demandApplyId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除需求申请异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除需求申请成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除需求申请失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看需求申请详情
     *
     * @param demandApplyId 需求申请id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/getDemandApplyById", method = RequestMethod.GET)
    public ResultInfo getDemandApplyById(@RequestParam("demandApplyId") Integer demandApplyId) {
        ResultInfo rsInfo = new ResultInfo();


        DemandApply demandApply;
        try {
            demandApply = demandApplyService.getDemandApplyByDemandApplyId(demandApplyId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取需求申请信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (demandApply == null) {
            rsInfo.setMessage("需求申请信息不存在！");
            rsInfo.setData(new DemandApply());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(demandApply);
        rsInfo.setMessage("获取需求申请成功！");
        return rsInfo;
    }

    /**
     * 获取需求申请列表
     *
     * @param pageNum  页数
     * @param pageSize 页大小
     * @param demandId 需求id
     * @param userId   大师id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/getDemandApplyList", method = RequestMethod.GET)
    public ResultInfo getDemandApplyList(@RequestParam(value = "pageNum") Integer pageNum,
                                         @RequestParam(value = "pageNum") Integer pageSize,
                                         @RequestParam(value = "demandId", required = false) Integer demandId,
                                         @RequestParam(value = "userId", required = false) Integer userId) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        HashMap<String, Object> params = new HashMap<>(16);
        if (null != demandId) {
            params.put("demandId", demandId);
        }
        if (null != userId) {
            params.put("userId", userId);
        }


        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = demandApplyService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求申请总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("需求申请总数量为0！");
            return resultInfo;
        }

        List<DemandApply> demandApplyList;
        try {
            demandApplyList = demandApplyService.getDemandApplyList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求申请列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (demandApplyList.size() == 0) {
            resultInfo.setMessage("需求申请列表为空！");
            resultInfo.setData(new ArrayList<DemandApply>());
            return resultInfo;
        }
        resultInfo.setData(demandApplyList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取需求申请列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除需求申请
     *
     * @param applyIdArray 申请id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/batchDeleteDemandApply")
    public ResultInfo deleteBatch(@RequestParam("applyIdArray") String applyIdArray) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(applyIdArray)) {
            resultInfo.setMessage("申请id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = demandApplyService.deleteBatch(applyIdArray.split(","));
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

    /**
     * 查询我申请的需求
     *
     * @param userId 大师id
     * @return ResultInfo
     */
    @RequestMapping(value = "/demandApply/getMyApply", method = RequestMethod.GET)
    public ResultInfo getMyApply(@RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam("userId") Integer userId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == userId) {
            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        PagingTool pagingTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(12);
        map.put("userId", userId);
        pagingTool.setParams(map);

        int myApplyCount;

        try {
            myApplyCount = demandApplyService.myApplyCount(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取我的需求申请总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (myApplyCount == 0) {
            resultInfo.setMessage("我的需求申请总数量为0！");
            return resultInfo;
        }

        List<TechnologyApplyVO> myApplyList;
        try {
            myApplyList = demandApplyService.getMyApplyList(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取我的需求申请列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        resultInfo.setData(myApplyList);
        resultInfo.setTotal(myApplyCount);
        resultInfo.setMessage("获取我的需求申请列表成功！");

        return resultInfo;
    }

    /**
     * 查询需求申请者信息列表
     *
     * @param pageNum  页数
     * @param pageSize 页大小
     * @param demandId 需求id
     * @return ResultInfo
     */
    @RequestMapping("/demand/getApplicantsList")
    public ResultInfo getApplicantsList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("demandId") Integer demandId) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == demandId) {
            resultInfo.setMessage("需求id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        PagingTool pagingTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(8);
        map.put("demandId", demandId);
        pagingTool.setParams(map);

        int applicantCount;

        try {
            //查询申请者数量
            applicantCount = demandApplyService.countTotal(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求申请者总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (applicantCount == 0) {
            resultInfo.setMessage("申请者数量为空！");
            return resultInfo;
        }

        List<HotTechnologyVo> applicantsList;
        try {
            applicantsList = demandApplyService.getApplicantsList(pagingTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取需求申请者列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        resultInfo.setData(applicantsList);
        resultInfo.setTotal(applicantCount);
        resultInfo.setMessage("获取需求申请者列表成功！");

        return resultInfo;
    }
}
