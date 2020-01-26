package com.techwells.wumei.util;

import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.TechnologyCase;
import com.techwells.wumei.domain.TechnologyOrder;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.techwells.wumei.util.ConstantUtil.*;

/**
 * 参数校验工具类
 *
 * @author miao
 */
public class ParamCheckUtil {


    /**
     * 大师信息校验
     *
     * @param technologyBo 大师信息
     * @param resultInfo   结果集
     * @return ResultInfo
     */
    public static ResultInfo technologyBoCheck(TechnologyBO technologyBo, ResultInfo resultInfo) {
        //参数校验
        if (null == technologyBo) {

            resultInfo.setMessage("大师信息不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyBo.getUserId()) {

            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyBo.getTechnologyOccupation()) {

            resultInfo.setMessage("请选择您的职业！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(technologyBo.getMobile()) || technologyBo.getMobile().length() < MOBILELENGTH) {

            resultInfo.setMessage("请填写正确手机号！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyBo.getWorkExperience()) {

            resultInfo.setMessage("您的工作年长不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyBo.getTechnologyAge()) {

            resultInfo.setMessage("您的年龄不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (technologyBo.getTechnologyAge() == 0 || technologyBo.getTechnologyAge() >= AGEMAX) {
            resultInfo.setMessage("您的年龄不正确！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (null == technologyBo.getSalary()) {

            resultInfo.setMessage("请填写您的您的薪资！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyBo.getCity()) {

            resultInfo.setMessage("请填写您的所在城市！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (CollectionUtils.isNotEmpty(technologyBo.getCaseList())) {

            for (TechnologyCaseBO technologyCase : technologyBo.getCaseList()) {
                if (!SUCCESSCODE.equals(caseCheck(technologyCase, resultInfo).getCode())) {
                    break;
                }
            }
        }
        return resultInfo;
    }

    /**
     * 大师用户信息校验
     *
     * @param user       用户信息
     * @param resultInfo 结果集
     * @return ResultInfo
     */
    public static ResultInfo technologyUserCheck(User user, ResultInfo resultInfo) {
        //参数校验
        if (null == user) {
            resultInfo.setMessage("该用户不存在！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (ISAUTHENCATION == user.getIsAuthentication()) {
            resultInfo.setMessage("您尚未实名，请先实名再进行大师申请！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(user.getUserIcon())) {
            resultInfo.setMessage("请完善您的头像信息！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (COMPANYTYPE == user.getUserType()) {
            resultInfo.setMessage("您已经是主办方身份，不能申请成为大师！");
            resultInfo.setCode("10001");
            return resultInfo;
        } else if (TECHNOLOGYTYPE == user.getUserType()) {
            resultInfo.setMessage("您已经是大师身份，请勿重复申请！");
            resultInfo.setCode("50000");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 主办方用户信息校验
     *
     * @param user       用户信息
     * @param resultInfo 结果集
     * @return ResultInfo
     */
    public static ResultInfo companyUserCheck(User user, ResultInfo resultInfo) {
        //参数校验
        if (null == user) {
            resultInfo.setMessage("该用户不存在！");
            resultInfo.setCode("10004");
            return resultInfo;
        }

        if (COMPANYTYPE == user.getUserType()) {
            resultInfo.setMessage("您已经申请过主办方认证了，请不要重复认证！");
            resultInfo.setCode("10006");
            return resultInfo;
        } else if (TECHNOLOGYTYPE == user.getUserType()) {
            resultInfo.setMessage("已经是技术人员身份不能认证为主办方！");
            resultInfo.setCode("10005");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 大师修改信息校验
     *
     * @param technology 用户信息
     * @param resultInfo 结果集
     * @return ResultInfo
     */
    public static ResultInfo modifyTechnologyCheck(Technology technology, ResultInfo resultInfo) {
        //参数校验
        if (null == technology.getUserId()) {
            resultInfo.setMessage("大师信息错误！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null != technology.getTechnologyAge()) {
            if (technology.getTechnologyAge() == 0 || technology.getTechnologyAge() > AGEMAX) {
                resultInfo.setMessage("大师年龄信息错误！");
                resultInfo.setCode("10002");
                return resultInfo;
            }
        }
        return resultInfo;
    }

    /**
     * 工作案例信息校验
     *
     * @param technologyCase 工作案例信息
     * @param resultInfo     结果集
     * @return ResultInfo
     */
    public static ResultInfo technologyCaseCheck(TechnologyCase technologyCase, ResultInfo resultInfo) {
        if (StringUtil.isEmpty(technologyCase.getCaseName())) {
            resultInfo.setMessage("请填写案例名称！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (StringUtil.isEmpty(technologyCase.getImageUrl())) {
            resultInfo.setMessage("请填写案例图片！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (null == technologyCase.getTechnologyId()) {
            resultInfo.setMessage("请填写用户id！");
            resultInfo.setCode("10003");
            return resultInfo;
        }
        if (StringUtil.isEmpty(technologyCase.getCaseCity())) {
            resultInfo.setMessage("请填写案例服务城市！");
            resultInfo.setCode("10004");
            return resultInfo;
        }
        if (null == technologyCase.getCaseTime()) {
            resultInfo.setMessage("请填写案例服务时间！");
            resultInfo.setCode("10005");
            return resultInfo;
        }
        if (StringUtil.isEmpty(technologyCase.getCaseIntroduce())) {
            resultInfo.setMessage("请填写案例介绍！");
            resultInfo.setCode("10006");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 大师下单订单信息校验
     *
     * @param technologyOrder 大师订单信息
     * @param resultInfo      结果集
     * @return ResultInfo
     */
    public static ResultInfo technologyOrderCheck(TechnologyOrder technologyOrder, ResultInfo resultInfo) {
        if (null == technologyOrder.getUserId()) {
            resultInfo.setMessage("下单用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyOrder.getTechnologyId()) {
            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (technologyOrder.getTechnologyId().equals(technologyOrder.getUserId())) {
            resultInfo.setMessage("您无法给自己下单！");
            resultInfo.setCode("10003");
            return resultInfo;
        }
        if (null == technologyOrder.getStartDate()) {
            resultInfo.setMessage("开始日期不能为空！");
            resultInfo.setCode("10004");
            return resultInfo;
        }
        if (null == technologyOrder.getEndDate()) {
            resultInfo.setMessage("结束日期不能为空！");
            resultInfo.setCode("10005");
            return resultInfo;
        }
        if (technologyOrder.getStartDate().isBefore(LocalDate.now()) ||
                technologyOrder.getEndDate().isBefore(LocalDate.now())) {
            resultInfo.setMessage("时间错误，不能早于今天！");
            resultInfo.setCode("10006");
            return resultInfo;
        }
        if (technologyOrder.getEndDate().isBefore(technologyOrder.getStartDate())) {
            resultInfo.setMessage("开始时间不能晚于结束日期！");
            resultInfo.setCode("10007");
            return resultInfo;
        }
        if (technologyOrder.getEndDate().equals(LocalDate.now()) || technologyOrder.getEndDate().equals(LocalDate.now())) {
            resultInfo.setMessage("时间不能是今天！");
            resultInfo.setCode("10008");
            return resultInfo;
        }

        if (null == technologyOrder.getTotalPrice() || technologyOrder.getTotalPrice() == 0) {
            resultInfo.setMessage("金额错误！");
            resultInfo.setCode("10009");
            return resultInfo;
        }
        if (null == technologyOrder.getAmount() || technologyOrder.getAmount() == 0) {
            resultInfo.setMessage("聘用天数错误！");
            resultInfo.setCode("10010");
            return resultInfo;
        }
        return resultInfo;
    }

    /**
     * 客户评价信息校验
     *
     * @param technologyEvaluateBo 客户评价信息
     * @param resultInfo           结果集
     * @return ResultInfo
     */
    public static ResultInfo technologyEvaluateBoCheck(TechnologyEvaluateBO technologyEvaluateBo, ResultInfo resultInfo) {
        if (null == technologyEvaluateBo.getUserId()) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == technologyEvaluateBo.getTechnologyId()) {
            resultInfo.setMessage("大师id不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (null == technologyEvaluateBo.getDemandId()) {
            resultInfo.setMessage("需求id不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        }

        return resultInfo;
    }

    /**
     * 工作案例信息校验
     *
     * @param technologyCaseBo 工作案例信息
     * @param resultInfo       结果集
     * @return ResultInfo
     */
    private static ResultInfo caseCheck(TechnologyCaseBO technologyCaseBo, ResultInfo resultInfo) {
        TechnologyCase technologyCase = new TechnologyCase();

        if (null == technologyCaseBo.getImageUrlArray() || technologyCaseBo.getImageUrlArray().length == 0) {
            resultInfo.setMessage("请上传案例图片！");
            resultInfo.setCode("10007");
            return resultInfo;
        }
        StringBuilder imageUrlBuilder = new StringBuilder();
        for (String imageUrl : technologyCaseBo.getImageUrlArray()) {
            imageUrlBuilder.append(imageUrl).append(",");
        }
        BeanUtils.copyProperties(technologyCaseBo, technologyCase);
        technologyCase.setImageUrl(imageUrlBuilder.toString().substring(0, imageUrlBuilder.length() - 1));


        return technologyCaseCheck(technologyCase, resultInfo);
    }

    /**
     * 实名信息校验
     *
     * @param authenticationBo 实名信息
     * @param resultInfo       结果集
     * @return ResultInfo
     */
    public static ResultInfo authenticationBoCheck(AuthenticationBO authenticationBo, ResultInfo resultInfo) {
        if (StringUtils.isBlank(authenticationBo.getRealName())) {
            resultInfo.setMessage("姓名不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (authenticationBo.getRealName().length() < NAMELENGTH) {
            resultInfo.setMessage("姓名不规范！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (!StringUtil.isChinese(authenticationBo.getRealName()) || StringUtil.isSpecialChar(authenticationBo.getRealName())) {
            resultInfo.setMessage("请填写正确的姓名！");
            resultInfo.setCode("10002");
            return resultInfo;
        }

        if (StringUtils.isBlank(authenticationBo.getIdCard())) {
            resultInfo.setMessage("身份证号不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (!StringUtil.isIDCard(authenticationBo.getIdCard())) {
            resultInfo.setMessage("身份证号码不规范！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == authenticationBo.getRelationId()) {
            resultInfo.setMessage("实名认证id不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == authenticationBo.getAuthenticationType()) {
            resultInfo.setMessage("实名认证类型不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtils.isBlank(authenticationBo.getSex())) {
            resultInfo.setMessage("性别不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        return resultInfo;
    }


    /**
     * 发布信息信息校验
     *
     * @param demandBo   实名信息
     * @param resultInfo 结果集
     * @return ResultInfo
     */
    public static ResultInfo demandBoCheck(DemandBO demandBo, ResultInfo resultInfo) {
        if (StringUtils.isBlank(demandBo.getDemandTitle())) {
            resultInfo.setMessage("需求标题不能为空,请填写需求标题！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (null == demandBo.getUserId()) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == demandBo.getStartTime()) {
            resultInfo.setMessage("开始时间不能为空,请选择开始时间！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null == demandBo.getEndTime()) {
            resultInfo.setMessage("结束时间不能为空,请选择结束时间！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (demandBo.getStartTime().isAfter(demandBo.getEndTime())) {
            resultInfo.setMessage("开始时间不能晚于结束时间,请选择正确时间段！");
            resultInfo.setCode("10003");
            return resultInfo;
        }

        if (StringUtils.isBlank(demandBo.getDemandLocation())) {
            resultInfo.setMessage("城市不能为空,请选择城市！");
            resultInfo.setCode("10004");
            return resultInfo;
        }
        if (null == demandBo.getTechnologyType()) {
            resultInfo.setMessage("请选择所需工种！");
            resultInfo.setCode("10005");
            return resultInfo;
        }
        if (null == demandBo.getSalary() || BigDecimal.ZERO.equals(demandBo.getSalary())) {
            resultInfo.setMessage("薪资不正确！");
            resultInfo.setCode("10006");
            return resultInfo;
        }
        if (StringUtils.isBlank(demandBo.getContacts())) {
            resultInfo.setMessage("请填写联系人姓名！");
            resultInfo.setCode("10007");
            return resultInfo;
        }
        if (!StringUtil.isChinese(demandBo.getContacts()) || StringUtil.isSpecialChar(demandBo.getContacts())) {
            resultInfo.setMessage("联系人姓名格式不正确！");
            resultInfo.setCode("10008");
            return resultInfo;
        }
        if (StringUtils.isBlank(demandBo.getContact())) {
            resultInfo.setMessage("请填写手机号！");
            resultInfo.setCode("10009");
            return resultInfo;
        }
        if (!StringUtil.isMobile(demandBo.getContact())) {
            resultInfo.setMessage("请填写正确手机号！");
            resultInfo.setCode("10010");
            return resultInfo;
        }
        return resultInfo;
    }


    /**
     * 分页参数校验
     *
     * @param pageNum    页数
     * @param pageSize   页大小
     * @param resultInfo 结果集
     * @return ResultInfo
     */
    public static ResultInfo pagingParamsCheck(Integer pageNum, Integer pageSize, ResultInfo resultInfo) {
        if (null == pageNum) {
            resultInfo.setMessage("页码不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (null == pageSize) {
            resultInfo.setMessage("页大小不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        return resultInfo;
    }
}
