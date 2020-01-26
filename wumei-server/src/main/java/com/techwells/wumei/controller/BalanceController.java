package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Balance;
import com.techwells.wumei.service.BalanceService;
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
public class BalanceController {
	
	@Resource
	private BalanceService balanceService;

	/**
	 * 添加对账
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/balance/addBalance")
	public @ResponseBody Object addBalance(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String balanceName = request.getParameter("balanceName");

		if (balanceName == null || balanceName.equals("")) {
			rsInfo.setMessage("对账名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Balance balance = new Balance();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "balance") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		balance.setCreatedDate(new Date());
		int count = 0;
		try {
			count = balanceService.addBalance(balance);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加对账异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加对账成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加对账失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改对账信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/balance/modifyBalance")
	public @ResponseBody Object modifyBalance(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String balanceId = request.getParameter("balanceId");
		String balanceName = request.getParameter("balanceName");

		if (balanceId == null || balanceId.equals("")) {
			rsInfo.setMessage("对账ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (balanceName == null || balanceName.equals("")) {
			rsInfo.setMessage("对账名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Balance balance = new Balance();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "balance") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		balance.setBalanceId(Integer.parseInt(balanceId));
		balance.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = balanceService.modifyBalance(balance);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改对账异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改对账失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改对账成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除对账
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/balance/deleteBalance")
	public @ResponseBody Object deleteBalance(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String balanceId = request.getParameter("balanceId");
		if (balanceId == null || balanceId.equals("")) {
			rsInfo.setMessage("对账Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = balanceService.delBalance(Integer.parseInt(balanceId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除对账异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除对账成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除对账失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看对账详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/balance/getBalanceById")
	public @ResponseBody Object getBalanceById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String balanceId = request.getParameter("balanceId");
		if (balanceId == null || balanceId.equals("")) {
			rsInfo.setMessage("对账ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Balance balance = null;
		try {
			balance = balanceService.getBalanceByBalanceId(Integer.parseInt(balanceId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取对账信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (balance == null) {
			rsInfo.setMessage("对账信息不存在！");
			rsInfo.setData(new Balance());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(balance);
		rsInfo.setMessage("获取对账成功！");
		return rsInfo;
	}

	/**
	 * @description 获取对账列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/balance/getBalanceList")
	public @ResponseBody Object getBalanceList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String merchantId = request.getParameter("merchantId");

		if (pageNum == null || pageNum.equals("")) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || pageSize.equals("")) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}

		if (merchantId != null && !merchantId.equals("")) {
			params.put("merchantId", merchantId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = balanceService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取对账总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("对账总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Balance> balanceList = null;
		try {
			balanceList = balanceService.getBalanceList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取对账列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (balanceList.size() == 0) {
			rsInfo.setMessage("对账列表为空！");
			rsInfo.setData(new ArrayList<Balance>());
			return rsInfo;
		}
		rsInfo.setData(balanceList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取对账列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除对账
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/balance/deleteBalanceBatch")
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
			count = balanceService.deleteBatch(idArr);
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
