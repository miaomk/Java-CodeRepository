package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Setting;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface SettingMapper {
    int deleteByPrimaryKey(Integer settingId);

    int insert(Setting record);

    int insertSelective(Setting record);

    Setting selectByPrimaryKey(Integer settingId);

    int updateByPrimaryKeySelective(Setting record);

    int updateByPrimaryKey(Setting record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Setting> selectSettingList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);
}