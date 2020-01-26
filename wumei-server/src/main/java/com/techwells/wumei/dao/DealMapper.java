package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Deal;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface DealMapper {
    int deleteByPrimaryKey(Integer dealId);

    int insert(Deal record);

    int insertSelective(Deal record);

    Deal selectByPrimaryKey(Integer dealId);

    int updateByPrimaryKeySelective(Deal record);

    int updateByPrimaryKey(Deal record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Deal> selectDealList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);

    /**
     * 批量更新交易订单
     *
     * @param updateList 交易订单List
     * @return int
     */
    int batchUpdateOrder(List<Deal> updateList);
}