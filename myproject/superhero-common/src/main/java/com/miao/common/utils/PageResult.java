package com.miao.common.utils;

import lombok.Data;

import java.util.List;

/**
 * 分页结果集
 *
 * @author miao
 */
@Data
public class PageResult<T> {

    private Long total;
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
