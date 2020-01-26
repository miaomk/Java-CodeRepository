package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.CompanyActivityInfo;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.rs.RsActivityUser;
import com.techwells.wumei.service.ActivityOrderService;
import com.techwells.wumei.service.ActivityService;
import com.techwells.wumei.service.CompanyService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;
    @Resource
    private CompanyService companyService;
    @Resource
    private ActivityOrderService activityOrderService;

    /**
     * 增加活动
     *
     * @param activityForm   活动表单JSON字符串
     * @param activityJson   活动信息JSON字符串
     * @param multipartFiles 图片文件上传
     * @return ResultInfo
     */
    @RequestMapping("/addActivity")
    @ResponseBody
    public ResultInfo addActivity(@RequestParam("activityForm") String activityForm,
                                  @RequestParam("activity") String activityJson,
                                  @RequestParam(value = "logo", required = false) MultipartFile[] multipartFiles) {
        ResultInfo resultInfo = new ResultInfo();

        Activity activity = JSON.parseObject(activityJson, Activity.class);

        if (StringUtil.isEmpty(activity.getActivityTheme())) {
            resultInfo.setMessage("活动主题不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(activityForm)) {
            resultInfo.setMessage("报名表单不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activity.getCompanyId()) {
            resultInfo.setMessage("公司的id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activity.getActivityStartTime()) {
            resultInfo.setMessage("活动开始时间不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == activity.getActivityEndTime()) {
            resultInfo.setMessage("活动结束时间不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (activity.getActivityEndTime().isBefore(LocalDateTime.now()) || activity.getActivityStartTime().isBefore(LocalDateTime.now())) {
            resultInfo.setMessage("活动时间设置错误！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        // 处理活动logo
        if (multipartFiles != null && multipartFiles.length > 0) {

            StringBuilder fileUrl = new StringBuilder();

            for (MultipartFile file : multipartFiles) {
                try {
                    fileUrl.append(FileUtil.uploadFile(file, "activity")).append(",");

                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!StringUtil.isEmpty(fileUrl.toString())) {
                fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));

                activity.setActivityLogo(fileUrl.toString());
            }
        }

        int count;
        try {
            activity.setForm(activityForm);
            log.info("开始添加活动,活动信息为：{}", JSON.toJSONString(activity));
            count = activityService.addActivity(activity);

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("添加活动异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("添加活动失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("添加活动成功");
        resultInfo.setMessage("添加活动成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除活动
     *
     * @param activityId 活动id
     * @return ResultInfo
     */
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteActivity(@RequestParam("activityId") String activityId) {
        ResultInfo resultInfo = new ResultInfo();


        if (StringUtil.isEmpty(activityId)) {
            resultInfo.setMessage("活动id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {
            log.info("删除活动开始,活动id为:{}", activityId);
            count = activityService.deleteActivity(Integer.parseInt(activityId));
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除活动异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("删除活动成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除活动失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("删除活动结束");
        return resultInfo;
    }

    /**
     * 批量删除活动
     *
     * @param activityIds 活动字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/batchDeleteActivity")
    @ResponseBody
    public ResultInfo batchDeleteActivity(@RequestParam("activityIds") String activityIds) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(activityIds)) {
            resultInfo.setMessage("活动id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        String[] activityIdArrays = activityIds.split(",");

        int count;
        try {

            count = activityService.batchDeleteActivity(activityIdArrays);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("批量删除活动异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("批量删除活动成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("批量删除活动失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("批量删除活动结束");
        return resultInfo;
    }

    /**
     * 修改活动详情
     *
     * @param activityJson   活动信息json字符串
     * @param multipartFiles 文件上传
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/modifyActivity")
    public ResultInfo modifyActivity(@RequestParam("activity") String activityJson,
                                     @RequestParam(value = "activityForm", required = false) String activityForm,
                                     @RequestParam(value = "logo", required = false) MultipartFile[] multipartFiles) {
        ResultInfo resultInfo = new ResultInfo();

        Activity activity = JSON.parseObject(activityJson, Activity.class);

        if (StringUtil.isEmpty(activity.getActivityTheme())) {
            resultInfo.setMessage("活动主题不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (StringUtil.isEmpty(activity.getActivityLogo()) && null == multipartFiles) {
            resultInfo.setMessage("图片封面不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == activity.getCompanyId()) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == activity.getActivityStartTime()) {
            resultInfo.setMessage("活动开始时间不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == activity.getActivityEndTime()) {
            resultInfo.setMessage("活动结束时间不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        // 处理活动logo
        if (multipartFiles != null && multipartFiles.length > 0) {

            StringBuilder fileUrl = new StringBuilder();

//            for (MultipartFile file : multipartFiles) {
            try {
                //只上传最后一个
                fileUrl.append(FileUtil.uploadFile(multipartFiles[multipartFiles.length - 1], "activity")).append(",");

            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
//            }
            if (!StringUtil.isEmpty(fileUrl.toString())) {
                fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));
                activity.setActivityLogo(fileUrl.toString());
            }
        }

        int count;
        try {
            if (!StringUtil.isEmpty(activityForm)) {
                activity.setForm(activityForm);
            }

            log.info("开始修改活动,活动信息为：{}", JSON.toJSONString(activity));
            count = activityService.modifyActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("修改活动信息异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改活动信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("修改活动信息成功");
        resultInfo.setMessage("修改活动信息成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    @RequestMapping(value = "/getActivityList")
    @ResponseBody
    public ResultInfo getActivityList(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        String companyId = request.getParameter("companyId");
        String activityTheme = request.getParameter("activityTheme");
        String activityType = request.getParameter("activityType");
        String activated = request.getParameter("activated");
        String isFree = request.getParameter("isFree");
        //最新
        String newest = request.getParameter("newest");
        //热度
        String pv = request.getParameter("pv");


        if (StringUtil.isEmpty(pageNum)) {
            resultInfo.setMessage("分页页码不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (StringUtil.isEmpty(pageSize)) {
            resultInfo.setMessage("页大小不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        log.info("获取活动列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(16);

        if (!StringUtil.isEmpty(activated)) {
            params.put("activated", activated);
        }

        if (!StringUtil.isEmpty(companyId)) {
            params.put("companyId", companyId);
        }
        if (!StringUtil.isEmpty(isFree)) {
            params.put("isFree", isFree);
        }
        if (!StringUtil.isEmpty(activityTheme)) {
            params.put("activityTheme", activityTheme);
        }
        if (!StringUtil.isEmpty(activityType)) {
            params.put("activityType", activityType);
        }
        if (!StringUtil.isEmpty(newest)) {
            params.put("newest", newest);
        }
        if (!StringUtil.isEmpty(pv)) {
            params.put("pv", pv);
        }
        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = activityService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取活动总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        List<Activity> activityList;
        try {

            activityList = activityService.getActivityList(pageTool);

        } catch (Exception e) {
            resultInfo.setMessage("获取活动列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (activityList.size() == 0) {
            resultInfo.setMessage("活动列表为空！");
            resultInfo.setData(new ArrayList<Activity>());
            resultInfo.setTotal(0);
            return resultInfo;
        }

        resultInfo.setData(activityList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取活动列表成功！");
        return resultInfo;
    }

    /**
     * 获取热门活动列表
     *
     * @param request 请求
     * @return ResultInfo
     */
    @RequestMapping(value = "/getPopularActivityList")
    @ResponseBody
    public ResultInfo getPopularActivityList(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        String isFree = request.getParameter("free");
        String activityType = request.getParameter("activityType");
        String orderBy = request.getParameter("orderBy");
        String activityName = request.getParameter("activityName");
        String city = request.getParameter("city");

        if (StringUtil.isEmpty(pageNum)) {
            resultInfo.setMessage("分页页码不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (StringUtil.isEmpty(pageSize)) {
            resultInfo.setMessage("页大小不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        log.info("获取热门活动列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(16);

        if (!StringUtil.isEmpty(activityType)) {
            params.put("activityType", activityType);
        }
        if (!StringUtil.isEmpty(orderBy)) {
            params.put("orderBy", orderBy);
        }
        if (!StringUtil.isEmpty(isFree)) {
            params.put("activityFree", isFree);
        }
        if (!StringUtil.isEmpty(activityName)) {
            params.put("activityName", activityName);
        }
        if (!StringUtil.isEmpty(city)) {
            params.put("city", city);
        }

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = activityService.popularCountTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取热门活动总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (totalCount == 0) {
            resultInfo.setMessage("热门活动列表为空！");
            resultInfo.setTotal(0);
            return resultInfo;
        }
        List<RsActivity> activityList;
        try {

            activityList = activityService.getPopularActivityList(pageTool);

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取热门活动列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(activityList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取热门活动列表成功！");
        return resultInfo;
    }

    /**
     * 获取用户活动详情
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getActivityInfo")
    public ResultInfo getActivityInfo(@RequestParam("activityId") String activityId,
                                      @RequestParam("userId") String userId) {
        ResultInfo resultInfo = new ResultInfo();
        RsActivity rsActivity;

        try {
            log.info("查询活动详情开始，活动id为:{}，用户id为:{}", activityId, userId);
            rsActivity = activityService.getActivityInfo(activityId, userId);
            if (null == rsActivity) {
                resultInfo.setMessage("查询活动详情失败！");
                resultInfo.setCode("10001");
                return resultInfo;
            }
        } catch (Exception e) {
            resultInfo.setMessage("查询活动详情异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(rsActivity);
        resultInfo.setMessage("查询活动详情成功！");
        return resultInfo;
    }

    /**
     * 主办方活动管理接口
     *
     * @param companyId 公司id
     * @param activated 活动状态 0 未开始 1 正在进行 2 已结束 3 截止报名
     * @param pageNum   页数
     * @param pageSize  页码
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping("/companyActivityList")
    public ResultInfo companyActivityList(@RequestParam("companyId") String companyId,
                                          @RequestParam("activated") String activated,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtils.isBlank(companyId)) {
            resultInfo.setMessage("主办方id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        Company company = companyService.getCompany(Integer.parseInt(companyId));
        if (null == company) {

            resultInfo.setMessage("主办方id无效！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        PagingTool pagingTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(16);
        map.put("companyId", companyId);
        if (!StringUtil.isEmpty(activated)) {
            map.put("activated", activated);
        }
        pagingTool.setParams(map);
        int count;
        try {
            count = activityService.getCompanyActivityCount(pagingTool);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取主办方活动总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count == 0) {

            resultInfo.setMessage("主办方名下没有活动！");
            resultInfo.setCode("200");
            return resultInfo;
        }

        List<RsActivityManage> activityList;
        try {

            activityList = activityService.getCompanyActivityList(pagingTool);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取主办方活动列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (activityList.size() < 1) {
            resultInfo.setMessage("主办方名下没有活动，列表为空！");
            resultInfo.setData(new ArrayList<Activity>());
            resultInfo.setTotal(0);
            return resultInfo;
        }

        resultInfo.setData(activityList);
        resultInfo.setTotal(count);
        resultInfo.setMessage("获取主办方活动列表成功！");
        return resultInfo;

    }

    @ResponseBody
    @RequestMapping("/companyActivityInfo")
    public ResultInfo companyActivityInfo(@RequestParam("activityId") String activityId) {

        ResultInfo resultInfo = new ResultInfo();

        CompanyActivityInfo companyActivityInfo;
        try {

            companyActivityInfo = activityService.getCompanyActivityInfo(activityId);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取公司活动详情异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(companyActivityInfo);
        resultInfo.setMessage("获取公司活动详情成功！");
        return resultInfo;

    }

    /**
     * 用户活动管理接口
     *
     * @param userId    用户id
     * @param activated 活动状态 0 未开始 1 正在进行 2 已结束 3 截止报名
     * @param pageNum   页数
     * @param payStatus 订货支付状态 1 支付成功 2 支付失败 3 待支付
     * @param pageSize  页码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserActivityList")
    public ResultInfo getUserActivityList(@RequestParam("userId") Integer userId,
                                          @RequestParam(value = "activated", defaultValue = "") String activated,
                                          @RequestParam(value = "payStatus", defaultValue = "") String payStatus,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        ResultInfo resultInfo = new ResultInfo();
        List<RsActivityUser> userActivityList;

        PagingTool pagingTool = new PagingTool(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);

        if (!StringUtil.isEmpty(activated)) {

            map.put("activated", activated);
        }
        if (!StringUtil.isEmpty(payStatus)) {

            map.put("payStatus", payStatus);
        }
        pagingTool.setParams(map);

        int count;
        try {
            count = activityOrderService.getUserActivityCount(pagingTool);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取用户名下活动总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count == 0) {

            resultInfo.setMessage("用户名下没有活动！");
            return resultInfo;
        }

        try {

            userActivityList = activityOrderService.getActivityList(pagingTool);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取用户活动列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        resultInfo.setData(userActivityList);
        resultInfo.setMessage("获取用户活动列表成功！");
        return resultInfo;
    }

    @ResponseBody
    @RequestMapping("/queryActivityInfo")
    public ResultInfo queryActivityInfo(@RequestParam("activityId") String activityId) {
        ResultInfo resultInfo = new ResultInfo();
        Activity activity;
        try {
            activity = activityService.queryActivityInfo(activityId);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取活动详信息异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        resultInfo.setData(activity);
        resultInfo.setMessage("获取活动详信息成功！");
        return resultInfo;
    }
}
