package com.techwells.wumei.dao;
import java.util.List;

import com.techwells.wumei.domain.Role;
import com.techwells.wumei.util.PagingTool;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Role> selectRoleList(PagingTool pagingTool);
 	
 	// 根据角色名称获取角色详情
 	Role selectByRoleName(String RoleName);

	List<Role> selectAllRole();

	String selectAuthorityByRoleId(Integer roleId);
	
	
}