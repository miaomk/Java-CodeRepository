package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.BrowseMapper;
import com.techwells.wumei.domain.Browse;
import com.techwells.wumei.service.BrowseService;
import com.techwells.wumei.util.PagingTool;


@Service("BrowseService")
public class BrowseServiceImpl implements BrowseService {

	private BrowseMapper browseMapper;

	public BrowseMapper getBrowseMapper() {
		return browseMapper;
	}

	@Autowired
	public void setBrowseMapper(BrowseMapper browseMapper) {
		this.browseMapper = browseMapper;
	}

	@Override
	public int addBrowse(Browse browse) {
		int count = 0;
		try {
			count = browseMapper.insertSelective(browse);
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
	public int delBrowse(int browseId) {
		int count = 0;
		try {
			count = browseMapper.deleteByPrimaryKey(browseId);
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
	public int modifyBrowse(Browse browse) {
		int count = 0;
		try {
			count = browseMapper.updateByPrimaryKeySelective(browse);
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
	public Browse getBrowseByBrowseId(int browseId) {
		Browse browse = null;
		try {
			browse = browseMapper.selectByPrimaryKey(browseId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return browse;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = browseMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Browse> getBrowseList(PagingTool pagingTool) {
		List<Browse> browseList = null;

		try {
			browseList = browseMapper.selectBrowseList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return browseList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
