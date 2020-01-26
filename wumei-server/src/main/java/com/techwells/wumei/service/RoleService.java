package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Role;
import com.techwells.wumei.util.PagingTool;

public interface RoleService {

	// 增删改查
	public int addRole(Role role);

	public int delRole(int roleId);

	public int modifyRole(Role role);

	Role getRoleByRoleId(int roleId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Role> getRoleList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	
	// 根据角色获取角色列表
	Role getRoleByRoleName(String roleName);

	List<Role> getAllRole();
}
