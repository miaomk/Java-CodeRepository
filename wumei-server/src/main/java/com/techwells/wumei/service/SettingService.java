package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Setting;
import com.techwells.wumei.util.PagingTool;

public interface SettingService {

	// 增删改查
	public int addSetting(Setting setting);

	public int delSetting(int settingId);

	public int modifySetting(Setting setting);

	Setting getSettingBySettingId(int settingId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Setting> getSettingList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
