package com.techwells.wumei.service;


import com.techwells.wumei.domain.Ticket;

/**
 * @author miao
 */
public interface TicketService {
    /**
     * 增加活动票
     * @param ticket 活动票信息
     * @return int
     */
    int addTicket(Ticket ticket);
}
