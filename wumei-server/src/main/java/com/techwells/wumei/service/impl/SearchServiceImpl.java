package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.SearchMapper;
import com.techwells.wumei.domain.Search;
import com.techwells.wumei.service.SearchService;
import com.techwells.wumei.util.PagingTool;


@Service("SearchService")
public class SearchServiceImpl implements SearchService {

	private SearchMapper searchMapper;

	public SearchMapper getSearchMapper() {
		return searchMapper;
	}

	@Autowired
	public void setSearchMapper(SearchMapper searchMapper) {
		this.searchMapper = searchMapper;
	}

	@Override
	public int addSearch(Search search) {
		int count = 0;
		try {
			count = searchMapper.insertSelective(search);
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
	public int delSearch(int searchId) {
		int count = 0;
		try {
			count = searchMapper.deleteByPrimaryKey(searchId);
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
	public int modifySearch(Search search) {
		int count = 0;
		try {
			count = searchMapper.updateByPrimaryKeySelective(search);
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
	public Search getSearchBySearchId(int searchId) {
		Search search = null;
		try {
			search = searchMapper.selectByPrimaryKey(searchId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return search;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = searchMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Search> getSearchList(PagingTool pagingTool) {
		List<Search> searchList = null;

		try {
			searchList = searchMapper.selectSearchList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return searchList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
