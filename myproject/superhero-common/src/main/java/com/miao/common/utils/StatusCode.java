package com.miao.common.utils;

/**
 * 返回结果集
 * 200 成功
 * 500 表示错误，信息存在msg字段中
 * 501 bean验证错误，不管出现多少个错误都以map形式返回
 * 502 拦截器拦截到用户token出错
 * 555 异常抛出信息
 * <p>
 * 状态码实体类
 *
 * @author miao
 */
public class StatusCode {
    /**
     * 成功
     */
    public static final int OK = 200;
    /**
     * 失败
     */
    public static final int ERROR = 20001;
    /**
     * 用户名或密码错误
     */
    public static final int LOGINERROR = 500;
    /**
     * 权限不足
     */
    public static final int ACCESSERROR = 20003;
    /**
     * 远程调用失败
     */
    public static final int REMOTEERROR = 20004;
    /**
     * 重复操作
     */
    public static final int REPERROR = 20005;

    /**
     * 参数错误
     */
    public static final int PARAMSERROR = 20006;
}
