package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.SettingMapper;
import com.techwells.wumei.domain.Setting;
import com.techwells.wumei.service.SettingService;
import com.techwells.wumei.util.PagingTool;


@Service("SettingService")
public class SettingServiceImpl implements SettingService {

	private SettingMapper settingMapper;

	public SettingMapper getSettingMapper() {
		return settingMapper;
	}

	@Autowired
	public void setSettingMapper(SettingMapper settingMapper) {
		this.settingMapper = settingMapper;
	}

	@Override
	public int addSetting(Setting setting) {
		int count = 0;
		try {
			count = settingMapper.insertSelective(setting);
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
	public int delSetting(int settingId) {
		int count = 0;
		try {
			count = settingMapper.deleteByPrimaryKey(settingId);
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
	public int modifySetting(Setting setting) {
		int count = 0;
		try {
			count = settingMapper.updateByPrimaryKeySelective(setting);
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
	public Setting getSettingBySettingId(int settingId) {
		Setting setting = null;
		try {
			setting = settingMapper.selectByPrimaryKey(settingId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return setting;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = settingMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Setting> getSettingList(PagingTool pagingTool) {
		List<Setting> settingList = null;

		try {
			settingList = settingMapper.selectSettingList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return settingList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
