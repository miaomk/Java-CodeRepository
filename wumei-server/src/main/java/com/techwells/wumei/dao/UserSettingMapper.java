package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.UserSetting;
import com.techwells.wumei.util.PagingTool;

public interface UserSettingMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserSetting record);

    int insertSelective(UserSetting record);

    UserSetting selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserSetting record);

    int updateByPrimaryKey(UserSetting record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<UserSetting> selectUserSettingList(PagingTool pagingTool);
	
}