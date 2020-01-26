package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.AaaMapper;
import com.techwells.wumei.domain.Aaa;
import com.techwells.wumei.service.AaaService;
import com.techwells.wumei.util.PagingTool;


@Service("AaaService")
public class AaaServiceImpl implements AaaService {

	private AaaMapper aaaMapper;

	public AaaMapper getAaaMapper() {
		return aaaMapper;
	}

	@Autowired
	public void setAaaMapper(AaaMapper aaaMapper) {
		this.aaaMapper = aaaMapper;
	}

	@Override
	public int addAaa(Aaa aaa) {
		int count = 0;
		try {
			count = aaaMapper.insertSelective(aaa);
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
	public int delAaa(int aaaId) {
		int count = 0;
		try {
			count = aaaMapper.deleteByPrimaryKey(aaaId);
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
	public int modifyAaa(Aaa aaa) {
		int count = 0;
		try {
			count = aaaMapper.updateByPrimaryKeySelective(aaa);
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
	public Aaa getAaaByAaaId(int aaaId) {
		Aaa aaa = null;
		try {
			aaa = aaaMapper.selectByPrimaryKey(aaaId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return aaa;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = aaaMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Aaa> getAaaList(PagingTool pagingTool) {
		List<Aaa> aaaList = null;

		try {
			aaaList = aaaMapper.selectAaaList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return aaaList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
