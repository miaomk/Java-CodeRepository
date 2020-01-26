package com.techwells.wumei.dao;

import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer ticketId);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer ticketId);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    /**
     * 通过活动id删除门票
     *
     * @param activityId 活动id
     * @return int
     */
    int deleteByActivityId(@Param("activityId") Integer activityId);

    /**
     * 批量通过活动id删除门票
     *
     * @param activityIds 活动id数组
     * @return int
     */
    int batchDeleteByActivityId(@Param("ids") String[] activityIds);

    /**
     * 通过活动id查询门票规则
     *
     * @param activityId 活动id
     * @return Ticket
     */
    Ticket selectByActivityId(@Param("activityId") Integer activityId);

    /**
     * 查询付费活动列表的信息
     *
     * @param payList 付费活动列表
     * @return List
     */
    List<RsActivityManage> getActivityTicketFee(@Param("activityIds") List<Integer> payList);

    /**
     * 通过活动id查询所有门票规则
     *
     * @param activityId 活动id
     * @return List
     */
    List<Ticket> selectTicketList(@Param("activityId") String activityId);

    /**
     * 通过活动id查询票种信息
     *
     * @param activityId 活动id
     * @return Ticket
     */
    Ticket getTicketInfoByActivity(@Param("activityId") String activityId);
}