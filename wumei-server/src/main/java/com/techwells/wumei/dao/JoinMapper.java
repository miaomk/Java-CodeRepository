package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Join;
import com.techwells.wumei.domain.rs.RsJoin;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface JoinMapper {
    int deleteByPrimaryKey(Integer joinId);

    int insert(Join record);

    int insertSelective(Join record);

    Join selectByPrimaryKey(Integer joinId);

    int updateByPrimaryKeySelective(Join record);

    int updateByPrimaryKey(Join record);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<RsJoin> selectJoinList(PagingTool pagingTool);

    int batchDelete(@Param("ids") String[] ids);

    RsJoin selectJoinById(Integer joinId);

    /**
     * 分页获取商城首页拼团采购列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsJoin> getHomeJoinList(PagingTool pagingTool);
}