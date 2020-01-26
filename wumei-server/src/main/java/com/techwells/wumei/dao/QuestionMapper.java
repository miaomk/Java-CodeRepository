package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Question;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Question> selectQuestionList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
}