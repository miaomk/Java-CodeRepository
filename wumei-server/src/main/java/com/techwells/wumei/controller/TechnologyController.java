package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.bo.TechnologyBO;
import com.techwells.wumei.domain.bo.TechnologyCaseBO;
import com.techwells.wumei.domain.rs.RsTechnology;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.service.TechnologyCaseService;
import com.techwells.wumei.service.TechnologyService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;

/**
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/technology")
public class TechnologyController {


    @Resource
    private TechnologyService technologyService;
    @Resource
    private UserService userService;
    @Resource
    private TechnologyCaseService technologyCaseService;

    /**
     * 大师认证接口
     *
     * @param technologyBo 大师信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/addTechnology", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addTechnology(@RequestBody TechnologyBO technologyBo) {

        ResultInfo resultInfo = new ResultInfo();
        if (!SUCCESSCODE.equals(ParamCheckUtil.technologyBoCheck(technologyBo, resultInfo).getCode())) {
            return resultInfo;
        }

        User user = userService.getUserByUserId(technologyBo.getUserId());
        if (!SUCCESSCODE.equals(ParamCheckUtil.technologyUserCheck(user, resultInfo).getCode())) {
            return resultInfo;
        }

        //赋值
        Technology technology = new Technology();
        BeanUtils.copyProperties(technologyBo, technology);
        technology.setTechnologyGender(Integer.parseInt(user.getGender()));
        //放开审核
        technology.setActivated(1);

        int count;
        try {
            log.info("开始添加大师,大师信息为：{}", JSON.toJSONString(technology));
            count = technologyService.addTechnology(technology);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("添加大师异常！");
            resultInfo.setCode("10004");
            return resultInfo;
        }
        if (count < 0) {
            resultInfo.setMessage("添加大师失败！");
            resultInfo.setCode("10005");
            return resultInfo;
        }
        try {
            //封装大师工作案例列表
            if (CollectionUtils.isNotEmpty(technologyBo.getCaseList())) {
                List<TechnologyCase> technologyCaseList = new ArrayList<>();
                for (TechnologyCaseBO technologyCaseBo : technologyBo.getCaseList()) {
                    TechnologyCase technologyCase = new TechnologyCase();
                    StringBuilder imageUrlBuilder = new StringBuilder();
                    for (String imageUrl : technologyCaseBo.getImageUrlArray()) {
                        imageUrlBuilder.append(imageUrl).append(",");
                    }

                    technologyCaseBo.setImageUrl(imageUrlBuilder.toString().substring(0, imageUrlBuilder.length() - 1));
                    BeanUtils.copyProperties(technologyCaseBo, technologyCase);
                    technologyCaseList.add(technologyCase);
                }
                //批量插入大师工作案例
                technologyCaseService.batchAddCase(technologyCaseList);
            }

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("填写大师工作案例异常！");
            resultInfo.setCode("500");
            return resultInfo;
        }
        try {
            //更新用户年龄信息
            user.setAge(technology.getTechnologyAge());
            user.setMobile(technologyBo.getMobile());
            //设置为大师类型
            user.setUserType(3);
            userService.modifyUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改用户信息异常！");
            resultInfo.setCode("500");
            return resultInfo;
        }
        log.info("添加大师成功");
        resultInfo.setMessage("添加大师成功！");
        resultInfo.setData(user);
        return resultInfo;
    }

    /**
     * 删除大师
     *
     * @return ResultInfo
     */
    @RequestMapping(value = "/deleteTechnology", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteTechnology(HttpServletRequest request) {

        ResultInfo resultInfo = new ResultInfo();

        String userId = request.getParameter("userId");

        if (StringUtil.isEmpty(userId)) {

            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }


        log.info("删除大师开始,用户id为:{}", userId);
        int count;
        try {
            count = technologyService.deleteTechnology(Integer.parseInt(userId));
        } catch (Exception e) {

            resultInfo.setMessage("删除大师异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("删除大师成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除大师失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("删除大师结束");
        return resultInfo;
    }

    /**
     * 批量删除大师
     *
     * @param request 请求
     * @return ResultInfo
     */
    @RequestMapping(value = "/batchDeleteTechnology", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo batchDeleteTechnology(HttpServletRequest request) {

        ResultInfo resultInfo = new ResultInfo();

        String userIds = request.getParameter("userIds");
        if (StringUtil.isEmpty(userIds)) {

            resultInfo.setMessage("ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        String[] userIdArrays = userIds.split(",");
        int count;
        try {
            log.info("批量删除大师开始");
            count = technologyService.batchDeleteTechnology(userIdArrays);
        } catch (Exception e) {

            resultInfo.setMessage("批量删除大师异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("批量删除大师成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("批量删除大师失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("批量删除结束人员结束");
        return resultInfo;
    }


    /**
     * 修改大师信息接口
     *
     * @param technology 大师信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/modifyTechnology", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo modifyTechnology(@RequestBody Technology technology) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.modifyTechnologyCheck(technology, resultInfo).getCode())) {
            return resultInfo;
        }
        int count;
        try {

            log.info("开始修改大师,大师信息为：{}", JSON.toJSONString(technology));
            count = technologyService.editTechnologyInfo(technology);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("修改大师异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改大师失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("修改大师成功");
        resultInfo.setMessage("修改大师成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 审核大师状态
     *
     * @param technologyId 大师id
     * @param status       审核状态 0 审核中 1 审核通过 2 审核失败
     * @return ResultInfo
     */
    @RequestMapping(value = "/editTechnologyStatus")
    @ResponseBody
    public ResultInfo editTechnologyStatus(@RequestParam("userId") Integer technologyId,
                                           @RequestParam("status") Integer status) {
        ResultInfo resultInfo = new ResultInfo();


        if (null == technologyId) {

            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == status) {

            resultInfo.setMessage("修改的状态不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {

            log.info("审核技术人开始,大师id为:{},审核状态为:{}", technologyId, status);
            count = technologyService.editTechnologyStatus(technologyId, status);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("审核大师异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("审核大师失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("审核大师成功");
        resultInfo.setMessage("审核大师成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 批量审核大师状态
     *
     * @param request 请求
     * @return ResultInfo
     */
    @RequestMapping(value = "/batchEditTechnologyStatus")
    @ResponseBody
    public ResultInfo batchEditTechnologyStatus(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String technologyIds = request.getParameter("userIds");

        String activated = request.getParameter("status");
        //参数非空校验
        if (StringUtil.isEmpty(technologyIds)) {

            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(activated)) {

            resultInfo.setMessage("修改的状态不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        //分割数组
        String[] userIds = technologyIds.split(",");
        int count;
        try {

            log.info("批量审核技术人开始,大师id为:{},审核状态为:{}", userIds, activated);
            count = technologyService.batchEditTechnologyStatus(userIds, Integer.parseInt(activated));
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("批量审核大师异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("批量审核大师失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("批量审核大师成功");
        resultInfo.setMessage("批量审核大师成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 修改大师推荐
     *
     * @param isRecommend 是否推荐
     * @param userId      大师id
     * @return ResultInfo
     */
    @RequestMapping(value = "/modifyTechnologyRecommend/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo modifyTechnologyRecommend(@RequestParam("isRecommend") String isRecommend,
                                                @PathVariable("userId") String userId) {
        ResultInfo resultInfo = new ResultInfo();


        if (StringUtil.isEmpty(isRecommend)) {

            resultInfo.setMessage("推荐状态不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        int count;
        try {
            //单个修改推荐
            log.info("修改大师推荐状态开始,大师id为:{},推荐状态为:{}", userId, isRecommend);
            count = technologyService.modifyRecommend(userId, Integer.parseInt(isRecommend));

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("修改推荐状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改推荐状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("修改推荐状态成功");
        resultInfo.setMessage("修改推荐状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    /**
     * 获取大师列表
     *
     * @param pageNum              页数
     * @param pageSize             页大小
     * @param technologyOccupation 大师类型
     * @param activated            审核状态 0 审核中 1 审核通过 2 审核失败
     * @param isRecommend          推荐状态 0 默认 1 推荐
     * @param userName             名称
     * @param createDate           最新
     * @param salary               薪资  1倒序(从高到低) 2 顺序(从低到高)
     * @param city                 城市
     * @return ResultInfo
     */
    @RequestMapping(value = "/getTechnologyList")
    @ResponseBody
    public ResultInfo getTechnologyList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam(value = "technologyOccupation", required = false) Integer technologyOccupation,
                                        @RequestParam(value = "activated", defaultValue = "1") Integer activated,
                                        @RequestParam(value = "isRecommend", required = false) Integer isRecommend,
                                        @RequestParam(value = "userName", required = false) String userName,
                                        @RequestParam(value = "createDate", required = false) Integer createDate,
                                        @RequestParam(value = "city", required = false) String city,
                                        @RequestParam(value = "salary", required = false) Integer salary) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        log.info("获取大师列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(16);

        if (null != technologyOccupation) {
            params.put("technologyOccupation", technologyOccupation);
        }

        if (null != createDate) {
            params.put("createDate", createDate);
        }
        if (!StringUtil.isEmpty(city)) {
            params.put("city", city);
        }
        if (null != salary) {
            params.put("salary", salary);
        }
        if (!StringUtil.isEmpty(userName)) {
            params.put("userName", userName);
        }
        if (null != isRecommend) {
            params.put("isRecommend", isRecommend);
        }
        if (null != activated) {
            params.put("activated", activated);
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = technologyService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取大师总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("大师列表为空！");
            resultInfo.setData(new ArrayList<Technology>());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        List<RsTechnology> technologyList;
        try {

            technologyList = technologyService.getTechnologyList(pageTool);

        } catch (Exception e) {
            resultInfo.setMessage("获取用户列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(technologyList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取大师列表成功！");
        return resultInfo;
    }

    /**
     * 获取大师信息
     *
     * @param technologyId 大师id
     * @return ResultInfo
     */
    @RequestMapping(value = "/getTechnologyInfo")
    @ResponseBody
    public ResultInfo getTechnologyInfo(@RequestParam("userId") Integer userId,
                                        @RequestParam("technologyId") Integer technologyId) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == technologyId) {
            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == userId) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("获取大师详细信息开始,用户id为：{},大师id为：{}", userId, technologyId);


        RsTechnology technology;
        try {

            technology = technologyService.getTechnologyInfo(userId, technologyId);

        } catch (Exception e) {
            resultInfo.setMessage("获取用户信息异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technology) {
            resultInfo.setMessage("该用户不是大师！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        log.info("获取大师信息成功,详细信息为:{}", JSON.toJSONString(technology));
        resultInfo.setData(technology);
        resultInfo.setMessage("获取大师信息成功！");
        return resultInfo;
    }

    /**
     * 获取热门大师列表
     *
     * @param pageNum              页数
     * @param pageSize             页大小
     * @param technologyOccupation 大师职业类型
     * @param city                 城市
     * @param salary               薪资  1倒序(从高到低) 2 顺序(从低到高)
     * @return ResultInfo
     */
    @RequestMapping(value = "/getHotTechnologyList")
    @ResponseBody
    public ResultInfo getHotTechnologyList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize,
                                           @RequestParam(value = "technologyOccupation", required = false) Integer technologyOccupation,
                                           @RequestParam(value = "city", required = false) String city,
                                           @RequestParam(value = "salary", required = false) Integer salary) {
        ResultInfo resultInfo = new ResultInfo();


        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }


        log.info("获取热门大师列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(12);
        if (null != technologyOccupation) {
            params.put("technologyOccupation", technologyOccupation);
        }
        if (null != salary) {
            params.put("salary", salary);
        }
        if (!StringUtil.isEmpty(city)) {
            params.put("city", city);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = technologyService.hotCountTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取热门大师总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("热门大师列表为空！");
            resultInfo.setData(new ArrayList<Technology>());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        List<HotTechnologyVo> technologyList;
        try {

            technologyList = technologyService.getHotTechnologyList(pageTool);

        } catch (Exception e) {
            resultInfo.setMessage("获取热门大师列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setData(technologyList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取热门大师列表成功！");
        return resultInfo;
    }

}
