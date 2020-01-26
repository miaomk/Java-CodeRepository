package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.UserSettingMapper;
import com.techwells.wumei.domain.UserSetting;
import com.techwells.wumei.service.UserSettingService;
import com.techwells.wumei.util.PagingTool;


@Service("UserSettingService")
public class UserSettingServiceImpl implements UserSettingService {

	private UserSettingMapper userSettingMapper;

	public UserSettingMapper getUserSettingMapper() {
		return userSettingMapper;
	}

	@Autowired
	public void setUserSettingMapper(UserSettingMapper userSettingMapper) {
		this.userSettingMapper = userSettingMapper;
	}

	@Override
	public int addUserSetting(UserSetting userSetting) {
		int count = 0;
		try {
			count = userSettingMapper.insertSelective(userSetting);
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
	public int delUserSetting(int userSettingId) {
		int count = 0;
		try {
			count = userSettingMapper.deleteByPrimaryKey(userSettingId);
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
	public int modifyUserSetting(UserSetting userSetting) {
		int count = 0;
		try {
			count = userSettingMapper.updateByPrimaryKeySelective(userSetting);
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
	public UserSetting getUserSettingByUserSettingId(int userSettingId) {
		UserSetting userSetting = null;
		try {
			userSetting = userSettingMapper.selectByPrimaryKey(userSettingId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return userSetting;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = userSettingMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<UserSetting> getUserSettingList(PagingTool pagingTool) {
		List<UserSetting> userSettingList = null;

		try {
			userSettingList = userSettingMapper.selectUserSettingList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return userSettingList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
