package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Exclusive;
import com.techwells.wumei.domain.rs.RsExclusive;
import com.techwells.wumei.service.ExclusiveService;
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
public class ExclusiveController {
	
	@Resource
	private ExclusiveService exclusiveService;

	/**
	 * 添加模板
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/exclusive/addExclusive")
	public @ResponseBody Object addExclusive(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String productId = request.getParameter("productId");
		String originalPrice = request.getParameter("originalPrice");
		String currentPrice = request.getParameter("currentPrice");
		String minimumLevel = request.getParameter("minimumLevel");
		String quantityRestrictions = request.getParameter("quantityRestrictions");
		String provideNumber = request.getParameter("provideNumber");
		String sortIndex = request.getParameter("sortIndex");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		productId = "111";

		if (productId == null || productId.equals("")) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (originalPrice == null || originalPrice.equals("")) {
			rsInfo.setMessage("初始价格不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (currentPrice == null || currentPrice.equals("")) {
			rsInfo.setMessage("专属价格不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (minimumLevel == null || minimumLevel.equals("")) {
			rsInfo.setMessage("最小会员等级不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (quantityRestrictions == null || quantityRestrictions.equals("")) {
			rsInfo.setMessage("数量限制不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (provideNumber == null || provideNumber.equals("")) {
			rsInfo.setMessage("提供数量不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (sortIndex == null || sortIndex.equals("")) {
			sortIndex = "0";
		}

		Exclusive exclusive = new Exclusive();
		exclusive.setProductId(Integer.parseInt(productId));
		exclusive.setOriginalPrice(Double.parseDouble(originalPrice));
		exclusive.setCurrentPrice(Double.parseDouble(currentPrice));
		exclusive.setMinimumLevel(Integer.parseInt(minimumLevel));
		exclusive.setQuantityRestrictions(Integer.parseInt(quantityRestrictions));
		exclusive.setProvideNumber(Integer.parseInt(provideNumber));
		//exclusive.setStartTime(new Date(Long.parseLong(startTime)));
		

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "exclusive") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		exclusive.setCreatedDate(new Date());
		int count = 0;
		try {
			count = exclusiveService.addExclusive(exclusive);
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
	@RequestMapping(value = "/exclusive/modifyExclusive/{exclusiveId}")
	public @ResponseBody Object modifyExclusive(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@PathVariable("exclusiveId") Integer exclusiveId) {
		ResultInfo rsInfo = new ResultInfo();

		String exclusiveName = request.getParameter("exclusiveName");

		if (exclusiveName == null || exclusiveName.equals("")) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Exclusive exclusive = new Exclusive();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "exclusive") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		exclusive.setExclusiveId(exclusiveId);
		exclusive.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = exclusiveService.modifyExclusive(exclusive);
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
	@RequestMapping(value = "/exclusive/deleteExclusive/{exclusiveId}")
	public @ResponseBody Object deleteExclusive(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("exclusiveId") Integer exclusiveId) {
		ResultInfo rsInfo = new ResultInfo();

		int count = 0;
		try {
			count = exclusiveService.delExclusive(exclusiveId);
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
	@RequestMapping(value = "/exclusive/getExclusiveById")
	public @ResponseBody Object getExclusiveById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String exclusiveId = request.getParameter("exclusiveId");
		if (exclusiveId == null || exclusiveId.equals("")) {
			rsInfo.setMessage("模板ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Exclusive exclusive = null;
		try {
			exclusive = exclusiveService.getExclusiveByExclusiveId(Integer.parseInt(exclusiveId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取模板信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (exclusive == null) {
			rsInfo.setMessage("模板信息不存在！");
			rsInfo.setData(new Exclusive());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(exclusive);
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
	@RequestMapping(value = "/exclusive/getExclusiveList")
	public @ResponseBody Object getExclusiveList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String exclusiveName = request.getParameter("exclusiveName");

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

		if (exclusiveName != null && !exclusiveName.equals("")) {
			params.put("exclusiveName", exclusiveName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = exclusiveService.countTotal(pageTool);
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

		List<RsExclusive> exclusiveList = null;
		try {
			exclusiveList = exclusiveService.getExclusiveList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取模板列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (exclusiveList.size() == 0) {
			rsInfo.setMessage("模板列表为空！");
			rsInfo.setData(new ArrayList<Exclusive>());
			return rsInfo;
		}
		rsInfo.setData(exclusiveList);
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
	@RequestMapping(value = "/exclusive/deleteExclusiveBatch")
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
			count = exclusiveService.deleteBatch(idArr);
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
