package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Question;
import com.techwells.wumei.util.PagingTool;

public interface QuestionService {

	// 增删改查
	public int addQuestion(Question question);

	public int delQuestion(int questionId);

	public int modifyQuestion(Question question);

	Question getQuestionByQuestionId(int questionId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Question> getQuestionList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
