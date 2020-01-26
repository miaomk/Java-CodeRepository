package com.techwells.wumei.controller;

import com.techwells.wumei.domain.TechnologyType;
import com.techwells.wumei.service.TechnologyTypeService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 技术人员类型Controller
 *
 * @author miao
 */
@RestController
@RequestMapping("/technologyType/")
public class TechnologyTypeController {

    @Resource
    private TechnologyTypeService technologyTypeService;

    /**
     * 添加技术人员类型
     *
     * @param technologyType 技术人员类型实体类
     * @return ResultInfo
     */
    @RequestMapping(value = "addTechnologyType")
    public ResultInfo addTechnologyType(@RequestBody TechnologyType technologyType) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(technologyType.getTypeName())) {
            resultInfo.setCode("10001");
            resultInfo.setMessage("技术人员类型名称不能为空！");
            return resultInfo;
        }

        if (null == technologyType.getMinSalary() || null == technologyType.getMaxSalary()) {
            resultInfo.setCode("10002");
            resultInfo.setMessage("技术人员类型薪酬不能为空！");
            return resultInfo;
        }
        if (technologyType.getMinSalary().compareTo(technologyType.getMaxSalary()) >= 0) {
            resultInfo.setCode("10003");
            resultInfo.setMessage("最大薪资不能小于最小薪资！");
            return resultInfo;
        }

        int count;
        try {
            count = technologyTypeService.addTechnologyType(technologyType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加技术人员类型异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加技术人员类型成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加技术人员类型失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改技术人员类型信息
     *
     * @param technologyType 技术人员信息
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyTechnologyType", method = RequestMethod.POST)
    public ResultInfo modifyTechnologyType(@RequestBody TechnologyType technologyType) {
        ResultInfo rsInfo = new ResultInfo();


        if (null == technologyType.getTypeId()) {
            rsInfo.setMessage("技术人员类型id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = technologyTypeService.modifyTechnologyType(technologyType);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改技术人员类型异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改技术人员类型失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改技术人员类型成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除技术人员类型
     *
     * @param technologyTypeId 技术人员类型id
     * @return ResultInfo
     */
    @RequestMapping(value = "deleteTechnologyType/{technologyTypeId}")
    public ResultInfo deleteTechnologyType(@PathVariable("technologyTypeId") Integer technologyTypeId) {
        ResultInfo rsInfo = new ResultInfo();

        int count;
        try {
            count = technologyTypeService.delTechnologyType(technologyTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除技术人员类型异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除技术人员类型成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除技术人员类型失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看技术人员类型详情
     *
     * @param technologyTypeId 技术人员类型id
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyTypeById/{technologyTypeId}")
    public ResultInfo getTechnologyTypeById(@PathVariable("technologyTypeId") Integer technologyTypeId) {
        ResultInfo rsInfo = new ResultInfo();


        TechnologyType technologyType;
        try {
            technologyType = technologyTypeService.getTechnologyTypeByTechnologyTypeId(technologyTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取技术人员类型信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (technologyType == null) {
            rsInfo.setMessage("技术人员类型信息不存在！");
            rsInfo.setData(new TechnologyType());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(technologyType);
        rsInfo.setMessage("获取技术人员类型成功！");
        return rsInfo;
    }

    /**
     * 获取技术人员类型列表
     *
     * @param pageNum            页数
     * @param pageSize           页大小
     * @param technologyTypeName 技术人员类型名称
     * @param activated          状态 0 启用 1 禁用
     * @return ResultInfo
     */
    @RequestMapping(value = "getTechnologyTypeList")
    public ResultInfo getTechnologyTypeList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
                                            @RequestParam(value = "technologyTypeName", required = false) String technologyTypeName,
                                            @RequestParam(value = "activated", defaultValue = "0") Integer activated) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);


        if (!StringUtil.isEmpty(technologyTypeName)) {
            params.put("technologyTypeName", technologyTypeName);
        }

        if (null != activated) {
            params.put("activated", activated);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = technologyTypeService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取技术人员类型总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("技术人员类型总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }

        List<TechnologyType> technologyTypeList;
        try {
            technologyTypeList = technologyTypeService.getTechnologyTypeList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取技术人员类型列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (technologyTypeList.size() == 0) {
            resultInfo.setMessage("技术人员类型列表为空！");
            resultInfo.setData(new ArrayList<TechnologyType>());
            return resultInfo;
        }
        resultInfo.setData(technologyTypeList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取技术人员类型列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除技术人员类型
     *
     * @param typeIds 技术人员类型id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "batchDeleteTechnologyType")
    public ResultInfo deleteBatch(@RequestParam("typeIds") String typeIds) {
        ResultInfo rsInfo = new ResultInfo();

        if (StringUtil.isEmpty(typeIds)) {
            rsInfo.setMessage("大师类型id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = technologyTypeService.deleteBatch(typeIds.split(","));
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
