package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Blacklist;
import com.techwells.wumei.domain.BlacklistKey;
import com.techwells.wumei.service.BlacklistService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class BlacklistController {
	
	@Resource
	private BlacklistService blacklistService;

	/**
	 * 添加黑名单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/blacklist/addBlacklist")
	public @ResponseBody Object addBlacklist(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		
		ResultInfo rsInfo = new ResultInfo();
		String fromUserId = request.getParameter("fromUserId");
		String toUserId = request.getParameter("toUserId");

		
		if (fromUserId == null || fromUserId.equals("")) {
			rsInfo.setMessage("用户Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (toUserId == null || toUserId.equals("")) {
			rsInfo.setMessage("被拉黑人Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Blacklist blacklist = new Blacklist();
		blacklist.setFromUserId(Integer.parseInt(fromUserId));
		blacklist.setToUserId(Integer.parseInt(toUserId));

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "blacklist") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		blacklist.setCreatedDate(new Date());
		int count = 0;
		try {
			count = blacklistService.addBlacklist(blacklist);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加黑名单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加黑名单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加黑名单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改黑名单信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blacklist/modifyBlacklist")
	public @ResponseBody Object modifyBlacklist(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String blacklistId = request.getParameter("blacklistId");
		String blacklistName = request.getParameter("blacklistName");

		if (blacklistId == null || blacklistId.equals("")) {
			rsInfo.setMessage("黑名单ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (blacklistName == null || blacklistName.equals("")) {
			rsInfo.setMessage("黑名单名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Blacklist blacklist = new Blacklist();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "blacklist") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		blacklist.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = blacklistService.modifyBlacklist(blacklist);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改黑名单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改黑名单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改黑名单成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除黑名单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blacklist/deleteBlacklist")
	public @ResponseBody Object deleteBlacklist(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String fromUserId = request.getParameter("fromUserId");
		String toUserId = request.getParameter("toUserId");

		
		if (fromUserId == null || fromUserId.equals("")) {
			rsInfo.setMessage("用户Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (toUserId == null || toUserId.equals("")) {
			rsInfo.setMessage("被拉黑人Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		BlacklistKey key = new BlacklistKey();
		key.setFromUserId(Integer.parseInt(fromUserId));
		key.setToUserId(Integer.parseInt(toUserId));
		
		int count = 0;
		try {
			count = blacklistService.delBlacklist(key);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除黑名单异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除黑名单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除黑名单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看黑名单详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blacklist/getBlacklistById")
	public @ResponseBody Object getBlacklistById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String fromUserId = request.getParameter("fromUserId");
		String toUserId = request.getParameter("toUserId");

		
		if (fromUserId == null || fromUserId.equals("")) {
			rsInfo.setMessage("用户Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (toUserId == null || toUserId.equals("")) {
			rsInfo.setMessage("被拉黑人Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		BlacklistKey key = new BlacklistKey();
		key.setFromUserId(Integer.parseInt(fromUserId));
		key.setToUserId(Integer.parseInt(toUserId));
		
		
		Blacklist blacklist = null;
		try {
			blacklist = blacklistService.getBlacklistByBlacklistId(key);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取黑名单信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (blacklist == null) {
			rsInfo.setMessage("黑名单信息不存在！");
			rsInfo.setData(new Blacklist());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(blacklist);
		rsInfo.setMessage("获取黑名单成功！");
		return rsInfo;
	}

	/**
	 * @description 获取黑名单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blacklist/getBlacklistList")
	public @ResponseBody Object getBlacklistList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userId = request.getParameter("userId");

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

		if (userId != null && !userId.equals("")) {
			params.put("userId", userId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = blacklistService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取黑名单总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("黑名单总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Blacklist> blacklistList = null;
		try {
			blacklistList = blacklistService.getBlacklistList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取黑名单列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (blacklistList.size() == 0) {
			rsInfo.setMessage("黑名单列表为空！");
			rsInfo.setData(new ArrayList<Blacklist>());
			return rsInfo;
		}
		rsInfo.setData(blacklistList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取黑名单列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除黑名单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blacklist/deleteBlacklistBatch")
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
			count = blacklistService.deleteBatch(idArr);
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
