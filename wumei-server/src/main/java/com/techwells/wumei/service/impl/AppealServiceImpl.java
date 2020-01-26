package com.techwells.wumei.service.impl;

import java.util.List;

import com.techwells.wumei.domain.rs.RsAppeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.AppealMapper;
import com.techwells.wumei.domain.Appeal;
import com.techwells.wumei.service.AppealService;
import com.techwells.wumei.util.PagingTool;


@Service("AppealService")
public class AppealServiceImpl implements AppealService {

	private AppealMapper appealMapper;

	public AppealMapper getAppealMapper() {
		return appealMapper;
	}

	@Autowired
	public void setAppealMapper(AppealMapper appealMapper) {
		this.appealMapper = appealMapper;
	}

	@Override
	public int addAppeal(Appeal appeal) {
		int count = 0;
		try {
			count = appealMapper.insertSelective(appeal);
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
	public int delAppeal(int appealId) {
		int count = 0;
		try {
			count = appealMapper.deleteByPrimaryKey(appealId);
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
	public int modifyAppeal(Appeal appeal) {
		int count = 0;
		try {
			count = appealMapper.updateByPrimaryKeySelective(appeal);
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
	public Appeal getAppealByAppealId(int appealId) {
		Appeal appeal = null;
		try {
			appeal = appealMapper.selectByPrimaryKey(appealId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return appeal;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = appealMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<RsAppeal> getAppealList(PagingTool pagingTool) {
		List<RsAppeal> appealList = null;

		try {
			appealList = appealMapper.selectAppealList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return appealList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		int count;
		try {
			count = appealMapper.batchDelete(idArr);
			if (count < 0) {
				throw new Exception("批量操作失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("批量操作异常！");
		}
		return count;
	}

}
