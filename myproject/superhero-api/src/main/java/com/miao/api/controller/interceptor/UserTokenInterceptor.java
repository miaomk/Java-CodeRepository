package com.miao.api.controller.interceptor;

import com.miao.common.utils.JsonUtils;
import com.miao.common.utils.ResultInfo;
import com.miao.common.utils.redis.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * token拦截器
 *
 * @author miao
 */
public class UserTokenInterceptor implements HandlerInterceptor {
    @Resource
    public RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis-user-token";

    /**
     * 拦截请求，发生在调用controller之前
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return Boolean{
     * 返回 false：请求被拦截
     * 返回 true：请求放行
     * }
     * @throws Exception 异常处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前端返回用户id
        String headerUserId = request.getHeader("headerUserId");
        //前端返回用户token
        String headerUserToken = request.getHeader("headerUserToken");

        if (StringUtils.isNotBlank(headerUserId) && StringUtils.isNotBlank(headerUserToken)) {
            String redisUserToken = redisOperator.get(REDIS_USER_TOKEN + "：" + headerUserId);
            //如果redis中没有token，用户已经登出或者不小心被误删
            if (StringUtils.isBlank(redisUserToken)) {
                returnErrorResponse(response, new ResultInfo().paramsFailure("用户会话过期，请重新登录..."));
                return false;
            } else if (!redisUserToken.equals(headerUserToken)) {
                returnErrorResponse(response, new ResultInfo().paramsFailure("用户已在别地登录"));
                return false;
            }

        } else {
            returnErrorResponse(response, new ResultInfo().paramsFailure("请登录"));
            return false;
        }

        return true;
    }

    /**
     * 构建一个返回json的方法
     */
    public void returnErrorResponse(HttpServletResponse response,
                                    ResultInfo result) throws Exception {

        OutputStream out = null;

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * 发生在调用controller之后，渲染页面之前
     *
     * @param request      请求
     * @param response     响应
     * @param handler      处理器
     * @param modelAndView 视图
     * @throws Exception 异常处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 发生在调用controller之后，渲染页面之后
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @param ex       异常对象
     * @throws Exception 异常处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
