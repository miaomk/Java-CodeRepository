package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Feedback;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer feedbackId);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Feedback> selectFeedbackList(PagingTool pagingTool);
  	
  	
	int batchDelete(@Param("ids")String[] ids);

  	
}