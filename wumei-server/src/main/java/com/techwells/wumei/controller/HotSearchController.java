package com.techwells.wumei.controller;

import com.techwells.wumei.domain.HotSearch;
import com.techwells.wumei.service.HotSearchService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
public class HotSearchController {
	
	@Resource
	private HotSearchService hotSearchService;

	/**
	 * 添加模板
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/hotSearch/addHotSearch")
	public @ResponseBody Object addHotSearch(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String hotSearchName = request.getParameter("hotSearchName");

		if (hotSearchName == null || hotSearchName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		HotSearch hotSearch = new HotSearch();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "hotSearch") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		hotSearch.setCreatedDate(new Date());
		int count = 0;
		try {
			count = hotSearchService.addHotSearch(hotSearch);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加模板异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加模板成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加模板失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改模板信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotSearch/modifyHotSearch/{hotSearchId}")
	public @ResponseBody Object modifyHotSearch(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@PathVariable("hotSearchId") Integer hotSearchId) {
		ResultInfo rsInfo = new ResultInfo();

		String hotSearchName = request.getParameter("hotSearchName");

		if (hotSearchName == null || hotSearchName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		HotSearch hotSearch = new HotSearch();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "hotSearch") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		hotSearch.setHotSearchId(hotSearchId);
		hotSearch.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = hotSearchService.modifyHotSearch(hotSearch);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改模板异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改模板失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改模板成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除模板
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotSearch/deleteHotSearch/{hotSearchId}")
	public @ResponseBody Object deleteHotSearch(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("hotSearchId") Integer hotSearchId) {
		ResultInfo rsInfo = new ResultInfo();

		int count = 0;
		try {
			count = hotSearchService.delHotSearch(hotSearchId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除模板异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除模板成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除模板失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看模板详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotSearch/getHotSearchById/{hotSearchId}")
	public @ResponseBody Object getHotSearchById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("hotSearchId") Integer hotSearchId) {
		ResultInfo rsInfo = new ResultInfo();

		
		HotSearch hotSearch = null;
		try {
			hotSearch = hotSearchService.getHotSearchByHotSearchId(hotSearchId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取模板信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (hotSearch == null) {
			rsInfo.setMessage("模板信息不存在！");
			rsInfo.setData(new HotSearch());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(hotSearch);
		rsInfo.setMessage("获取模板成功！");
		return rsInfo;
	}

	/**
	 * @description 获取模板列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotSearch/getHotSearchList")
	public @ResponseBody Object getHotSearchList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String hotSearchName = request.getParameter("hotSearchName");

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

		if (hotSearchName != null && !hotSearchName.equals("")) {
			params.put("hotSearchName", hotSearchName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = hotSearchService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取模板总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("模板总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<HotSearch> hotSearchList = null;
		try {
			hotSearchList = hotSearchService.getHotSearchList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取模板列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (hotSearchList.size() == 0) {
			rsInfo.setMessage("模板列表为空！");
			rsInfo.setData(new ArrayList<HotSearch>());
			return rsInfo;
		}
		rsInfo.setData(hotSearchList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取模板列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotSearch/batchDeleteHotSearch")
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
			count = hotSearchService.deleteBatch(idArr);
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
