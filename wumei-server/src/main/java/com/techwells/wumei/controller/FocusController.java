package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Focus;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.RsFocus;
import com.techwells.wumei.service.FocusService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miao
 */
@Slf4j
@Controller
@RequestMapping(value = "/focus")
public class FocusController {

    @Resource
    private UserService userService;
    @Resource
    private FocusService focusService;


    @RequestMapping(value = "/addFocus")
    @ResponseBody
    public ResultInfo addFocus(@RequestParam(value = "userId") Integer userId,
                               @RequestParam(value = "relationId") Integer relationId,
                               @RequestParam(value = "focusType") Integer focusType) {
        ResultInfo resultInfo = new ResultInfo();

        if (userId == null) {
            resultInfo.setMessage("用户ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (focusType == null) {
            resultInfo.setMessage("关注类型不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (relationId == null) {
            resultInfo.setMessage("被关注者ID不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }

        User user = userService.getUserByUserId(userId);
        if (null == user) {
            resultInfo.setMessage("该用户不存在！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        Focus focus = new Focus();

        focus.setUserId(userId);
        focus.setRelationId(relationId);
        focus.setFocusType(focusType);
        Integer focusId;
        try {
            focusId = focusService.getFocusId(userId, relationId, focusType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("查询关注异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (null != focusId) {

            resultInfo.setMessage("您已经关注过了,请勿重复关注！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        int count;
        try {
            log.info("增加关注开始,关注人id:{},被关注人id:{},关注类型:{}", userId, relationId, focusType);
            count = focusService.addFocus(focus);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("关注异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("关注失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("关注完成");
        resultInfo.setMessage("关注成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    @RequestMapping(value = "/deleteFocus")
    @ResponseBody
    public ResultInfo deleteFocus(@RequestParam(value = "focusId") Integer focusId) {
        ResultInfo resultInfo = new ResultInfo();

        if (focusId == null) {
            resultInfo.setMessage("关注ID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count;
        try {

            log.info("取消关注开始,关注表id:{}", focusId);
            count = focusService.deleteFocus(focusId);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("取消关注异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("删除关注失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("删除关注完成");
        resultInfo.setMessage("删除关注成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    @RequestMapping(value = "/cancelFocus")
    @ResponseBody
    public ResultInfo cancelFocus(@RequestParam(value = "userId") Integer userId,
                                  @RequestParam(value = "relationId") Integer relationId,
                                  @RequestParam(value = "focusType") Integer focusType) {
        ResultInfo resultInfo = new ResultInfo();

        if (userId == null) {
            resultInfo.setMessage("用户ID不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (focusType == null) {
            resultInfo.setMessage("关注类型不能为空！");
            resultInfo.setCode("10002");
            return resultInfo;
        }
        if (relationId == null) {
            resultInfo.setMessage("被关注者ID不能为空！");
            resultInfo.setCode("10003");
            return resultInfo;
        }

        int count;
        try {

            log.info("取消关注开始,关注人id:{},被关注人id:{}，关注类型：{}", userId, relationId, focusType);
            count = focusService.cancelFocus(userId, relationId, focusType);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("取消关注异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (count < 1) {
            resultInfo.setMessage("取消关注失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        log.info("取消关注完成");
        resultInfo.setMessage("取消关注成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    @RequestMapping(value = "/getFocusList")
    public ResultInfo getFocusList(@RequestParam(value = "userId") Integer userId) {
        ResultInfo resultInfo = new ResultInfo();

        if (userId == null) {
            resultInfo.setMessage("用户id不能为空！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        List<RsFocus> focusList;
        try {
            focusList = focusService.getFocusCompanyList(userId);
        } catch (Exception e) {
            e.printStackTrace();

            resultInfo.setMessage("获取关注列表异常!");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (focusList.size() == 0) {

            resultInfo.setMessage("关注列表为空！");
            resultInfo.setData(new ArrayList<RsFocus>());
            return resultInfo;
        }

        resultInfo.setData(focusList);
        resultInfo.setTotal(focusList.size());
        resultInfo.setMessage("获取关注列表成功！");
        return resultInfo;
    }
}
