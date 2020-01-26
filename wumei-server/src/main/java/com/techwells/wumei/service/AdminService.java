package com.techwells.wumei.service;

import com.techwells.wumei.domain.Admin;
import com.techwells.wumei.domain.vo.AdminVO;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface AdminService {

	// 增删改查
	public int addAdmin(Admin admin);

	public int delAdmin(int adminId);

	public int modifyAdmin(Admin admin);

	Admin getAdminByAdminId(int adminId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Admin> getAdminList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	
	// 根据管理员用户名获取管理员列表
	AdminVO getAdminByAdminName(String adminName);
}
