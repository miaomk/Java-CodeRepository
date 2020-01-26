package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ExclusiveMapper;
import com.techwells.wumei.domain.Exclusive;
import com.techwells.wumei.domain.rs.RsExclusive;
import com.techwells.wumei.service.ExclusiveService;
import com.techwells.wumei.util.PagingTool;


@Service("ExclusiveService")
public class ExclusiveServiceImpl implements ExclusiveService {

	private ExclusiveMapper exclusiveMapper;

	public ExclusiveMapper getExclusiveMapper() {
		return exclusiveMapper;
	}

	@Autowired
	public void setExclusiveMapper(ExclusiveMapper exclusiveMapper) {
		this.exclusiveMapper = exclusiveMapper;
	}

	@Override
	public int addExclusive(Exclusive exclusive) {
		int count = 0;
		try {
			count = exclusiveMapper.insertSelective(exclusive);
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
	public int delExclusive(int exclusiveId) {
		int count = 0;
		try {
			count = exclusiveMapper.deleteByPrimaryKey(exclusiveId);
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
	public int modifyExclusive(Exclusive exclusive) {
		int count = 0;
		try {
			count = exclusiveMapper.updateByPrimaryKeySelective(exclusive);
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
	public Exclusive getExclusiveByExclusiveId(int exclusiveId) {
		Exclusive exclusive = null;
		try {
			exclusive = exclusiveMapper.selectByPrimaryKey(exclusiveId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return exclusive;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = exclusiveMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<RsExclusive> getExclusiveList(PagingTool pagingTool) {
		List<RsExclusive> exclusiveList = null;

		try {
			exclusiveList = exclusiveMapper.selectExclusiveList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return exclusiveList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
