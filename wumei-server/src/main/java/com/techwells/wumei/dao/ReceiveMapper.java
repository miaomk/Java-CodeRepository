package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Receive;
import com.techwells.wumei.domain.rs.RsReceive;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ReceiveMapper {
    int deleteByPrimaryKey(Integer receiveId);

    int insert(Receive record);

    int insertSelective(Receive record);

    Receive selectByPrimaryKey(Integer receiveId);

    int updateByPrimaryKeySelective(Receive record);

    int updateByPrimaryKey(Receive record);
    
    // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<RsReceive> selectReceiveList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
 	
 	int countUserAndCoupon(@Param("userId") int userId,@Param("couponId") int couponId);
    /**
     * 分页获取我的优惠券列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int myReceiveCount(PagingTool pagingTool);
    /**
     * 分页获取我的优惠券列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsReceive> getMyReceiveList(PagingTool pagingTool);

    /**
     * 批量更新已经过期的优惠券列表
     *
     * @param couponList 过去的优惠券列表
     */
    int batchUpdateReceiveStatus(List<Coupon> couponList);
}