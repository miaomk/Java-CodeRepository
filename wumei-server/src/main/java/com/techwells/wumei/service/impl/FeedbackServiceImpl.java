package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.FeedbackMapper;
import com.techwells.wumei.domain.Feedback;
import com.techwells.wumei.service.FeedbackService;
import com.techwells.wumei.util.PagingTool;


@Service("FeedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	private FeedbackMapper feedbackMapper;

	public FeedbackMapper getFeedbackMapper() {
		return feedbackMapper;
	}

	@Autowired
	public void setFeedbackMapper(FeedbackMapper feedbackMapper) {
		this.feedbackMapper = feedbackMapper;
	}

	@Override
	public int addFeedback(Feedback feedback) {
		int count = 0;
		try {
			count = feedbackMapper.insertSelective(feedback);
			if (count < 0) {
				throw new Exception("添加模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加模板异常！");
		}
		return count;
	}

	@Override
	public int delFeedback(int feedbackId) {
		int count = 0;
		try {
			count = feedbackMapper.deleteByPrimaryKey(feedbackId);
			if (count < 0) {
				throw new Exception("删除模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除模板异常！");
		}
		return count;
	}

	@Override
	public int modifyFeedback(Feedback feedback) {
		int count = 0;
		try {
			count = feedbackMapper.updateByPrimaryKeySelective(feedback);
			if (count < 0) {
				throw new Exception("修改模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改模板异常！");
		}
		return count;
	}

	@Override
	public Feedback getFeedbackByFeedbackId(int feedbackId) {
		Feedback feedback = null;
		try {
			feedback = feedbackMapper.selectByPrimaryKey(feedbackId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return feedback;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = feedbackMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Feedback> getFeedbackList(PagingTool pagingTool) {
		List<Feedback> feedbackList = null;

		try {
			feedbackList = feedbackMapper.selectFeedbackList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return feedbackList;
	}

	@Override
	public int batchDelete(String[] ids) {
		int count = 0;
		try {
			count = feedbackMapper.batchDelete(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("批量删除异常");
		}
		return count;
	}

}
