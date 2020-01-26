package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TicketMapper;
import com.techwells.wumei.domain.Ticket;
import com.techwells.wumei.service.TicketService;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 活动票实现类
 * @author miao
 */
@Service("TicketService")
public class TicketServiceImpl implements TicketService {

    @Resource
    private TicketMapper ticketMapper;

    @Override
    public int addTicket(Ticket ticket) {
        int count;
        try{
            count = ticketMapper.insertSelective(ticket);
            if (count < 0) {
                throw new Exception("添加活动票失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationContextException("增加活动票失败");
        }
        return count;
    }
}
