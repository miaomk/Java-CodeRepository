package com.techwells.wumei.service;

import com.techwells.wumei.domain.Feedback;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface FeedbackService {

	// 增删改查
	public int addFeedback(Feedback feedback);

	public int delFeedback(int feedbackId);

	public int modifyFeedback(Feedback feedback);

	Feedback getFeedbackByFeedbackId(int feedbackId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Feedback> getFeedbackList(PagingTool pagingTool);

	// 批量删除
	int batchDelete(String[] ids);
}
