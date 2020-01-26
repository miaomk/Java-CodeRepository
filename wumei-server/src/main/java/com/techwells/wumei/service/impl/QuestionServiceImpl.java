package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.QuestionMapper;
import com.techwells.wumei.domain.Question;
import com.techwells.wumei.service.QuestionService;
import com.techwells.wumei.util.PagingTool;


@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {

	private QuestionMapper questionMapper;

	public QuestionMapper getQuestionMapper() {
		return questionMapper;
	}

	@Autowired
	public void setQuestionMapper(QuestionMapper questionMapper) {
		this.questionMapper = questionMapper;
	}

	@Override
	public int addQuestion(Question question) {
		int count;
		try {
			count = questionMapper.insertSelective(question);
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
	public int delQuestion(int questionId) {
		int count;
		try {
			count = questionMapper.deleteByPrimaryKey(questionId);
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
	public int modifyQuestion(Question question) {
		int count;
		try {
			count = questionMapper.updateByPrimaryKeySelective(question);
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
	public Question getQuestionByQuestionId(int questionId) {
		Question question = null;
		try {
			question = questionMapper.selectByPrimaryKey(questionId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return question;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = questionMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Question> getQuestionList(PagingTool pagingTool) {
		List<Question> questionList;

		try {
			questionList = questionMapper.selectQuestionList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return questionList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
