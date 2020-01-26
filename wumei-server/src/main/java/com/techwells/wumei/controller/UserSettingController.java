package com.techwells.wumei.controller;

import com.techwells.wumei.domain.UserSetting;
import com.techwells.wumei.service.UserSettingService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "*.do")
public class UserSettingController {
	
	@Resource
	private UserSettingService userSettingService;

	/**
	 * 添加用户设置
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/userSetting/addUserSetting")
	public @ResponseBody Object addUserSetting(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String needVerify = request.getParameter("needVerify");
		

		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		UserSetting userSetting = new UserSetting();
		userSetting.setUserId(Integer.parseInt(userId));
		userSetting.setNeedVerify(Integer.parseInt(needVerify));

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "userSetting") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}


		
		int count = 0;
		try {
			count = userSettingService.addUserSetting(userSetting);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加用户设置异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加用户设置成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加用户设置失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改用户设置信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSetting/modifyUserSetting")
	public @ResponseBody Object modifyUserSetting(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String userSettingId = request.getParameter("userSettingId");
		String userSettingName = request.getParameter("userSettingName");

		if (userSettingId == null || userSettingId.equals("")) {
			rsInfo.setMessage("用户设置ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (userSettingName == null || userSettingName.equals("")) {
			rsInfo.setMessage("用户设置名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		UserSetting userSetting = new UserSetting();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "userSetting") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		
		
		int count = 0;
		try {
			count = userSettingService.modifyUserSetting(userSetting);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改用户设置异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改用户设置失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改用户设置成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除用户设置
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSetting/deleteUserSetting")
	public @ResponseBody Object deleteUserSetting(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userSettingId = request.getParameter("userSettingId");
		if (userSettingId == null || userSettingId.equals("")) {
			rsInfo.setMessage("用户设置Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = userSettingService.delUserSetting(Integer.parseInt(userSettingId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除用户设置异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除用户设置成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除用户设置失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看用户设置详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSetting/getUserSettingById")
	public @ResponseBody Object getUserSettingById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户设置ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		UserSetting userSetting = null;
		try {
			userSetting = userSettingService.getUserSettingByUserSettingId(Integer.parseInt(userId));
			
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取用户设置信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (userSetting == null) {
			rsInfo.setMessage("用户设置信息不存在！");
			rsInfo.setData(new UserSetting());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(userSetting);
		rsInfo.setMessage("获取用户设置成功！");
		return rsInfo;
	}

	/**
	 * @description 获取用户设置列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSetting/getUserSettingList")
	public @ResponseBody Object getAdminList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userSettingName = request.getParameter("userSettingName");

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

		if (userSettingName != null && !userSettingName.equals("")) {
			params.put("userSettingName", userSettingName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = userSettingService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户设置总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("用户设置总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<UserSetting> userSettingList = null;
		try {
			userSettingList = userSettingService.getUserSettingList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户设置列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (userSettingList.size() == 0) {
			rsInfo.setMessage("用户设置列表为空！");
			rsInfo.setData(new ArrayList<UserSetting>());
			return rsInfo;
		}
		rsInfo.setData(userSettingList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取用户设置列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除用户设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSetting/deleteUserSettingBatch")
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
			count = userSettingService.deleteBatch(idArr);
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
}
