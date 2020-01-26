package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.Authentication;
import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.bo.AuthenticationBO;
import com.techwells.wumei.service.AuthenticationService;
import com.techwells.wumei.service.CompanyService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.service.VerificationCodeService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;

/**
 * 实名注册Controller
 *
 * @author miao
 */
@RestController
@Slf4j
public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private VerificationCodeService verificationCodeService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    /**
     * 添加实名认证
     *
     * @param authenticationBo 实名认证实体类信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/addAuthentication", method = RequestMethod.POST)
    public ResultInfo addAuthentication(@RequestBody AuthenticationBO authenticationBo) {
        ResultInfo resultInfo = new ResultInfo();
        //参数校验
        if (!SUCCESSCODE.equals(ParamCheckUtil.authenticationBoCheck(authenticationBo, resultInfo).getCode())) {
            return resultInfo;
        }

        //查询用户是否存在
        User user = userService.getUserByUserId(authenticationBo.getUserId());
        if (null == user) {
            resultInfo.setMessage("该用户不存在！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        //查询是否已经实名认证过
        Authentication checkExistence = authenticationService.selectByIdCard(authenticationBo.getIdCard());
        if (null != checkExistence) {
            resultInfo.setMessage("该身份证号已经实名认证过了！");
            resultInfo.setCode("10010");
            return resultInfo;
        }

        Authentication authentication = new Authentication();

        BeanUtils.copyProperties(authenticationBo, authentication);
        //审核通过
        authentication.setActivated(1);
        authentication.setCreatorId(authenticationBo.getUserId());

        int count;
        try {
            log.info("实名认证开始，实名信息为：{}", JSON.toJSONString(authentication));
            count = authenticationService.addAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("实名认证异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 0) {
            resultInfo.setMessage("实名认证失败！");
            resultInfo.setData(count);
            return resultInfo;
        }
        try {
            // 1主办方认证 2 技术人员认证
            if (1 == authenticationBo.getAuthenticationType()) {
                log.info("实名认证成功，开始修改主办方信息");
                Company company = companyService.getCompany(authenticationBo.getRelationId());
                company.setIsAuthentication(1);
                companyService.modifyCompany(company);
                resultInfo.setData(company);
            } else {
                log.info("实名认证成功，开始修改用户信息");
                //修改实名认证状态
                user.setIsAuthentication(1);
                user.setRealName(authenticationBo.getRealName());
                user.setNickName(authenticationBo.getRealName());
                user.setGender(authenticationBo.getSex());
                userService.modifyUser(user);
                resultInfo.setData(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改相关信息异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("实名认证通过！");
        return resultInfo;
    }

    /**
     * 修改实名认证信息
     *
     * @param authentication 要修改的实名认证信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/modifyAuthentication")
    public ResultInfo modifyAuthentication(@RequestBody Authentication authentication) {
        ResultInfo resultInfo = new ResultInfo();

        if (null == authentication.getAuthenticationId()) {
            resultInfo.setMessage("实名认证id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {
            count = authenticationService.modifyAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改实名认证异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("修改实名认证失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改实名认证成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 删除实名认证
     *
     * @param authenticationId 实名认证id
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/deleteAuthentication")
    public ResultInfo deleteAuthentication(@RequestParam("authenticationId") Integer authenticationId) {
        ResultInfo resultInfo = new ResultInfo();

        int count;
        try {
            count = authenticationService.delAuthentication(authenticationId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("删除实名认证异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count > 0) {
            resultInfo.setMessage("删除实名认证成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("删除实名认证失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 查看实名认证详情
     *
     * @param authenticationId 实名认证id
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/getAuthenticationById")
    public ResultInfo getAuthenticationById(@RequestParam("authenticationId") Integer authenticationId) {
        ResultInfo resultInfo = new ResultInfo();


        Authentication authentication;
        try {
            authentication = authenticationService.getAuthenticationByAuthenticationId(authenticationId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("获取实名认证信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (authentication == null) {
            resultInfo.setMessage("实名认证信息不存在！");
            resultInfo.setData(new Authentication());
            resultInfo.setTotal(0);
            return resultInfo;
        }
        resultInfo.setData(authentication);
        resultInfo.setMessage("获取实名认证成功！");
        return resultInfo;
    }

    /**
     * 获取实名认证列表
     *
     * @param pageNum  页数
     * @param pageSize 页大小
     * @param realName 真实姓名
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/getAuthenticationList")
    public ResultInfo getAuthenticationList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "realName", required = false) String realName) {
        ResultInfo resultInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);


        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

        if (realName != null && !"".equals(realName)) {
            params.put("realName", realName);
        }

        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = authenticationService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取实名认证总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("实名认证总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }

        List<Authentication> authenticationList;
        try {
            authenticationList = authenticationService.getAuthenticationList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取实名认证列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (authenticationList.size() == 0) {
            resultInfo.setMessage("实名认证列表为空！");
            resultInfo.setData(new ArrayList<Authentication>());
            return resultInfo;
        }
        resultInfo.setData(authenticationList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取实名认证列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除实名认证
     *
     * @param authenticationIds 实名认证id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/authentication/batchDeleteAuthentication")
    public ResultInfo deleteBatch(@RequestParam("id") String authenticationIds) {
        ResultInfo resultInfo = new ResultInfo();

        if (StringUtil.isEmpty(authenticationIds)) {
            resultInfo.setMessage("实名认证id不能为空");
            resultInfo.setCode("100002");
            return resultInfo;
        }

        int count;
        try {
            count = authenticationService.deleteBatch(authenticationIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("批量删除异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count > 0) {
            resultInfo.setMessage("批量删除实名信息成功！");
            resultInfo.setData(count);
        } else {
            resultInfo.setMessage("批量删除实名信息失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        return resultInfo;
    }
}
