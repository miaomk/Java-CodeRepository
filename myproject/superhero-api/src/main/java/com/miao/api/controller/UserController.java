package com.miao.api.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.miao.common.utils.*;
import com.miao.pojo.RegisterLoginUsersBO;
import com.miao.pojo.Users;
import com.miao.pojo.bo.MPWXUserBO;
import com.miao.pojo.bo.ModifiedUserBO;
import com.miao.pojo.vo.UsersVO;
import com.miao.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author miao
 */
@RestController
@Slf4j
@Api(value = "用户接口", tags = {"用户相关接口"})
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    private final static String USER_DEFAULT_FACE_IMAGE_URL = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    /**
     * 微信小程序登录
     *
     * @param code       code
     * @param mpwxUserBO 用户信息
     * @return ResultInfo
     */
    @RequestMapping(value = "mpLogin/{code}", method = RequestMethod.POST)
    @ApiOperation(value = "微信小程序登录接口", notes = "微信小程序登录接口", httpMethod = "POST")
    public ResultInfo mpLogin(@ApiParam(value = "前端传来的code")
                              @PathVariable(value = "code") String code,
                              @ApiParam(value = "用户信息", required = true)
                              @RequestBody MPWXUserBO mpwxUserBO) {

        String url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> map = new HashMap<>(16);
        map.put("appid", MPWXConfig.APPID);
        map.put("secret", MPWXConfig.SECRET);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");


        String wxResult = HttpClientUtil.doGet(url, map);
        String openid = (String) JSON.parseObject(wxResult).get("openid");

        if (StringUtil.isEmpty(openid)) {
            return ResultInfo.failure(StatusCode.REPERROR, "openId解析错误");
        }

        //根据openId查询用户是否已经存在,用户已存在则登录,不存在则注册
        Users userByOpenId = userService.getUserByOpenId(openid);
        UsersVO usersVO;
        if (null != userByOpenId) {
            usersVO = uniqueUserToken(userByOpenId);
            return ResultInfo.success(usersVO, "用户已经存在");
        } else {

            //使用idWork生成唯一主键
            String userId = sid.nextShort();

            //插入用户信息
            Users user = new Users();
            user.setId(userId);
            user.setMpWxOpenId(openid);
            user.setSex(mpwxUserBO.getGender());
            user.setNickname(mpwxUserBO.getNickName());
            user.setFaceImage(mpwxUserBO.getAvatarUrl());

            userService.insertUsers(user);

            //生成token
            usersVO = uniqueUserToken(user);

            return ResultInfo.success(usersVO, "登录成功");
        }
    }

    /**
     * 账号密码注册或登录
     *
     * @param registerLoginUsersBO 账号，密码
     * @return ResultInfo
     */
    @RequestMapping(value = "registerOrLogin", method = RequestMethod.POST)
    @ApiOperation(value = "账号密码注册或登录接口", notes = "用户使用账号密码注册或登录接口", httpMethod = "POST")
    public ResultInfo registerOrLogin(@RequestBody RegisterLoginUsersBO registerLoginUsersBO) {

        //1.判断用户名密码不能为空
        if (StringUtil.isEmpty(registerLoginUsersBO.getPassword()) || StringUtil.isEmpty(registerLoginUsersBO.getUsername())) {

            return ResultInfo.failure(StatusCode.PARAMSERROR, "账号密码不能为空");
        }

        //2.使用用户名查询用户是否存在
        Users userByUserName = userService.getUserByUserName(registerLoginUsersBO.getUsername());

        //如果用户存在则查看密码是否正确
        if (null != userByUserName) {
            //登录成功，生成token
            if (registerLoginUsersBO.getPassword().equals(userByUserName.getPassword())) {

                UsersVO usersVO = uniqueUserToken(userByUserName);
                return ResultInfo.success(usersVO, "登录成功");
            } else {

                return ResultInfo.failure(StatusCode.LOGINERROR, "账号或密码错误");
            }
        } else {
            //新增一个用户
            Users users = new Users();
            users.setId(sid.nextShort());
            users.setUsername(registerLoginUsersBO.getUsername());
            users.setFaceImage(USER_DEFAULT_FACE_IMAGE_URL);
            users.setPassword(registerLoginUsersBO.getPassword());

            userService.insertUsers(users);

            return ResultInfo.success(uniqueUserToken(users), "注册成功");
        }

    }

    /**
     * 用户退出登录
     *
     * @param userId 用户id
     * @return ResultInfo
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ApiOperation(value = "用户退出登录接口", notes = "用户退出登录，清除用户token", httpMethod = "POST")
    public ResultInfo logout(@ApiParam(name = "userId", value = "用户id", required = true)
                             @RequestParam(value = "userId") String userId) {

        //根据用户id查询用户信息
        Users userByUserId = userService.getUserByUserId(userId);
        //用户存在
        if (null != userByUserId) {
            //删除token
            redisOperator.del(REDIS_USER_TOKEN + "：" + userId);
            return ResultInfo.success("成功退出");

        } else {

            return ResultInfo.failure(StatusCode.PARAMSERROR, "无效的用户id");
        }

    }

    /**
     * 修改用户信息接口
     *
     * @param modifiedUserBO 要修改的用户信息
     * @return ResultInfo
     */
    @RequestMapping(value = "modifyUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息接口", notes = "修改用户信息", httpMethod = "POST")
    public ResultInfo modifyUser(@RequestBody ModifiedUserBO modifiedUserBO) {
        if (StringUtil.isEmpty(modifiedUserBO.getUserId())) {

            ResultInfo.paramsError("用户id不能为空");
        }
        Integer sex = modifiedUserBO.getSex();
        //改变sex赋值
        modifiedUserBO.setSex(sex != 0 ? sex : 2);
        //赋值
        Users users = new Users();
        BeanUtils.copyProperties(modifiedUserBO, users);
        users.setId(modifiedUserBO.getUserId());
        //更新
        userService.modifyUsers(users);
        //查询最新信息
        Users userByUserId = userService.getUserByUserId(users.getId());
        return ResultInfo.success(userByUserId, "修改用户信息成功！");
    }


}
