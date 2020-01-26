package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Appeal;
import com.techwells.wumei.domain.rs.RsAppeal;
import com.techwells.wumei.service.AppealService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.UploadImageUtil;
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

/**
 * 订单申诉Controller
 *
 * @author Administrator
 */
@RestController
public class AppealController {
	
	@Resource
	private AppealService appealService;

	/**
	 * 添加模板
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/appeal/addAppeal")
	public ResultInfo addAppeal(HttpServletRequest request,
										  @RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String appealName = request.getParameter("appealName");

		if (appealName == null || "".equals(appealName)) {
			rsInfo.setMessage("申诉名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Appeal appeal = new Appeal();

		// 处理图片
		if (files != null && files.length > 0) {
			UploadImageUtil.uploadImage("appeal",files);
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "appeal") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		appeal.setCreatedDate(new Date());
		int count;
		try {
			count = appealService.addAppeal(appeal);
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
	@RequestMapping(value = "/appeal/modifyAppeal")
	public ResultInfo modifyAppeal(HttpServletRequest request,
											 @RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String appealId = request.getParameter("appealId");
		String appealName = request.getParameter("appealName");

		if (appealId == null || "".equals(appealId)) {
			rsInfo.setMessage("模板ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (appealName == null || "".equals(appealName)) {
			rsInfo.setMessage("模板名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Appeal appeal = new Appeal();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "appeal") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		appeal.setUpdatedDate(new Date());
		int count;
		try {
			count = appealService.modifyAppeal(appeal);
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
	@RequestMapping(value = "/appeal/deleteAppeal")
	public ResultInfo deleteAppeal(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String appealId = request.getParameter("appealId");
		if (appealId == null || "".equals(appealId)) {
			rsInfo.setMessage("模板Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = appealService.delAppeal(Integer.parseInt(appealId));
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
	@RequestMapping(value = "/appeal/getAppealById")
	public ResultInfo getAppealById(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String appealId = request.getParameter("appealId");
		if (appealId == null || "".equals(appealId)) {
			rsInfo.setMessage("模板ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Appeal appeal;
		try {
			appeal = appealService.getAppealByAppealId(Integer.parseInt(appealId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取模板信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (appeal == null) {
			rsInfo.setMessage("模板信息不存在！");
			rsInfo.setData(new Appeal());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(appeal);
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
	@RequestMapping(value = "/appeal/getAppealList")
	public ResultInfo getAppealList(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String appealName = request.getParameter("appealName");
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		String createTime = request.getParameter("createTime");
		String handleMan = request.getParameter("handleMan");
		String handleTime = request.getParameter("handleTime");

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

		if (appealName != null && !"".equals(appealName)) {
			params.put("appealName", appealName);
		}

		if (id != null && !"".equals(id)) {
			params.put("orderId", id);
		}

		if (status != null && !"".equals(status)) {
			params.put("activated", status);
		}

		if (createTime != null && !"".equals(createTime)) {
			params.put("createTime", createTime);
		}

		if (handleMan != null && !"".equals(handleMan)) {
			params.put("handleMan", handleMan);
		}

		if (handleTime != null && !"".equals(handleTime)) {
			params.put("handleTime", handleTime);
		}


		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = appealService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取申诉列表总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("申诉列表总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsAppeal> appealList;
		try {
			appealList = appealService.getAppealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取申诉列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (appealList.size() == 0) {
			rsInfo.setMessage("申诉列表为空！");
			rsInfo.setData(new ArrayList<Appeal>());
			return rsInfo;
		}
		rsInfo.setData(appealList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取申诉列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除模板
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appeal/deleteAppealBatch")
	public ResultInfo deleteBatch(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");

		if (idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count;
		try {
			count = appealService.deleteBatch(idArr);
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
