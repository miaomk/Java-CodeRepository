package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Groupon;
import com.techwells.wumei.domain.rs.RsGroupon;
import com.techwells.wumei.service.GrouponService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 团购Controller
 */
@Controller
//@RequestMapping(value = "*.do")
@RestController
public class GrouponController {
	
	@Resource
	private GrouponService grouponService;


	/**
	 * 添加拼团规则
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/groupon/addGroupon")
	public @ResponseBody Object addGroupon(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,@RequestBody Groupon groupon) {
		ResultInfo rsInfo = new ResultInfo();

		Integer productId = groupon.getProductId();
		if (productId == null ) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		groupon.setCreatedDate(new Date());
		int count;
		try {
			count = grouponService.addGroupon(groupon);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加拼团规则异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加拼团规则成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加拼团规则失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改拼团规则信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupon/modifyGroupon/{ruldId}")
	public @ResponseBody Object modifyGroupon(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,@PathVariable("ruleId")Integer ruleId, @RequestBody Groupon groupon) {
		ResultInfo rsInfo = new ResultInfo();

		Integer productId = groupon.getProductId();

		if (productId == null ) {
			rsInfo.setMessage("拼团商品ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		
		groupon.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = grouponService.modifyGroupon(groupon);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改拼团规则异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改拼团规则失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改拼团规则成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除拼团规则
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupon/deleteGroupon/{ruleId}")
	public @ResponseBody Object deleteGroupon(HttpServletRequest request,
			HttpSession session, HttpServletResponse response,@PathVariable("ruleId") Integer ruleId) {
		ResultInfo rsInfo = new ResultInfo();

		int count = 0;
		try {
			count = grouponService.delGroupon(ruleId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除拼团规则异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除拼团规则成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除拼团规则失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看拼团规则详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupon/getGrouponById/{ruleId}")
	public @ResponseBody Object getGrouponById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response,@PathVariable("ruleId") Integer ruleId) {
		ResultInfo rsInfo = new ResultInfo();

		Groupon groupon = null;
		try {
			groupon = grouponService.getGrouponByGrouponId(ruleId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取拼团规则信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (groupon == null) {
			rsInfo.setMessage("拼团规则信息不存在！");
			rsInfo.setData(new Groupon());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(groupon);
		rsInfo.setMessage("获取拼团规则成功！");
		return rsInfo;
	}

	/**
	 * @description 获取拼团规则列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupon/getGrouponList")
	public @ResponseBody Object getGrouponList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String productId = request.getParameter("productId");

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

		if (productId != null && !"".equals(productId)) {
			params.put("productId", productId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = grouponService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取拼团规则总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("拼团规则总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsGroupon> grouponList = null;
		try {
			grouponList = grouponService.getGrouponList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取拼团规则列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (grouponList.size() == 0) {
			rsInfo.setMessage("拼团规则列表为空！");
			rsInfo.setData(new ArrayList<RsGroupon>());
			return rsInfo;
		}
		rsInfo.setData(grouponList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取拼团规则列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除拼团规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupon/batchDelete")
	public @ResponseBody Object deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] ids = request.getParameterValues("ids");

		if (ids == null || ids.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = grouponService.deleteBatch(ids);
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
