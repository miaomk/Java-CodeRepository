package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Role;
import com.techwells.wumei.service.RoleService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
public class RoleController {
	private RoleService roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@PostMapping(value = "/role/addRole")
	public @ResponseBody Object addRole(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String roleName = request.getParameter("roleName");
		String description = request.getParameter("description");
		String authoritys = request.getParameter("authoritys");
		if (roleName == null || "".equals(roleName)) {
			rsInfo.setMessage("角色名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		Role role = new Role();
		if(authoritys != null && !"".equals(authoritys)) {
			role.setAuthoritys(authoritys);
		}
		role.setRoleName(roleName);
		role.setCreatedDate(new Date());
		
		if(description!=null&&! "".equals(description)) {
			role.setDescription(description);
		}
		int count = 0;
		try {
			count = roleService.addRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加角色异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加角色成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加角色失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改角色信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/modifyRole/{roleId}")
	public @ResponseBody Object modifyRole(HttpServletRequest request,
			HttpSession session, HttpServletResponse response,@PathVariable("roleId")Integer roleId) {
		ResultInfo rsInfo = new ResultInfo();

		String roleName = request.getParameter("roleName");
		String authoritys = request.getParameter("authoritys");
		String description = request.getParameter("description");

		if (roleId == null || "".equals(roleId)) {
			rsInfo.setMessage("角色ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (roleName == null || roleName.equals("")) {
			rsInfo.setMessage("角色名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Role role = new Role();
		if(authoritys != null && !"".equals(authoritys)) {
			role.setAuthoritys(authoritys);
		}
		if(description!=null&&! "".equals(description)) {
			role.setDescription(description);
		}
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		role.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = roleService.modifyRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改角色异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改角色失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改角色成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除角色
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/deleteRole")
	public @ResponseBody Object deleteRole(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String roleId = request.getParameter("roleId");
		if (roleId == null || roleId.equals("")) {
			rsInfo.setMessage("角色Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = roleService.delRole(Integer.parseInt(roleId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除角色异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除角色成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除角色失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看角色详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/getRoleById")
	public @ResponseBody Object getRoleById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String roleId = request.getParameter("roleId");
		if (roleId == null || roleId.equals("")) {
			rsInfo.setMessage("角色ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Role role = null;
		try {
			role = roleService.getRoleByRoleId(Integer.parseInt(roleId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取角色信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (role == null) {
			rsInfo.setMessage("角色信息不存在！");
			rsInfo.setData(new Role());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(role);
		rsInfo.setMessage("获取角色成功！");
		return rsInfo;
	}

	/**
	 * @description 获取角色列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/getRoleList")
	public @ResponseBody Object getRoleList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String roleName = request.getParameter("roleName");

		if (pageNum == null || pageNum.equals("")) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || pageSize.equals("")) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (roleName != null && !roleName.equals("")) {
			params.put("roleName", roleName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = roleService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取角色总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("角色总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Role> roleList = null;
		try {
			roleList = roleService.getRoleList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取角色列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (roleList.size() == 0) {
			rsInfo.setMessage("角色列表为空！");
			rsInfo.setData(new ArrayList<Role>());
			return rsInfo;
		}
		rsInfo.setData(roleList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取角色列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/deleteRoleBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = roleService.deleteBatch(idArr);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("批量删除异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (count > 0) {
			rsInfo.setMessage("批量删除成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("批量删除失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}
	
	
	/**
	 * 获取所有角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/role/getAllRole")
	public @ResponseBody Object getAllRole(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();


		List<Role> roleList = null;
		try {
			roleList = roleService.getAllRole();
		} catch (Exception e) {
			rsInfo.setMessage("获取角色列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (roleList.size() == 0) {
			rsInfo.setMessage("无角色数据！");
			rsInfo.setData(new ArrayList<Role>());
			return rsInfo;
		}
		rsInfo.setData(roleList);
		rsInfo.setMessage("获取角色列表成功！");
		return rsInfo;
	}

	
	
}
