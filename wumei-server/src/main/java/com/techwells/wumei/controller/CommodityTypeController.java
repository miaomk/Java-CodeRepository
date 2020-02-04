package com.techwells.wumei.controller;

import com.techwells.wumei.domain.CommodityType;
import com.techwells.wumei.service.CommodityTypeService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


@RestController
@RequestMapping(value = "/commodityType/")
public class CommodityTypeController {

    @Resource
    private CommodityTypeService commodityTypeService;

    /**
     * 增加租赁商品类型
     *
     * @param commodityType 租赁商品类型实体类
     * @return ResultInfo
     */
    @RequestMapping(value = "addCommodityType", method = RequestMethod.POST)
    public ResultInfo addCommodityType(@RequestBody CommodityType commodityType) {
        ResultInfo resultInfo = new ResultInfo();
        if (StringUtil.isEmail(commodityType.getTypeName())) {

            resultInfo.setMessage("租赁商品类型名称不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = commodityTypeService.addCommodityType(commodityType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加租赁商品类型异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加租赁商品类型成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加租赁商品类型失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改租赁商品类型信息
     *
     * @param commodityType 租赁商品类型实体类
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyCommodityType", method = RequestMethod.POST)
    public ResultInfo modifyCommodityType(@RequestBody CommodityType commodityType) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == commodityType.getTypeId()) {
            resultInfo.setMessage("租赁商品类型id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {

            count = commodityTypeService.modifyCommodityType(commodityType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改租赁商品类型异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改租赁商品类型失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改租赁商品类型成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除租赁商品类型
     *
     * @param commodityTypeId 租赁商品类型id
     * @return ResultInfo
     */
    @RequestMapping(value = "deleteCommodityType/{commodityTypeId}", method = RequestMethod.POST)
    public ResultInfo deleteCommodityType(@PathVariable("commodityTypeId") Integer commodityTypeId) {
        ResultInfo resultInfo = new ResultInfo();

        int count;
        try {

            count = commodityTypeService.delCommodityType(commodityTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除租赁商品类型异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除租赁商品类型成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除租赁商品类型失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 查看租赁商品类型详情
     *
     * @param commodityTypeId 租赁商品类型id
     * @return ResultInfo
     */
    @RequestMapping(value = "getCommodityInfo/{commodityTypeId}", method = RequestMethod.GET)
    public ResultInfo getCommodityTypeById(@PathVariable("commodityTypeId") Integer commodityTypeId) {
        ResultInfo rsInfo = new ResultInfo();

        CommodityType commodityType;
        try {

            commodityType = commodityTypeService.getCommodityTypeInfo(commodityTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取租赁商品类型信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (commodityType == null) {
            rsInfo.setMessage("租赁商品类型信息不存在！");
            rsInfo.setData(new CommodityType());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(commodityType);
        rsInfo.setMessage("获取租赁商品类型成功！");
        return rsInfo;
    }

    /**
     * 获取租赁商品类型列表
     *
     * @param pageNum           页数
     * @param pageSize          页大小
     * @param commodityTypeName 租赁商品类型名称
     * @return ResultInfo
     */
    @RequestMapping(value = "getCommodityTypeList", method = RequestMethod.GET)
    public ResultInfo getCommodityTypeList(@RequestParam(value = "pageNum") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize,
                                           @RequestParam(value = "commodityTypeName", required = false) String commodityTypeName) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        if (commodityTypeName != null && !"".equals(commodityTypeName)) {
            params.put("commodityTypeName", commodityTypeName);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = commodityTypeService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取租赁商品类型总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("租赁商品类型总数量为0！");
            return resultInfo;
        }

        List<CommodityType> commodityTypeList;
        try {
            commodityTypeList = commodityTypeService.getCommodityTypeList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取租赁商品类型列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (commodityTypeList.size() == 0) {
            resultInfo.setMessage("租赁商品类型列表为空！");
            resultInfo.setData(new ArrayList<CommodityType>());
            return resultInfo;
        }
        resultInfo.setData(commodityTypeList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取租赁商品类型列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除租赁商品类型
     *
     * @param typeIds 租赁商品类型id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "batchDeleteCommodityType", method = RequestMethod.POST)
    public ResultInfo deleteBatch(@RequestParam("id") String typeIds) {
        ResultInfo rsInfo = new ResultInfo();

        if (StringUtil.isEmpty(typeIds)) {
            rsInfo.setMessage("租赁商品类型id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = commodityTypeService.deleteBatch(typeIds.split(","));
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
