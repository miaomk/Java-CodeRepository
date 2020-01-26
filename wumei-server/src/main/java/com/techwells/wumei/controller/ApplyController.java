package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Apply;
import com.techwells.wumei.service.ApplyService;
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
public class ApplyController {
	
	@Resource
	private ApplyService applyService;

	/**
	 * 添加模板
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/apply/addApply")
	public @ResponseBody Object addApply(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String realName = request.getParameter("realName");
		String mobile = request.getParameter("mobile");
		String company = request.getParameter("company");
		String provinceCode = request.getParameter("provinceCode");
		String cityCode = request.getParameter("cityCode");
		String districtCode = request.getParameter("districtCode");

		if (realName == null || realName.equals("")) {
			rsInfo.setMessage("姓名不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		

		Apply apply = new Apply();
		apply.setRealName(realName);
		apply.setMobile(mobile);
		apply.setCompany(company);
		apply.setProvinceCode(provinceCode);
		apply.setCityCode(cityCode);
		apply.setDistrictCode(districtCode);

		apply.setCreatedDate(new Date());
		int count = 0;
		try {
			count = applyService.addApply(apply);
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
	@RequestMapping(value = "/apply/modifyApply/{applyId}")
	public @ResponseBody Object modifyApply(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@PathVariable("applyId") Integer applyId) {
		ResultInfo rsInfo = new ResultInfo();

		String applyName = request.getParameter("applyName");

		if (applyName == null || applyName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Apply apply = new Apply();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "apply") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		apply.setApplyId(applyId);
		apply.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = applyService.modifyApply(apply);
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
	@RequestMapping(value = "/apply/deleteApply/{applyId}")
	public @ResponseBody Object deleteApply(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("applyId") Integer applyId) {
		ResultInfo rsInfo = new ResultInfo();

		int count = 0;
		try {
			count = applyService.delApply(applyId);
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
	@RequestMapping(value = "/apply/getApplyById/{applyId}")
	public @ResponseBody Object getApplyById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("applyId") Integer applyId) {
		ResultInfo rsInfo = new ResultInfo();

		
		Apply apply = null;
		try {
			apply = applyService.getApplyByApplyId(applyId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取模板信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (apply == null) {
			rsInfo.setMessage("模板信息不存在！");
			rsInfo.setData(new Apply());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(apply);
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
	@RequestMapping(value = "/apply/getApplyList")
	public @ResponseBody Object getApplyList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String applyName = request.getParameter("applyName");

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

		if (applyName != null && !applyName.equals("")) {
			params.put("applyName", applyName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = applyService.countTotal(pageTool);
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

		List<Apply> applyList = null;
		try {
			applyList = applyService.getApplyList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取模板列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (applyList.size() == 0) {
			rsInfo.setMessage("模板列表为空！");
			rsInfo.setData(new ArrayList<Apply>());
			return rsInfo;
		}
		rsInfo.setData(applyList);
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
	@RequestMapping(value = "/apply/batchDeleteApply")
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
			count = applyService.deleteBatch(idArr);
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
