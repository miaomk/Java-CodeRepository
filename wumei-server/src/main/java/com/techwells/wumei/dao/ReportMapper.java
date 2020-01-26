package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Report;
import com.techwells.wumei.util.PagingTool;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Report> selectReportList(PagingTool pagingTool);
  	
}