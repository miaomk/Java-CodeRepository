package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Join;
import com.techwells.wumei.domain.rs.RsJoin;
import com.techwells.wumei.service.JoinService;
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
public class JoinController {
	
	@Resource
	private JoinService joinService;
	
	

	/**
	 * 添加拼团
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/join/addJoin")
	public @ResponseBody Object addJoin(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String joinName = request.getParameter("joinName");

		if (joinName == null || "".equals(joinName)) {
			rsInfo.setMessage("拼团名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Join join = new Join();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "join") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		join.setCreatedDate(new Date());
		int count;
		try {
			count = joinService.addJoin(join);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加拼团异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加拼团成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加拼团失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改拼团信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/join/modifyJoin")
	public @ResponseBody Object modifyJoin(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String ruleId = request.getParameter("ruleId");
		String joinName = request.getParameter("joinName");

		if (ruleId == null || ruleId.equals("")) {
			rsInfo.setMessage("拼团ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (joinName == null || joinName.equals("")) {
			rsInfo.setMessage("拼团名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Join join = new Join();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "join") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		join.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = joinService.modifyJoin(join);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改拼团异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改拼团失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改拼团成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除拼团
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/join/deleteJoin")
	public @ResponseBody Object deleteJoin(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String ruleId = request.getParameter("ruleId");
		if (ruleId == null || "".equals(ruleId)) {
			rsInfo.setMessage("拼团Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = joinService.delJoin(Integer.parseInt(ruleId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除拼团异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除拼团成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除拼团失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}


	/**
	 *  查看拼团详情
	 * @param joinId
	 * @return
	 */
	@RequestMapping(value = "/join/getJoinById/{joinId}")
	public @ResponseBody Object getJoinById(@PathVariable("joinId") Integer joinId) {
		ResultInfo rsInfo = new ResultInfo();

		RsJoin join;
		try {
			join = joinService.getJoinByJoinId(joinId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取拼团信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (join == null) {
			rsInfo.setMessage("拼团信息不存在！");
			rsInfo.setData(new Join());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(join);
		rsInfo.setMessage("获取拼团成功！");
		return rsInfo;
	}

	/**
	 * @description 获取团购规则列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/join/getJoinList")
	public @ResponseBody Object getJoinList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

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

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = joinService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取拼团总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("拼团总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsJoin> ruleList = null;
		try {
			ruleList= joinService.getJoinList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取拼团列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (ruleList.size() == 0) {
			rsInfo.setMessage("拼团列表为空！");
			rsInfo.setData(new ArrayList<Join>());
			return rsInfo;
		}
		rsInfo.setData(ruleList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取拼团列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除拼团
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/join/deleteJoinBatch")
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
			count = joinService.deleteBatch(idArr);
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
