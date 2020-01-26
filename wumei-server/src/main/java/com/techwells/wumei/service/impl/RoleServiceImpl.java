package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.wumei.dao.AuthorityMapper;
import com.techwells.wumei.dao.RoleMapper;
import com.techwells.wumei.domain.Role;
import com.techwells.wumei.service.RoleService;
import com.techwells.wumei.util.PagingTool;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {

	private RoleMapper roleMapper;
	
	private AuthorityMapper authorityMapper;

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public int addRole(Role role) {
		int count = 0;
		try {
			count = roleMapper.insertSelective(role);
			if (count < 0) {
				throw new Exception("添加角色失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加角色异常！");
		}
		return count;
	}

	@Override
	public int delRole(int roleId) {
		int count = 0;
		try {
			count = roleMapper.deleteByPrimaryKey(roleId);
			if (count < 0) {
				throw new Exception("删除角色失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除角色异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyRole(Role role) {		
		int count = 0;
		try {
			count = roleMapper.updateByPrimaryKeySelective(role);
			if (count < 0) {
				throw new Exception("修改角色失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改角色异常！");
		}
		return count;
	}

	@Override
	public Role getRoleByRoleId(int roleId) {
		Role role = null;
		try {
			role = roleMapper.selectByPrimaryKey(roleId);
			/*
			 * String authoritys = role.getAuthoritys(); if(authoritys != null&&
			 * authoritys.length()>0) { String[] authorityIds = authoritys.split(",");
			 * List<Authority> authorities =
			 * authorityMapper.selectAuthorityListByIds(authorityIds); if(authorities!= null
			 * && authorities.size() >=0) { role.setAuthorityList(authorities); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取角色详情异常！");
		}
		return role;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = roleMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取角色总数异常！");
		}
		return count;
	}

	@Override
	public List<Role> getRoleList(PagingTool pagingTool) {
		List<Role> roleList = null;

		try {
			roleList = roleMapper.selectRoleList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取角色列表异常");
		}
		return roleList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Role getRoleByRoleName(String roleName) {
		Role role = null;
		try {
			role = roleMapper.selectByRoleName(roleName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取角色详情异常！");
		}
		return role;
	}

	@Override
	public List<Role> getAllRole() {
		List<Role> roleList = null;

		try {
			roleList = roleMapper.selectAllRole();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取角色列表异常");
		}
		return roleList;
	}

}
