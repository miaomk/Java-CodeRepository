package com.techwells.wumei.controller;

import com.techwells.wumei.domain.PV;
import com.techwells.wumei.service.PvService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 浏览Controller
 *
 * @author miao
 */
@RestController
@Slf4j
@RequestMapping("/pv/")
public class PvController {

    @Resource
    private PvService pvService;

    /**
     * 添加浏览记录
     *
     * @param userId     用户id
     * @param relationId 被浏览id
     * @param pvType     浏览类型1 活动 2 商品 3 严选租赁 4 工作案例
     * @return ResultInfo
     */
    @RequestMapping("addPv")
    public ResultInfo addPv(@RequestParam("userId") Integer userId,
                            @RequestParam("relationId") Integer relationId,
                            @RequestParam("pvType") Integer pvType) {
        ResultInfo resultInfo = new ResultInfo();
        if (null == userId) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == relationId) {
            resultInfo.setMessage("浏览id不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }

        if (null == pvType) {
            resultInfo.setMessage("浏览类型不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        }

        PV pv = new PV();
        pv.setPvType(pvType);
        pv.setRelationId(relationId);
        pv.setUserId(userId);

        int count;
        try {

            count = pvService.addPv(pv);
        } catch (Exception e) {

            resultInfo.setMessage("添加浏览记录异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("添加浏览记录成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("添加浏览记录失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        return resultInfo;
    }

    /**
     * 获取浏览数
     *
     * @param relationId 被浏览id
     * @param pvType     浏览类型1 活动 2 商品 3 严选租赁
     * @return ResultInfo
     */
    @RequestMapping("getPvCount")
    public ResultInfo getPvCount(@RequestParam("relationId") Integer relationId,
                                 @RequestParam("pvType") Integer pvType) {
        ResultInfo resultInfo = new ResultInfo();

        PagingTool pagingTool = new PagingTool();
        Map<String, Object> map = new HashMap<>(12);

        map.put("relationId", relationId);
        map.put("pvType", pvType);

        pagingTool.setParams(map);
        int count;
        try {

            count = pvService.countTotal(pagingTool);
        } catch (Exception e) {

            resultInfo.setMessage("获取浏览总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(count);
        resultInfo.setTotal(count);

        return resultInfo;
    }

}
