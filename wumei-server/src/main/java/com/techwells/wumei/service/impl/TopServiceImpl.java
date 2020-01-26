package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TopMapper;
import com.techwells.wumei.domain.Top;
import com.techwells.wumei.domain.rs.RsTop;
import com.techwells.wumei.service.TopService;
import com.techwells.wumei.util.DateUtil;
import com.techwells.wumei.util.PagingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("TopService")
public class TopServiceImpl implements TopService {

	private TopMapper topMapper;

	public TopMapper getTopMapper() {
		return topMapper;
	}

	@Autowired
	public void setTopMapper(TopMapper topMapper) {
		this.topMapper = topMapper;
	}

	@Override
	public int addTop(Top top) {
		int count = 0;
		try {
			count = topMapper.insertSelective(top);
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
	public int delTop(int topId) {
		int count = 0;
		try {
			count = topMapper.deleteByPrimaryKey(topId);
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
	public int modifyTop(Top top) {
		int count = 0;
		try {
			count = topMapper.updateByPrimaryKeySelective(top);
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
	public Top getTopByTopId(int topId) {
		Top top = null;
		try {
			top = topMapper.selectByPrimaryKey(topId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return top;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = topMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<RsTop> getTopList(PagingTool pagingTool) {
		List<RsTop> topList = null;

		try {
			topList = topMapper.selectTopList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return topList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchAddTop(String[] topIdArrays,String startDate,String endDate) {
		List<Top> topList = new ArrayList<>();

		int count = 0;
		try {

			Date startTime = DateUtil.getDateFromString(startDate);
			Date endTime = DateUtil.getDateFromString(endDate);

			for (String topIdArray : topIdArrays) {
				Top top = new Top();

				top.setProductId(Integer.parseInt(topIdArray));
				top.setCreatedDate(new Date());
				top.setStartTime(startTime);
				top.setEndTime(endTime);

				topList.add(top);
			}

			count = topMapper.batchInsertTop(topList);
			if (count < 0) {
				throw new Exception("添加榜单失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加榜单异常！");
		}
		return count;


	}

	@Override
	public int updateSort(String topId, String sort) {
		Top top = new Top();
		top.setTopId(Integer.parseInt(topId));
		top.setSortIndex(Integer.parseInt(sort));

		int count = 0;
		try {
			count = topMapper.updateByPrimaryKeySelective(top);

			if (count < 0) {
				throw new Exception("更改排序失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更改排序异常！");
		}

		return count;
	}

	@Override
	public int batchUpdateStatus(String[] idArrays,String recommendStatus) {

		int count;
		try {

			count = topMapper.batchUpdateStatus(idArrays, recommendStatus);

			if (count < 0) {
				throw new Exception("更改推荐状态失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更改推荐状态异常！");

		}

		return count;
	}

}
