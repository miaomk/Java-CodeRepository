package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.techwells.wumei.domain.Commodity;
import com.techwells.wumei.domain.rs.RsCommodity;
import com.techwells.wumei.service.CommodityService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 严选租赁商品Controller
 *
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/commodity/")
public class CommodityController {

    @Resource
    private CommodityService commodityService;

    /**
     * 新增租赁商品
     *
     * @param commodityBo    租赁商品信息
     * @param multipartFiles 商品图片上传
     * @return ResultInfo
     */
    @RequestMapping(value = "addCommodity")
    @ResponseBody
    public ResultInfo addCommodity(@RequestParam("commodity") String commodityBo,
                                   @RequestParam(required = false, value = "commodityIcon") MultipartFile[] multipartFiles) {
        ResultInfo resultInfo = new ResultInfo();
        //严选租赁商品实体类
        Commodity commodity = JSON.parseObject(commodityBo, Commodity.class);
        if (null == commodity) {
            resultInfo.setMessage("商品不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(commodity.getCommodityName())) {
            resultInfo.setMessage("商品名不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        // 处理商品图片
        if (multipartFiles != null && multipartFiles.length > 0) {
            StringBuilder fileUrl = new StringBuilder();
            for (MultipartFile file : multipartFiles) {
                try {
                    fileUrl.append(FileUtil.uploadFile(file, "commodity")).append(",");
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!StringUtil.isEmpty(fileUrl.toString())) {
                fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));

                commodity.setCommodityIcon(fileUrl.toString());
            }
        } else {
            resultInfo.setMessage("商品图片不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            log.info("增加租赁商品开始,商品详情为:{}", JSON.toJSONString(commodity));
            count = commodityService.addCommodity(commodity);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加租赁商品异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("增加租赁商品成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("增加租赁商品失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("增加租赁商品成功！");
        resultInfo.setMessage("增加租赁商品成功");
        return resultInfo;
    }

    /**
     * 删除租赁商品
     *
     * @param commodityId 商品id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "deleteCommodity", method = RequestMethod.POST)
    public ResultInfo deleteCommodity(@RequestParam("commodityId") String commodityId) {
        ResultInfo resultInfo = new ResultInfo();
        if (StringUtil.isEmpty(commodityId)) {
            resultInfo.setMessage("商品id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            log.info("删除租赁商品开始,商品id为:{}", commodityId);
            count = commodityService.deleteCommodity(commodityId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除租赁商品异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("删除租赁商品成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除租赁商品失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("删除租赁商品成功！");
        resultInfo.setMessage("删除租赁商品成功！");
        return resultInfo;
    }

    /**
     * 修改租赁商品信息
     *
     * @param commodityBo    租赁商品信息
     * @param multipartFiles 商品图片
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyCommodity")
    @ResponseBody
    public ResultInfo modifyCommodity(@RequestParam("commodity") String commodityBo,
                                      @RequestParam(required = false, value = "commodityIcon") MultipartFile[] multipartFiles) {
        ResultInfo resultInfo = new ResultInfo();
        Commodity commodity = JSON.parseObject(commodityBo, Commodity.class);
        if (null == commodity.getCommodityId()) {
            resultInfo.setMessage("商品id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(commodity.getCommodityName())) {
            resultInfo.setMessage("商品名不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(commodity.getCommodityIcon()) && null == multipartFiles) {
            resultInfo.setMessage("商品图片不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        // 处理商品图片
        if (multipartFiles != null && multipartFiles.length > 0) {
            StringBuilder fileUrl = new StringBuilder();
            for (MultipartFile file : multipartFiles) {
                try {
                    fileUrl.append(FileUtil.uploadFile(file, "commodity")).append(",");
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!StringUtil.isEmpty(fileUrl.toString())) {

                if (StringUtil.isEmpty(commodity.getCommodityIcon())) {

                    fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));
                    commodity.setCommodityIcon(fileUrl.toString());
                } else {

                    commodity.setCommodityIcon(fileUrl.append(commodity.getCommodityIcon()).toString());
                }

            }
        }
        int count;
        try {
            log.info("修改租赁商品开始,商品详情为:{}", JSON.toJSONString(commodity));
            count = commodityService.modifyCommodity(commodity);
            if (count < 1) {
                resultInfo.setMessage("修改租赁商品失败！");
                resultInfo.setCode("10001");
                return resultInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改租赁商品异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("修改租赁商品成功！");
        resultInfo.setMessage("修改租赁商品成功");
        resultInfo.setTotal(count);
        return resultInfo;
    }

    /**
     * 修改租赁商品推荐状态
     *
     * @param commodityId 租赁商品id
     * @param recommend   推荐状态 0 默认 1 推荐
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "modifyCommodityRecommend", method = RequestMethod.POST)
    public ResultInfo modifyCommodityRecommend(@RequestParam("commodityId") String commodityId,
                                               @RequestParam("recommend") String recommend) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(commodityId)) {
            resultInfo.setMessage("商品id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            log.info("修改租赁商品推荐状态开始,商品id为:{},推荐状态为:{}", commodityId, recommend);
            count = commodityService.editCommodityRecommend(commodityId, recommend);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改租赁商品推荐状态异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("修改租赁商品推荐状态成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("修改租赁商品推荐状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("修改租赁商品推荐状态成功！");
        resultInfo.setMessage("修改租赁商品推荐状态成功！");
        return resultInfo;
    }

    /**
     * 修改租赁商品上下架状态
     *
     * @param commodityId 租赁商品id
     * @param activated   上下架状态  0 未上架 1 上架 2 下架
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "modifyCommodityStatus", method = RequestMethod.POST)
    public ResultInfo modifyCommodityStatus(@RequestParam("commodityId") String commodityId,
                                            @RequestParam("activated") String activated) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(commodityId)) {
            resultInfo.setMessage("商品id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        int count;
        try {
            log.info("修改租赁商品状态开始,商品id为:{},修改状态为:{}", commodityId, activated);
            count = commodityService.modifyCommodityStatus(commodityId, activated);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改租赁商品状态异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("修改租赁商品状态成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("修改租赁商品状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("修改租赁商品状态成功！");
        resultInfo.setMessage("修改租赁商品状态成功！");
        return resultInfo;
    }

    /**
     * 分页查询租赁商品列表
     *
     * @param request 申请
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "getCommodityList")
    public ResultInfo getCommodityList(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        //严选租赁商品类型  1 灯光 2音响 3视频 4其他
        String commodityType = request.getParameter("commodityType");
        //地区
        String location = request.getParameter("location");
        //推荐
        String recommend = request.getParameter("recommend");
        //最新
        String createDate = request.getParameter("createDate");
        //租赁商品名称
        String commodityName = request.getParameter("commodityName");
        //主办方id
        String companyId = request.getParameter("companyId");

        //参数校验
        if (StringUtil.isEmpty(pageNum)) {
            resultInfo.setMessage("查询页码不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(pageSize)) {
            resultInfo.setMessage("每页大小不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        log.info("获取租赁商品列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(16);

        if (!StringUtil.isEmpty(commodityType)) {
            params.put("commodityType", commodityType);
        }

        if (!StringUtil.isEmpty(location)) {
            params.put("location", location);
        }

        if (!StringUtil.isEmpty(recommend)) {
            params.put("recommend", recommend);
        }

        if (!StringUtil.isEmpty(createDate)) {
            params.put("createDate", createDate);
        }
        if (!StringUtil.isEmpty(commodityName)) {
            params.put("commodityName", commodityName);
        }
        if (!StringUtil.isEmpty(companyId)) {
            params.put("companyId", companyId);
        }
        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = commodityService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取严选租赁商品总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("严选租赁商品总数为空");
            resultInfo.setTotal(0);
            return resultInfo;
        }
        List<RsCommodity> rsCommodities;
        try {
            rsCommodities = commodityService.getCommodityList(pageTool);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取租赁商品列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("获取租赁商品列表成功!,列表为:{}", JSONArray.toJSONString(rsCommodities));
        resultInfo.setData(rsCommodities);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取租赁商品列表成功！");
        return resultInfo;
    }

    /**
     * 通过商品id查询商品详情
     *
     * @param commodityId 商品id
     * @param userId      用户id
     * @param queryType   查询类型 1 小程序查询 2 后台查询
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "getCommodityById")
    public ResultInfo getCommodityById(@RequestParam("commodityId") String commodityId,
                                       @RequestParam(value = "userId", required = false) String userId,
                                       @RequestParam(value = "queryType") Integer queryType) {
        ResultInfo resultInfo = new ResultInfo();

        //参数校验
        if (StringUtil.isEmpty(commodityId)) {
            resultInfo.setMessage("商品id不能为空");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        //小程序查询校验用户id
        if (1 == queryType && StringUtil.isEmpty(userId)) {
            resultInfo.setMessage("用户id不能为空");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("查询租赁商品信息开始,商品id为:{}", commodityId);
        RsCommodity rsCommodity;
        try {
            rsCommodity = commodityService.getCommodityInfoById(commodityId, userId);
            if (null == rsCommodity) {
                resultInfo.setMessage("获取租赁商品信息异常，商品不存在！");
                return resultInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取租赁商品信息异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        log.info("获取租赁商品列表成功!,列表为:{}", JSON.toJSONString(rsCommodity));
        resultInfo.setData(rsCommodity);
        resultInfo.setMessage("获取租赁商品信息成功！");
        return resultInfo;
    }

}
