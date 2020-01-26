package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.UserSetting;
import com.techwells.wumei.util.PagingTool;

public interface UserSettingService {

	// 增删改查
	public int addUserSetting(UserSetting userSetting);

	public int delUserSetting(int userSettingId);

	public int modifyUserSetting(UserSetting userSetting);

	UserSetting getUserSettingByUserSettingId(int userSettingId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<UserSetting> getUserSettingList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
