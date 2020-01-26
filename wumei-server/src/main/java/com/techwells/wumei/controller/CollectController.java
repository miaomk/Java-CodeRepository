package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Collect;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.COLLECTTECHTYPE;
import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;

/**
 * 收藏Controller
 *
 * @author Administrator
 */
@RestController
public class CollectController {

    @Resource
    private CollectService collectService;
    @Resource
    private CommodityService commodityService;
    @Resource
    private TechnologyService technologyService;
    @Resource
    private DemandService demandService;
    @Resource
    private ProductService productService;
    @Resource
    private ActivityService activityService;

    /**
     * 添加收藏
     *
     * @param userId      收藏者用户id
     * @param collectType 收藏类型 1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @param relationId  被收藏者id
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/addCollect")
    public ResultInfo addCollect(@RequestParam("userId") String userId,
                                 @RequestParam("collectType") String collectType,
                                 @RequestParam("relationId") String relationId) {
        ResultInfo resultInfo = new ResultInfo();

        if (userId == null || "".equals(userId)) {
            resultInfo.setMessage("用户ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (collectType == null || "".equals(collectType)) {
            resultInfo.setMessage("收藏类型不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (relationId == null || "".equals(relationId)) {
            resultInfo.setMessage("被收藏者ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (userId.equals(relationId) && COLLECTTECHTYPE.equals(collectType)) {
            resultInfo.setMessage("您无法收藏您自己！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        collect.setCollectType(Integer.parseInt(collectType));
        collect.setRelationId(Integer.parseInt(relationId));

        Collect c;
        try {
            c = collectService.getCollectByCollect(collect);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("获取收藏异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (c != null) {
            resultInfo.setMessage("您已经收藏过了，请勿重复收藏！");
            resultInfo.setCode("10001");
        }

        collect.setCreatedDate(new Date());

        int count;
        try {
            count = collectService.addCollect(collect);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("添加收藏异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加收藏成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加收藏失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改收藏信息
     *
     * @param collectId   收藏ID
     * @param collectName 收藏名称
     * @return ResultInfo
     */
    @Deprecated
    @RequestMapping(value = "/collect/modifyCollect")
    public ResultInfo modifyCollect(@RequestParam("collectId") String collectId,
                                    @RequestParam("collectName") String collectName) {
        ResultInfo rsInfo = new ResultInfo();

        if (collectId == null || "".equals(collectId)) {
            rsInfo.setMessage("收藏ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (collectName == null || "".equals(collectName)) {
            rsInfo.setMessage("收藏名称不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        Collect collect = new Collect();

        collect.setCollectId(Integer.parseInt(collectId));

        int count;
        try {
            count = collectService.modifyCollect(collect);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改收藏异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改收藏失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改收藏成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除收藏
     *
     * @param collectId 收藏id
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/deleteCollect")
    public ResultInfo deleteCollect(@RequestParam("collectId") String collectId) {
        ResultInfo rsInfo = new ResultInfo();

        if (collectId == null || "".equals(collectId)) {
            rsInfo.setMessage("收藏Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        int count;
        try {
            count = collectService.delCollect(Integer.parseInt(collectId));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("取消收藏异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("取消收藏成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("取消收藏失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 取消收藏
     *
     * @param userId      收藏者id
     * @param relationId  被收藏者id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/delCollect")
    public ResultInfo delCollect(@RequestParam("userId") String userId,
                                 @RequestParam("relationId") String relationId,
                                 @RequestParam("collectType") String collectType) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(userId)) {
            resultInfo.setMessage("收藏者Id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (StringUtil.isEmpty(relationId)) {
            resultInfo.setMessage("被收藏者Id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (StringUtil.isEmpty(collectType)) {
            resultInfo.setMessage("收藏类型不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = collectService.deleteCollect(userId, relationId, collectType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("取消收藏异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("取消收藏成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("取消收藏失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        return resultInfo;
    }

    /**
     * 查看收藏详情
     *
     * @param collectId 收藏id
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/getCollectById")
    public ResultInfo getCollectById(@RequestParam("collectId") Integer collectId) {
        ResultInfo rsInfo = new ResultInfo();

        if (collectId == null) {
            rsInfo.setMessage("收藏ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        Collect collect;
        try {
            collect = collectService.getCollectByCollectId(collectId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取收藏信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (collect == null) {
            rsInfo.setMessage("收藏信息不存在！");
            rsInfo.setData(new Collect());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(collect);
        rsInfo.setMessage("获取收藏成功！");
        return rsInfo;
    }

    /**
     * 获取收藏列表
     *
     * @param pageNum  页数
     * @param pageSize 页大小
     * @param userId   用户id
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/getCollectList")
    public ResultInfo getCollectList(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value = "userId", required = false) String userId) {
        ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (pageNum == null) {
            rsInfo.setMessage("页码不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (pageSize == null) {
            rsInfo.setMessage("页大小不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (userId != null && !"".equals(userId)) {
            params.put("userId", userId);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = collectService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取收藏总数异常！");
            rsInfo.setCode("10002");
            return rsInfo;
        }

        if (totalCount == 0) {
            rsInfo.setMessage("收藏总数量为0！");
            rsInfo.setCode("23211");
            return rsInfo;
        }

        List<RsCollect> collectList;
        try {
            collectList = collectService.getCollectList(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取收藏列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (collectList.size() == 0) {
            rsInfo.setMessage("收藏列表为空！");
            rsInfo.setData(new ArrayList<Collect>());
            return rsInfo;
        }
        rsInfo.setData(collectList);
        rsInfo.setTotal(totalCount);
        rsInfo.setMessage("获取收藏列表成功！");
        return rsInfo;
    }


    /**
     * 获取收藏列表
     *
     * @param pageNum     页数
     * @param pageSize    页大小
     * @param userId      用户id
     * @param collectType 收藏类型 1 收藏商品 2 收藏活动 3 收藏租赁商品 4 大师  5 需求
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/getMyCollectList")
    public ResultInfo getMyCollectList(@RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "userId") String userId,
                                       @RequestParam(value = "collectType") Integer collectType) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        if (userId == null || "".equals(userId)) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        } else {
            params.put("userId", userId);
        }

        if (null == collectType) {
            resultInfo.setMessage("收藏类型不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        } else {
            params.put("collectType", collectType);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = collectService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取收藏总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("用户收藏列表为空！");
            resultInfo.setData(new ArrayList<Collect>());
            return resultInfo;
        }

        try {
            //查询出关联id列表
            List<Integer> relationIdList = collectService.getRelationIdList(userId, collectType);
            switch (collectType) {
                case 1:
                    resultInfo.setData(collectService.getCollectList(pageTool));
                    break;
                case 2:
                    resultInfo.setData(activityService.getCollectActivityList(relationIdList, pageTool));
                    break;
                case 3:
                    resultInfo.setData(commodityService.getCollectCommodityList(relationIdList, pageTool));
                    break;
                case 4:
                    resultInfo.setData(technologyService.getCollectTechnologyList(relationIdList, pageTool));
                    break;
                case 5:
                    resultInfo.setData(demandService.getCollectDemandList(relationIdList, pageTool));
                    break;
                default:
                    resultInfo.setData(new ArrayList<>());
            }
        } catch (Exception e) {
            resultInfo.setMessage("获取用户收藏列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取收藏列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除收藏
     *
     * @param ids 收藏id数组
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/batchDelete")
    public ResultInfo batchDelete(@RequestParam("ids") String ids) {
        ResultInfo rsInfo = new ResultInfo();


        if (StringUtil.isEmpty(ids)) {
            rsInfo.setMessage("收藏id不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        String[] idArrays = ids.split(",");
        int count;
        try {
            count = collectService.batchDelete(idArrays, null);
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
     * 批量删除我的收藏
     *
     * @param ids    收藏id字符串
     * @param userId 用户id
     * @return ResultInfo
     */
    @RequestMapping(value = "/collect/batchDeleteMyCollect")
    public ResultInfo batchDeleteMyCollect(@RequestParam("ids") String ids,
                                           @RequestParam("userId") String userId) {
        ResultInfo rsInfo = new ResultInfo();


        if (userId == null || "".equals(userId)) {
            rsInfo.setMessage("用户ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (StringUtil.isEmpty(ids)) {
            rsInfo.setMessage("收藏id不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        String[] idArrays = ids.split(",");

        int count;
        try {
            count = collectService.batchDelete(idArrays, Integer.parseInt(userId));
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
