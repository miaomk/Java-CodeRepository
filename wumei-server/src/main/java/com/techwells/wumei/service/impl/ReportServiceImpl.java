package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ReportMapper;
import com.techwells.wumei.domain.Report;
import com.techwells.wumei.service.ReportService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("ReportService")
public class ReportServiceImpl implements ReportService {

	private ReportMapper reportMapper;

	public ReportMapper getReportMapper() {
		return reportMapper;
	}

	@Resource
	public void setReportMapper(ReportMapper reportMapper) {
		this.reportMapper = reportMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addReport(Report report) {
		int count;
		try {
			count = reportMapper.insertSelective(report);
			if (count < 0) {
				throw new Exception("举报失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("举报异常！");
		}
		return count;
	}

	@Override
	public int delReport(int reportId) {
		int count = 0;
		try {
			count = reportMapper.deleteByPrimaryKey(reportId);
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
	public int modifyReport(Report report) {
		int count = 0;
		try {
			count = reportMapper.updateByPrimaryKeySelective(report);
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
	public Report getReportByReportId(int reportId) {
		Report report = null;
		try {
			report = reportMapper.selectByPrimaryKey(reportId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return report;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = reportMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Report> getReportList(PagingTool pagingTool) {
		List<Report> reportList = null;

		try {
			reportList = reportMapper.selectReportList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return reportList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
