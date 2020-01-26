package com.techwells.wumei.util;


import lombok.Data;

/**
 * 统一结果集
 *
 * @author Administrator
 */
@Data
public class ResultInfo {

    /**
     * 状态码   200 表示成功
     */
    private String code = "200";
    /**
     * 提示信息
     */
    private String message = "";
    /**
     * 查询到的总数
     */
    private int total = 0;
    /**
     * 封装结果集
     */
    private Object data;
}
