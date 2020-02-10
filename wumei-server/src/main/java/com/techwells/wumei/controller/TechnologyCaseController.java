package com.techwells.wumei.controller;

import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.vo.CaseVO;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.service.TechnologyCaseService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


/**
 * 技术人员工作案例Controller
 *
 * @author miao
 */
@RestController
@RequestMapping(value = "/technologyCase/")
public class TechnologyCaseController {

    @Resource
    private TechnologyCaseService technologyCaseService;

    /**
     * 添加技术人员工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return ResultInfo
     */
    @RequestMapping(value = "addTechnologyCase", method = RequestMethod.POST)
    public ResultInfo addTechnologyCase(@RequestBody TechnologyCase technologyCase) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.technologyCaseCheck(technologyCase, resultInfo).getCode())) {
            return resultInfo;
        }
        int count;
        try {
            count = technologyCaseService.addTechnologyCase(technologyCase);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加工作案例异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加工作案例成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加工作案例失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改工作案例信息
     *
     * @param technologyCase 技术人员工作案例信息
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyTechnologyCase", method = RequestMethod.POST)
    public ResultInfo modifyTechnologyCase(@RequestBody TechnologyCase technologyCase) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == technologyCase.getCaseId()) {

            resultInfo.setMessage("工作案例id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = technologyCaseService.modifyTechnologyCase(technologyCase);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改工作案例异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改工作案例失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改工作案例成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除工作案例
     *
     * @param technologyCaseId 工作案例id
     * @return ResultInfo
     */
    @RequestMapping(value = "deleteTechnologyCase")
    public ResultInfo deleteTechnologyCase(@RequestParam("technologyCaseId") Integer technologyCaseId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == technologyCaseId) {
            resultInfo.setMessage("工作案例id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            count = technologyCaseService.delTechnologyCase(technologyCaseId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除工作案例异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除工作案例成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除工作案例失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 查看工作案例详情
     *
     * @param technologyCaseId 工作案例id
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyCaseById")
    public ResultInfo getTechnologyCaseById(@RequestParam("technologyCaseId") Integer technologyCaseId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == technologyCaseId) {
            resultInfo.setMessage("工作案例id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        TechnologyCase technologyCase;
        try {
            technologyCase = technologyCaseService.getTechnologyCaseByTechnologyCaseId(technologyCaseId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("获取工作案例信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (technologyCase == null) {
            resultInfo.setMessage("工作案例信息不存在！");
            resultInfo.setData(new TechnologyCase());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        resultInfo.setData(technologyCase);
        resultInfo.setMessage("获取工作案例成功！");
        return resultInfo;
    }

    /**
     * 查看工作案例详情
     *
     * @param caseId 工作案例id
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyCaseInfo")
    public ResultInfo getTechnologyCaseInfo(@RequestParam("caseId") Integer caseId) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == caseId) {
            resultInfo.setMessage("案例id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        CaseVO caseVo;
        try {
            caseVo = technologyCaseService.getCaseInfo(caseId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("获取工作案例信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (caseVo == null) {
            resultInfo.setMessage("工作案例不存在！");
            resultInfo.setData(new TechnologyCase());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        resultInfo.setData(caseVo);
        resultInfo.setMessage("获取工作案例成功！");
        return resultInfo;
    }

    /**
     * 获取工作案例列表
     *
     * @param pageNum      页数
     * @param pageSize     页大小
     * @param caseName     案例名
     * @param technologyId 技术人员id
     * @param queryType    查询类型  1 小程序
     * @param activated    案例状态 0 启用 1 禁用
     * @return ResultInfo
     */
    @RequestMapping(value = "getCaseList", method = RequestMethod.GET)
    public ResultInfo getCaseList(@RequestParam(value = "pageNum") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  @RequestParam(value = "caseName", required = false) String caseName,
                                  @RequestParam(value = "technologyId", required = false) Integer technologyId,
                                  @RequestParam(value = "queryType") Integer queryType,
                                  @RequestParam(value = "activated", required = false) Integer activated) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(12);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        if (1 == queryType && null == technologyId) {

            resultInfo.setMessage("技术人员id不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);

        if (!StringUtil.isEmpty(caseName)) {
            params.put("caseName", caseName);
        }
        if (null != technologyId) {
            params.put("technologyId", technologyId);
        }
        if (null != activated) {
            params.put("activated", activated);
        }

        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyCaseService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取工作案例总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("工作案例总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }

        List<TechnologyCase> technologyCaseList;
        try {
            technologyCaseList = technologyCaseService.getCaseList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取工作案例列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setData(technologyCaseList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取工作案例列表成功！");
        return resultInfo;
    }


    /**
     * 获取大师工作案例列表
     *
     * @param pageNum      页数
     * @param pageSize     页大小
     * @param technologyId 技术人员id
     * @param pvCount      按热度倒序
     * @param createTime   按时间倒序
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyCaseList", method = RequestMethod.GET)
    public ResultInfo getTechnologyCaseList(@RequestParam(value = "pageNum") Integer pageNum,
                                            @RequestParam(value = "pageSize") Integer pageSize,
                                            @RequestParam(value = "technologyId") Integer technologyId,
                                            @RequestParam(value = "pvCount", required = false) Integer pvCount,
                                            @RequestParam(value = "createTime", required = false) Integer createTime) {

        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(12);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        if (null == technologyId) {

            resultInfo.setMessage("技术人员id不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        } else {
            params.put("technologyId", technologyId);
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);

        if (null != pvCount) {
            params.put("pvCount", pvCount);
        }
        if (null != createTime) {
            params.put("createTime", createTime);
        }

        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyCaseService.technologyCaseTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取大师工作案例总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("大师工作案例总数量为0！");
            return resultInfo;
        }

        List<TechnologyCaseVO> technologyCaseList;
        try {
            technologyCaseList = technologyCaseService.getTechnologyCaseList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取工作案例列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setData(technologyCaseList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取大师工作案例列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除工作案例
     *
     * @param id 工作案例id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "batchDeleteTechnologyCase", method = RequestMethod.POST)
    public ResultInfo deleteBatch(@RequestParam("id") String id) {
        ResultInfo rsInfo = new ResultInfo();

        if (StringUtil.isEmpty(id)) {
            rsInfo.setMessage("工作案例id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = technologyCaseService.deleteBatch(id.split(","));
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
     * 批量增加工作案例
     *
     * @param technologyCases 工作案例id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "batchAddTechnologyCase", method = RequestMethod.POST)
    public ResultInfo insertBatch(@RequestBody TechnologyCase[] technologyCases) {
        ResultInfo rsInfo = new ResultInfo();
        if (technologyCases.length == 0) {
            rsInfo.setMessage("不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        List<TechnologyCase> technologyCasesList = new ArrayList<>(Arrays.asList(technologyCases));
        for (TechnologyCase technologyCase : technologyCasesList) {
            if (!SUCCESSCODE.equals(ParamCheckUtil.technologyCaseCheck(technologyCase, rsInfo).getCode())) {
                return rsInfo;
            }
        }
        int count;
        try {
            count = technologyCaseService.batchAddCase(technologyCasesList);
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
