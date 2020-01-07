package com.miao.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 返回结果集
 *
 * @author miao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultInfo {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 状态码
     */
    private Integer code = StatusCode.OK;
    /**
     * 相应信息
     */
    private String msg;
    /**
     * 相应数据
     */
    private Object data;

    /**
     * 响应成功
     *
     * @param data 数据
     * @param msg  信息
     * @return ResultInfo
     */
    public static ResultInfo success(Object data, String msg) {

        return new ResultInfo().setData(data).setMsg(msg);
    }

    /**
     * 响应成功
     *
     * @param msg 信息
     * @return ResultInfo
     */
    public static ResultInfo success(String msg) {

        return new ResultInfo().setMsg(msg);
    }

    /**
     * 失败方法
     *
     * @param code 状态码
     * @param msg  信息
     * @return ResultInfo
     */
    public static ResultInfo failure(Integer code, String msg) {


        return new ResultInfo().setCode(code).setMsg(msg);
    }

    /**
     * 缺少参数
     *
     * @param msg 错误信息
     * @return ResultInfo
     */
    public static ResultInfo failureParams(String msg) {


        return new ResultInfo().setCode(StatusCode.PARAMSERROR).setMsg(msg);
    }


    /**
     * 参数错误
     *
     * @param msg 信息
     * @return ResultInfo
     */
    public static ResultInfo paramsError(String msg) {


        return new ResultInfo().setCode(StatusCode.PARAMSERROR).setMsg(msg);
    }


    /**
     * 参数错误
     *
     * @param msg 信息
     * @return ResultInfo
     */
    public ResultInfo paramsFailure(String msg) {


        return new ResultInfo().setCode(502).setMsg(msg);
    }

}
