package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Score;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer scoreId);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer scoreId);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Score> selectScoreList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);
}