package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.BlacklistMapper;
import com.techwells.wumei.domain.Blacklist;
import com.techwells.wumei.domain.BlacklistKey;
import com.techwells.wumei.service.BlacklistService;
import com.techwells.wumei.util.PagingTool;


@Service("BlacklistService")
public class BlacklistServiceImpl implements BlacklistService {

	private BlacklistMapper blacklistMapper;

	public BlacklistMapper getBlacklistMapper() {
		return blacklistMapper;
	}

	@Autowired
	public void setBlacklistMapper(BlacklistMapper blacklistMapper) {
		this.blacklistMapper = blacklistMapper;
	}

	@Override
	public int addBlacklist(Blacklist blacklist) {
		int count = 0;
		try {
			count = blacklistMapper.insertSelective(blacklist);
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
	public int delBlacklist(BlacklistKey key) {
		int count = 0;
		try {
			count = blacklistMapper.deleteByPrimaryKey(key);
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
	public int modifyBlacklist(Blacklist blacklist) {
		int count = 0;
		try {
			count = blacklistMapper.updateByPrimaryKeySelective(blacklist);
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
	public Blacklist getBlacklistByBlacklistId(BlacklistKey key) {
		Blacklist blacklist = null;
		try {
			blacklist = blacklistMapper.selectByPrimaryKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return blacklist;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = blacklistMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Blacklist> getBlacklistList(PagingTool pagingTool) {
		List<Blacklist> blacklistList = null;

		try {
			blacklistList = blacklistMapper.selectBlacklistList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return blacklistList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
