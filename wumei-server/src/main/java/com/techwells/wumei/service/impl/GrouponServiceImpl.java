package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.GrouponMapper;
import com.techwells.wumei.domain.Groupon;
import com.techwells.wumei.domain.rs.RsGroupon;
import com.techwells.wumei.service.GrouponService;
import com.techwells.wumei.util.PagingTool;


@Service("GrouponService")
public class GrouponServiceImpl implements GrouponService {

	private GrouponMapper grouponMapper;

	public GrouponMapper getGrouponMapper() {
		return grouponMapper;
	}

	@Autowired
	public void setGrouponMapper(GrouponMapper grouponMapper) {
		this.grouponMapper = grouponMapper;
	}

	@Override
	public int addGroupon(Groupon groupon) {
		int count = 0;
		try {
			count = grouponMapper.insertSelective(groupon);
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
	public int delGroupon(int grouponId) {
		int count = 0;
		try {
			count = grouponMapper.deleteByPrimaryKey(grouponId);
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
	public int modifyGroupon(Groupon groupon) {
		int count = 0;
		try {
			count = grouponMapper.updateByPrimaryKeySelective(groupon);
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
	public Groupon getGrouponByGrouponId(int grouponId) {
		Groupon groupon = null;
		try {
			groupon = grouponMapper.selectByPrimaryKey(grouponId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return groupon;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = grouponMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<RsGroupon> getGrouponList(PagingTool pagingTool) {
		List<RsGroupon> grouponList = null;

		try {
			grouponList = grouponMapper.selectGrouponList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return grouponList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<Groupon> getGrouponListByProductId(Integer productId) {
		List<Groupon> groupon = null;
		try {
			groupon = grouponMapper.getGrouponListByProductId(productId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取拼团规则详情异常！");
		}
		return groupon;
	}

}
