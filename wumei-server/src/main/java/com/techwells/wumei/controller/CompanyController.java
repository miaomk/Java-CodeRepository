package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsCompany;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;

/**
 * 主办方Controller
 *
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;
    @Resource
    private ActivityService activityService;
    @Resource
    private UserService userService;
    @Resource
    private TechnologyService technologyService;
    @Resource
    private FocusService focusService;

    /**
     * 增加公司接口
     *
     * @param request        公司信息
     * @param multipartFiles 公司证明图片上传
     * @return ResultInfo
     */
    @RequestMapping("/addCompany")
    @ResponseBody
    public ResultInfo  addCompany(HttpServletRequest request,
                                 @RequestParam(required = false, value = "prove") MultipartFile[] multipartFiles) {
        ResultInfo resultInfo = new ResultInfo();

        String userId = request.getParameter("userId");
        String companyName = request.getParameter("companyName");
        String companyTaxNumber = request.getParameter("companyTaxnumber");
        String prove = request.getParameter("companyProve");
        String contact = request.getParameter("contact");
        String position = request.getParameter("position");
        String companyIntroduction = request.getParameter("companyIntroduction");

        //参数校验
        if (StringUtil.isEmpty(companyName)) {
            resultInfo.setMessage("公司名不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(contact)) {
            resultInfo.setMessage("公司联系方式不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (StringUtil.isEmpty(companyTaxNumber)) {
            resultInfo.setMessage("公司税号不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (StringUtil.isEmpty(userId)) {
            resultInfo.setMessage("用户id不能为空!");
            resultInfo.setCode("10003");
            return resultInfo;
        }

        //判断用户是否存在
        User user = userService.getUserByUserId(Integer.parseInt(userId));
        if (!SUCCESSCODE.equals(ParamCheckUtil.companyUserCheck(user, resultInfo).getCode())) {
            return resultInfo;
        }

        //数据封装
        RsCompany company = new RsCompany();
        company.setCompanyName(companyName);
        company.setUserId(Integer.parseInt(userId));
        company.setCompanyTaxnumber(companyTaxNumber);
        company.setContact(contact);
        company.setCompanyProve(prove);
        company.setPosition(position);
        company.setCompanyIntroduction(companyIntroduction);
        //审核通过，目前放开审核
        company.setActivated(1);

        // 处理主办方证明材料图片
        if (multipartFiles != null && multipartFiles.length > 0) {
            String companyProve = UploadImageUtil.uploadImage("company", multipartFiles);
            if (!StringUtil.isEmpty(companyProve)) {

                company.setCompanyProve(companyProve);
            } else {
                resultInfo.setMessage("公司证明图片上传出错！");
                resultInfo.setCode("10001");
                return resultInfo;
            }
        }

        int count;
        try {
            log.info("开始主办方认证,公司信息为：{}", JSON.toJSONString(company));
            count = companyService.addCompany(company);

        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("主办方认证异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("主办方认证失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        try {
            //查询最新的用户信息
            user = userService.getUserByUserId(Integer.parseInt(userId));
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("修改用户信息异常！");
            resultInfo.setCode("500");
            return resultInfo;
        }
        log.info("主办方认证完成");
        resultInfo.setMessage("主办方认证完成");
        resultInfo.setData(user);
        return resultInfo;
    }

    /**
     * 删除公司接口
     *
     * @param request 请求
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
    public ResultInfo deleteCompany(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String companyId = request.getParameter("companyId");

        if (StringUtil.isEmpty(companyId)) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {

            count = companyService.deleteCompany(Integer.valueOf(companyId));
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("删除公司异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除公司成功！");
            resultInfo.setData(count);
        } else if (count == 0) {
            resultInfo.setMessage("公司不存在，删除失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        } else {
            resultInfo.setMessage("删除公司失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 批量删除公司接口
     *
     * @param request 请求
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping("/batchDeleteCompany")
    public ResultInfo batchDeleteCompany(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        String companyIds = request.getParameter("companyIds");

        if (StringUtil.isEmpty(companyIds)) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        String[] companyIdArrays = companyIds.split(",");


        int count;
        try {

            count = companyService.batchDeleteCompany(companyIdArrays);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("批量删除公司异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("批量删除公司成功！");
            resultInfo.setData(count);
        } else if (count == 0) {
            resultInfo.setMessage("公司不存在，批量删除失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        } else {
            resultInfo.setMessage("批量删除公司失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改公司接口
     *
     * @param company 公司详情
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping("/modifyCompany")
    public ResultInfo modifyCompany(@RequestBody Company company) {
        ResultInfo resultInfo = new ResultInfo();

        //Company company = JSON.parseObject(request.getParameter("company"), Company.class);

        if (StringUtil.isEmpty(company.getCompanyName())) {
            resultInfo.setMessage("公司名称不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == company.getCompanyId()) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;
        try {
            log.info("修改公司信息开始,公司详细信息为:{}", JSON.toJSONString(company));
            count = companyService.modifyCompany(company);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("修改公司信息异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("修改公司信息成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("修改公司信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 修改公司状态
     *
     * @param request 请求
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/modifyCompanyActivated", method = RequestMethod.POST)
    public ResultInfo modifyCompanyActivated(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String companyId = request.getParameter("companyId");
        String activated = request.getParameter("activated");

        if (StringUtil.isEmpty(companyId)) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (StringUtil.isEmpty(activated)) {
            resultInfo.setMessage("状态不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        int count;

        try {
            String ids = ",";
            if (companyId.contains(ids)) {

                String[] companyIdsArrays = companyId.split(",");
                count = companyService.batchModifyCompanyStatus(companyIdsArrays, activated);
            } else {
                count = companyService.modifyCompanyStatus(companyId, activated);
            }
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("审核公司信息异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("审核公司信息成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("审核公司信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 分页获取公司列表
     *
     * @param pageNum     页数
     * @param pageSize    页大小
     * @param companyName 公司名称
     * @param activated   审核状态 0 审核中 1审核已通过 2审核失败
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.GET)
    public ResultInfo getCompanyList(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value = "companyName", required = false) String companyName,
                                     @RequestParam(value = "activated", required = false) Integer activated) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        log.info("获取公司列表开始,{}页，{}行", pageNum, pageSize);
        HashMap<String, Object> params = new HashMap<>(16);

        if (!StringUtil.isEmpty(companyName)) {
            params.put("companyName", companyName);
        }

        if (null != activated) {
            params.put("activated", activated);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = companyService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取公司总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        List<RsCompany> companyList;
        try {
            companyList = companyService.getCompanyList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取公司列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (companyList.size() == 0) {
            resultInfo.setMessage("公司列表为空！");
            resultInfo.setData(new ArrayList<RsCompany>());
            resultInfo.setTotal(0);
            return resultInfo;
        }

        log.info("获取公司列表成功,公司列表为:{}", JSONArray.toJSONString(companyList));

        resultInfo.setData(companyList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取公司列表成功！");
        return resultInfo;
    }

    /**
     * 根据用户id获取公司详情
     *
     * @param userId 用户id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyInfoByUserId")
    public ResultInfo getCompanyInfoByUserId(@RequestParam("userId") Integer userId) {
        ResultInfo resultInfo = new ResultInfo();


        if (null == userId) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        User user = userService.getUserByUserId(userId);
        if (null == user) {
            resultInfo.setMessage("该用户不存在！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        Company company = companyService.getCompanyById(user.getCompanyId());
        if (null == company) {
            resultInfo.setMessage("该用户没有主办方身份！");
            resultInfo.setCode("10003");
            return resultInfo;
        }
        //判断公司是否审核通过
        if (1 != company.getActivated()) {
            resultInfo.setMessage("该用户公司审核未通过!");
            resultInfo.setCode("10004");
            return resultInfo;
        }

        RsCompany rsCompany;
        try {

            rsCompany = companyService.getCompanyInfoByUserId(userId);

        } catch (Exception e) {
            resultInfo.setMessage("获取公司列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("获取用户主办方信息成功,信息为:{}", JSON.toJSONString(rsCompany));

        resultInfo.setData(rsCompany);
        resultInfo.setMessage("获取用户主办方信息成功！");
        return resultInfo;
    }

    /**
     * 根据公司id获取公司详情
     *
     * @param companyId 公司id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
    public ResultInfo getCompanyInfo(@RequestParam("companyId") Integer companyId) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == companyId) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        Company company;
        try {

            company = companyService.getCompanyById(companyId);

        } catch (Exception e) {
            resultInfo.setMessage("获取公司列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == company) {
            resultInfo.setMessage("公司不存在！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        log.info("获取用户主办方信息成功,信息为:{}", JSON.toJSONString(company));

        resultInfo.setData(company);
        resultInfo.setMessage("获取用户主办方信息成功！");
        return resultInfo;
    }

    /**
     * 根据公司id获取公司详情
     *
     * @param companyId 公司id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyInfoByCompanyId")
    public ResultInfo getCompanyInfoByCompanyId(@RequestParam("companyId") String companyId,
                                                @RequestParam(value = "userId") String userId) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(companyId)) {
            resultInfo.setMessage("公司id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(userId)) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        RsCompany rsCompany;
        try {

            rsCompany = companyService.getCompanyInfoByCompanyId(Integer.parseInt(companyId));
            //查询关注状态
            int focusStatus = focusService.queryFocusStatus(Integer.parseInt(userId), Integer.parseInt(companyId), 1);
            List<RsActivity> rsActivityList = activityService.getActivityByCompanyId(rsCompany.getCompanyId());

            rsCompany.setRsActivityList(rsActivityList);
            rsCompany.setFocusStatus(focusStatus);
        } catch (Exception e) {
            resultInfo.setMessage("获取公司列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("获取用户主办方信息成功,信息为:{}", JSON.toJSONString(rsCompany));

        resultInfo.setData(rsCompany);
        resultInfo.setMessage("获取用户主办方信息成功！");
        return resultInfo;
    }
}