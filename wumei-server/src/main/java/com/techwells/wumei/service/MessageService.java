package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Message;
import com.techwells.wumei.util.PagingTool;

public interface MessageService {

	// 增删改查
	public int addMessage(Message message);

	public int delMessage(int messageId);

	public int modifyMessage(Message message);

	Message getMessageByMessageId(int messageId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Message> getMessageList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
