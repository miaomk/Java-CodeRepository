package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.HotSearchMapper;
import com.techwells.wumei.domain.HotSearch;
import com.techwells.wumei.service.HotSearchService;
import com.techwells.wumei.util.PagingTool;


@Service("HotSearchService")
public class HotSearchServiceImpl implements HotSearchService {

	private HotSearchMapper hotSearchMapper;

	public HotSearchMapper getHotSearchMapper() {
		return hotSearchMapper;
	}

	@Autowired
	public void setHotSearchMapper(HotSearchMapper hotSearchMapper) {
		this.hotSearchMapper = hotSearchMapper;
	}

	@Override
	public int addHotSearch(HotSearch hotSearch) {
		int count = 0;
		try {
			count = hotSearchMapper.insertSelective(hotSearch);
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
	public int delHotSearch(int hotSearchId) {
		int count = 0;
		try {
			count = hotSearchMapper.deleteByPrimaryKey(hotSearchId);
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
	public int modifyHotSearch(HotSearch hotSearch) {
		int count = 0;
		try {
			count = hotSearchMapper.updateByPrimaryKeySelective(hotSearch);
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
	public HotSearch getHotSearchByHotSearchId(int hotSearchId) {
		HotSearch hotSearch = null;
		try {
			hotSearch = hotSearchMapper.selectByPrimaryKey(hotSearchId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return hotSearch;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = hotSearchMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<HotSearch> getHotSearchList(PagingTool pagingTool) {
		List<HotSearch> hotSearchList = null;

		try {
			hotSearchList = hotSearchMapper.selectHotSearchList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return hotSearchList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
