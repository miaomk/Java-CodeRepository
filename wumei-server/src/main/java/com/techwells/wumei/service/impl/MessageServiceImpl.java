package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.MessageMapper;
import com.techwells.wumei.domain.Message;
import com.techwells.wumei.service.MessageService;
import com.techwells.wumei.util.PagingTool;

import javax.annotation.Resource;


@Service("MessageService")
public class MessageServiceImpl implements MessageService {
	@Resource
	private MessageMapper messageMapper;

	@Override
	public int addMessage(Message message) {
		int count;
		try {
			count = messageMapper.insertSelective(message);
			if (count < 0) {
				throw new Exception("添加通知消息失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加通知消息异常！");
		}
		return count;
	}

	@Override
	public int delMessage(int messageId) {
		int count;
		try {
			count = messageMapper.deleteByPrimaryKey(messageId);
			if (count < 0) {
				throw new Exception("删除通知消息失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除通知消息异常！");
		}
		return count;
	}

	@Override
	public int modifyMessage(Message message) {
		int count;
		try {
			count = messageMapper.updateByPrimaryKeySelective(message);
			if (count < 0) {
				throw new Exception("修改通知消息失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改通知消息异常！");
		}
		return count;
	}

	@Override
	public Message getMessageByMessageId(int messageId) {
		Message message;
		try {
			message = messageMapper.selectByPrimaryKey(messageId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取通知消息详情异常！");
		}
		return message;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = messageMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取通知消息总数异常！");
		}
		return count;
	}

	@Override
	public List<Message> getMessageList(PagingTool pagingTool) {
		List<Message> messageList;

		try {
			messageList = messageMapper.selectMessageList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取通知消息列表异常");
		}
		return messageList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
