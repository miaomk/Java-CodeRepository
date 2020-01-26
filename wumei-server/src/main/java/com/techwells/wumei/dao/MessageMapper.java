package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Message;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Message> selectMessageList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}