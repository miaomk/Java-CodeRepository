package com.techwells.wumei.controller;

import com.techwells.wumei.domain.PurchaseRule;
import com.techwells.wumei.service.PurchaseRuleService;
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
public class PurchaseRuleController {
	
	@Resource
	private PurchaseRuleService purchaseRuleService;

	/**
	 * 添加模板
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/purchaseRule/addPurchaseRule")
	public @ResponseBody Object addPurchaseRule(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseRuleName = request.getParameter("purchaseRuleName");

		if (purchaseRuleName == null || purchaseRuleName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		PurchaseRule purchaseRule = new PurchaseRule();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "purchaseRule") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		purchaseRule.setCreatedDate(new Date());
		int count = 0;
		try {
			count = purchaseRuleService.addPurchaseRule(purchaseRule);
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
	@RequestMapping(value = "/purchaseRule/modifyPurchaseRule")
	public @ResponseBody Object modifyPurchaseRule(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseRuleId = request.getParameter("purchaseRuleId");
		String purchaseRuleName = request.getParameter("purchaseRuleName");

		if (purchaseRuleId == null || purchaseRuleId.equals("")) {
			rsInfo.setMessage("模板ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (purchaseRuleName == null || purchaseRuleName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		PurchaseRule purchaseRule = new PurchaseRule();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "purchaseRule") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		purchaseRule.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = purchaseRuleService.modifyPurchaseRule(purchaseRule);
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
	@RequestMapping(value = "/purchaseRule/deletePurchaseRule")
	public @ResponseBody Object deletePurchaseRule(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseRuleId = request.getParameter("purchaseRuleId");
		if (purchaseRuleId == null || purchaseRuleId.equals("")) {
			rsInfo.setMessage("模板Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = purchaseRuleService.delPurchaseRule(Integer.parseInt(purchaseRuleId));
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
	@RequestMapping(value = "/purchaseRule/getPurchaseRuleById")
	public @ResponseBody Object getPurchaseRuleById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseRuleId = request.getParameter("purchaseRuleId");
		if (purchaseRuleId == null || purchaseRuleId.equals("")) {
			rsInfo.setMessage("模板ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		PurchaseRule purchaseRule = null;
		try {
			purchaseRule = purchaseRuleService.getPurchaseRuleByPurchaseRuleId(Integer.parseInt(purchaseRuleId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取模板信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (purchaseRule == null) {
			rsInfo.setMessage("模板信息不存在！");
			rsInfo.setData(new PurchaseRule());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(purchaseRule);
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
	@RequestMapping(value = "/purchaseRule/getPurchaseRuleList")
	public @ResponseBody Object getPurchaseRuleList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String purchaseRuleName = request.getParameter("purchaseRuleName");

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

		if (purchaseRuleName != null && !purchaseRuleName.equals("")) {
			params.put("purchaseRuleName", purchaseRuleName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = purchaseRuleService.countTotal(pageTool);
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

		List<PurchaseRule> purchaseRuleList = null;
		try {
			purchaseRuleList = purchaseRuleService.getPurchaseRuleList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取模板列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (purchaseRuleList.size() == 0) {
			rsInfo.setMessage("模板列表为空！");
			rsInfo.setData(new ArrayList<PurchaseRule>());
			return rsInfo;
		}
		rsInfo.setData(purchaseRuleList);
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
	@RequestMapping(value = "/purchaseRule/deletePurchaseRuleBatch")
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
			count = purchaseRuleService.deleteBatch(idArr);
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
