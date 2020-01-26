package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Admin;
import com.techwells.wumei.domain.vo.AdminVO;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
	int deleteByPrimaryKey(Integer adminId);

	int insert(Admin record);

	int insertSelective(Admin record);

	Admin selectByPrimaryKey(Integer adminId);

	int updateByPrimaryKeySelective(Admin record);

	int updateByPrimaryKey(Admin record);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Admin> selectAdminList(PagingTool pagingTool);
	
	// 根据管理员用户名获取管理员详情
	AdminVO selectByAdminName(@Param("adminName") String adminName);
}