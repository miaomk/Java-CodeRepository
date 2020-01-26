package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Admin;
import com.techwells.wumei.domain.vo.AdminVO;
import com.techwells.wumei.service.AdminService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 管理员Controller
 *
 * @author miao
 */
@RestController
public class AdminController {
	@Resource
	private AdminService adminService;

	/**
	 *
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/admin/addAdmin")
	public ResultInfo addAdmin(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String adminName = request.getParameter("adminName");
		String description = request.getParameter("description");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String deptId = request.getParameter("deptId");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String roleId = request.getParameter("roleId");

		if (adminName == null || "".equals(adminName)) {
			rsInfo.setMessage("管理员名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (adminService.getAdminByAdminName(adminName) != null) {
			rsInfo.setMessage("管理员名称已存在！");
			rsInfo.setCode("10100");
			return rsInfo;
		}

		Admin admin = new Admin();
		admin.setAdminId(UUID.randomUUID().toString().replace("-", ""));
		admin.setAdminName(adminName);
		if (email != null && !"".equals(email)) {
			admin.setEmail(email);
		}
		if (mobile != null && !"".equals(mobile)) {
			admin.setMobile(mobile);
		}
		if (deptId != null && !"".equals(deptId)) {
			admin.setDeptId(Integer.parseInt(deptId));
		}
		if (account != null && !"".equals(account)) {
			admin.setAccount(account);
		}
		if (password != null && !"".equals(password)) {
			// 将密码进行加密操作
			admin.setPassword(password);
		}
		if (roleId != null && !"".equals(roleId)) {
			admin.setRoleId(Integer.parseInt(roleId));
		}
		if (description != null && !"".equals(description)) {
			admin.setDescription(description);
		}

		int count;
		try {
			count = adminService.addAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加管理员异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加管理员成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加管理员失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改管理员信息
	 *
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/admin/modifyAdmin/{adminId}")
	public ResultInfo modifyAdmin(HttpServletRequest request,
											@PathVariable("adminId") String adminId) {
		ResultInfo rsInfo = new ResultInfo();

		String adminName = request.getParameter("adminName");
		String description = request.getParameter("description");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String deptId = request.getParameter("deptId");
		String account = request.getParameter("account");
		String roleId = request.getParameter("roleId");

		if (adminId == null || "".equals(adminId)) {
			rsInfo.setMessage("管理员ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (adminName == null || "".equals(adminName)) {
			rsInfo.setMessage("管理员名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Admin admin = new Admin();
		admin.setAdminName(adminName);
		admin.setAdminId(adminId);
		if (email != null && !"".equals(email)) {
			admin.setEmail(email);
		}
		if (mobile != null && !"".equals(mobile)) {
			admin.setMobile(mobile);
		}
		if (deptId != null && !"".equals(deptId)) {
			admin.setDeptId(Integer.parseInt(deptId));
		}
		if (account != null && !"".equals(account)) {
			admin.setAccount(account);
		}
		if (roleId != null && !"".equals(roleId)) {
			admin.setRoleId(Integer.parseInt(roleId));
		}
		if (description != null && !"".equals(description)) {
			admin.setDescription(description);
		}

		int count;
		try {
			count = adminService.modifyAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改管理员异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改管理员失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改管理员成功！");
		rsInfo.setData(count);
		return rsInfo;
	}
	/**
	 * Description: 删除管理员
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteAdmin")
	public ResultInfo deleteAdmin(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String adminId = request.getParameter("adminId");
		if (adminId == null || "".equals(adminId)) {
			rsInfo.setMessage("管理员Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = adminService.delAdmin(Integer.parseInt(adminId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除管理员异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除管理员成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除管理员失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看管理员详情
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/getAdminById")
	public ResultInfo getAdminById(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String adminId = request.getParameter("adminId");
		if (adminId == null || "".equals(adminId)) {
			rsInfo.setMessage("管理员ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Admin admin;
		try {
			admin = adminService.getAdminByAdminId(Integer.parseInt(adminId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取管理员信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (admin == null) {
			rsInfo.setMessage("管理员信息不存在！");
			rsInfo.setData(new Admin());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(admin);
		rsInfo.setMessage("获取管理员成功！");
		return rsInfo;
	}

	/**
	 * @description 获取管理员列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/getAdminList")
	public ResultInfo getAdminList(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String adminName = request.getParameter("adminName");
		String roleId = request.getParameter("roleId");

		if (pageNum == null || "".equals(pageNum)) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || "".equals(pageSize)) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (adminName != null && !"".equals(adminName)) {
			params.put("adminName", adminName);
		}

		if (roleId != null && !"".equals(roleId)) {
			params.put("roleId", roleId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = adminService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取管理员总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("管理员总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}

		List<Admin> adminList;
		try {
			adminList = adminService.getAdminList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取管理员列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (adminList.size() == 0) {
			rsInfo.setMessage("管理员列表为空！");
			rsInfo.setData(new ArrayList<Admin>());
			return rsInfo;
		}
		rsInfo.setData(adminList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取管理员列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除管理员
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteAdminBatch")
	public ResultInfo deleteBatch(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count;
		try {
			count = adminService.deleteBatch(idArr);
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
	 * 登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/login")
	public @ResponseBody
	Object login(HttpServletRequest request) {
		String adminName;
		//response.addHeader("Access-Control-Allow-Origin", "*");
		adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		ResultInfo rsInfo = new ResultInfo();


		if (adminName == null || "".equals(adminName)) {
			rsInfo.setMessage("用户名不能为空！");
			rsInfo.setCode("11110");
			return rsInfo;
		}
		if (password == null || "".equals(password)) {
			rsInfo.setMessage("密码不能为空！");
			rsInfo.setCode("11111");
			return rsInfo;
		}
		AdminVO admin;
		try {
			admin = adminService.getAdminByAdminName(adminName);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取用户信息出错！");
			rsInfo.setCode("11210");
			return rsInfo;
		}
		if (admin != null) {
			if (!password.equals(admin.getPassword())) {

				rsInfo.setMessage("密码不正确！");
				rsInfo.setCode("11310");
				return rsInfo;
			}
		} else {
			rsInfo.setMessage("用户不存在！");
			rsInfo.setCode("11311");
			return rsInfo;
		}

		rsInfo.setData(admin);
		rsInfo.setMessage(null);
		request.getSession().setAttribute("userId",admin.getAdminId());
		return rsInfo;
	}
}
