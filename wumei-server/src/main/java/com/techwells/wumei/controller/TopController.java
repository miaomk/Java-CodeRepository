package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Top;
import com.techwells.wumei.domain.rs.RsTop;
import com.techwells.wumei.service.TopService;
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
public class TopController {
	
	@Resource
	private TopService topService;

	/**
	 * 添加榜单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/top/addTop")
	public @ResponseBody Object addTop(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String topName = request.getParameter("topName");

		if (topName == null || topName.equals("")) {
			rsInfo.setMessage("榜单名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Top top = new Top();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "top") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		top.setCreatedDate(new Date());
		int count = 0;
		try {
			count = topService.addTop(top);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加榜单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加榜单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加榜单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改榜单信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top/modifyTop")
	public @ResponseBody Object modifyTop(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String topId = request.getParameter("topId");
		String topName = request.getParameter("topName");

		if (topId == null || topId.equals("")) {
			rsInfo.setMessage("榜单ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (topName == null || topName.equals("")) {
			rsInfo.setMessage("榜单名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Top top = new Top();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "top") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		top.setTopId(Integer.parseInt(topId));
		top.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = topService.modifyTop(top);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改榜单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改榜单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改榜单成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除榜单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top/deleteTop")
	public @ResponseBody Object deleteTop(HttpServletRequest request,
										  HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String topId = request.getParameter("ids");
		if (topId == null || topId.equals("")) {
			rsInfo.setMessage("榜单Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = topService.delTop(Integer.parseInt(topId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除榜单异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除榜单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除榜单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看榜单详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top/getTopById")
	public @ResponseBody Object getTopById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String topId = request.getParameter("topId");
		if (topId == null || topId.equals("")) {
			rsInfo.setMessage("榜单ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Top top = null;
		try {
			top = topService.getTopByTopId(Integer.parseInt(topId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取榜单信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (top == null) {
			rsInfo.setMessage("榜单信息不存在！");
			rsInfo.setData(new Top());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(top);
		rsInfo.setMessage("获取榜单成功！");
		return rsInfo;
	}

	/**
	 * @description 获取榜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top/getTopList")
	public @ResponseBody Object getTopList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String topName = request.getParameter("productName");

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

		if (topName != null && !"".equals(topName)) {
			params.put("topName", topName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = topService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取榜单总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("榜单总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsTop> topList = null;
		try {
			topList = topService.getTopList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取榜单列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (topList.size() == 0) {
			rsInfo.setMessage("榜单列表为空！");
			rsInfo.setData(new ArrayList<Top>());
			return rsInfo;
		}
		rsInfo.setData(topList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取榜单列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除榜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top/deleteTopBatch")
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
			count = topService.deleteBatch(idArr);
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

	@ResponseBody
	@RequestMapping("/top/batchAdd")
	public ResultInfo batchAddTop(HttpServletRequest request){
		ResultInfo resultInfo = new ResultInfo();
		String selectProducts = request.getParameter("productIds");

		String startDate = request.getParameter("startDate");
		String endDate =  request.getParameter("endDate");

		String[] topIdArrays = selectProducts.split(",");

		int count;

		try {
			count = topService.batchAddTop(topIdArrays,startDate,endDate);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("添加榜单异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (count < 1) {
			resultInfo.setMessage("添加榜单失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setMessage("添加榜单成功！");
		resultInfo.setData(count);
		return resultInfo;
	}

	@ResponseBody
	@RequestMapping("/top/updateSort/{topId}")
	public ResultInfo updateSortTop(HttpServletRequest request,
									@PathVariable("topId")String topId){

		ResultInfo resultInfo = new ResultInfo();

		String sort = request.getParameter("sort");

		if (null == sort || "".equals(sort)) {

			resultInfo.setMessage("榜单排序不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}
		int count;
		try {
			count = topService.updateSort(topId,sort);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("添加榜单异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (count < 1) {
			resultInfo.setMessage("添加榜单失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setMessage("添加榜单成功！");
		resultInfo.setData(count);
		return resultInfo;
	}

	@ResponseBody
	@RequestMapping("/top/batchUpdateRecommendStatus")
	public ResultInfo batchUpdateStatus(HttpServletRequest request){

		ResultInfo resultInfo = new ResultInfo();
		String recommendStatus = request.getParameter("recommendStatus");
		String ids = request.getParameter("ids");

		if ("".equals(recommendStatus) || null == recommendStatus) {
			resultInfo.setMessage("榜单状态不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		if ("".equals(ids) || null == ids) {
			resultInfo.setMessage("榜单id不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		String[] idArrays = ids.split(",");
		int count = 0;

		try{

			count = topService.batchUpdateStatus(idArrays,recommendStatus);

		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("更改推荐状态异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (count < 1) {
			resultInfo.setMessage("更改推荐状态失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setMessage("更改推荐状态 成功！");
		resultInfo.setData(count);
		return resultInfo;
	}
}
