package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Report;
import com.techwells.wumei.util.PagingTool;

public interface ReportService {

	// 增删改查
	public int addReport(Report report);

	public int delReport(int reportId);

	public int modifyReport(Report report);

	Report getReportByReportId(int reportId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Report> getReportList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
