package com.techwells.wumei.service;

import com.techwells.wumei.domain.PV;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface PvService {
    /**
     * 增加浏览量
     *
     * @param pv pv信息
     * @return int
     */
    int addPv(PV pv);

    /**
     * 获取pv总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);
}
