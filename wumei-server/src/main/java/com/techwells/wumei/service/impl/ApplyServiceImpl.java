package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ApplyMapper;
import com.techwells.wumei.domain.Apply;
import com.techwells.wumei.service.ApplyService;
import com.techwells.wumei.util.PagingTool;


@Service("ApplyService")
public class ApplyServiceImpl implements ApplyService {

	private ApplyMapper applyMapper;

	public ApplyMapper getApplyMapper() {
		return applyMapper;
	}

	@Autowired
	public void setApplyMapper(ApplyMapper applyMapper) {
		this.applyMapper = applyMapper;
	}

	@Override
	public int addApply(Apply apply) {
		int count = 0;
		try {
			count = applyMapper.insertSelective(apply);
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
	public int delApply(int applyId) {
		int count = 0;
		try {
			count = applyMapper.deleteByPrimaryKey(applyId);
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
	public int modifyApply(Apply apply) {
		int count = 0;
		try {
			count = applyMapper.updateByPrimaryKeySelective(apply);
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
	public Apply getApplyByApplyId(int applyId) {
		Apply apply = null;
		try {
			apply = applyMapper.selectByPrimaryKey(applyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return apply;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = applyMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Apply> getApplyList(PagingTool pagingTool) {
		List<Apply> applyList = null;

		try {
			applyList = applyMapper.selectApplyList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return applyList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
