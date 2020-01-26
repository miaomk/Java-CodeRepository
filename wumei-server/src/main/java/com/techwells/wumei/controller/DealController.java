package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Deal;
import com.techwells.wumei.service.DealService;
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


@RestController
public class DealController {
	
	@Resource
	private DealService dealService;

	/**
	 * 添加交易
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/deal/addDeal")
	public ResultInfo addDeal(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String dealName = request.getParameter("dealName");

		if (dealName == null || "".equals(dealName)) {
			rsInfo.setMessage("交易名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Deal deal = new Deal();


		int count;
		try {
			count = dealService.addDeal(deal);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加交易异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加交易成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加交易失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改交易信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/modifyDeal")
	public ResultInfo modifyDeal(HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String dealId = request.getParameter("dealId");
		String dealName = request.getParameter("dealName");

		if (dealId == null || dealId.equals("")) {
			rsInfo.setMessage("交易ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (dealName == null || dealName.equals("")) {
			rsInfo.setMessage("交易名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Deal deal = new Deal();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "deal") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		deal.setDealId(Integer.parseInt(dealId));

		int count;
		try {
			count = dealService.modifyDeal(deal);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改交易异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改交易失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改交易成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除交易
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/deleteDeal")
	public ResultInfo deleteDeal(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String dealId = request.getParameter("dealId");
		if (dealId == null || dealId.equals("")) {
			rsInfo.setMessage("交易Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = dealService.delDeal(Integer.parseInt(dealId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除交易异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除交易成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除交易失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看交易详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/getDealById")
	public ResultInfo getDealById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String dealId = request.getParameter("dealId");
		if (dealId == null || dealId.equals("")) {
			rsInfo.setMessage("交易ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Deal deal;
		try {
			deal = dealService.getDealByDealId(Integer.parseInt(dealId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取交易信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (deal == null) {
			rsInfo.setMessage("交易信息不存在！");
			rsInfo.setData(new Deal());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(deal);
		rsInfo.setMessage("获取交易成功！");
		return rsInfo;
	}

	/**
	 * @description 获取交易列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/getDealList")
	public ResultInfo getDealList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String dealName = request.getParameter("dealName");

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

		if (dealName != null && !dealName.equals("")) {
			params.put("dealName", dealName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = dealService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("交易总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Deal> dealList;
		try {
			dealList = dealService.getDealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (dealList.size() == 0) {
			rsInfo.setMessage("交易列表为空！");
			rsInfo.setData(new ArrayList<Deal>());
			return rsInfo;
		}
		rsInfo.setData(dealList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取交易列表成功！");
		return rsInfo;
	}
	
	
	
	/**
	 * @description 获取交易列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/getMyDealList")
	public ResultInfo getMyDealList(HttpServletRequest request,
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
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
			
		}else{
			params.put("userId", userId);
		}
		
		params.put("isBalanceRelation", 1);  //跟余额有关

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = dealService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("交易总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Deal> dealList;
		try {
			dealList = dealService.getDealList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取交易列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (dealList.size() == 0) {
			rsInfo.setMessage("交易列表为空！");
			rsInfo.setData(new ArrayList<Deal>());
			return rsInfo;
		}
		rsInfo.setData(dealList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取交易列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除交易
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deal/deleteDealBatch")
	public ResultInfo deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count;
		try {
			count = dealService.deleteBatch(idArr);
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
	
	
	/**
	 * 添加交易
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/deal/applyWithdraw")
	public ResultInfo withdraw(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
	    
		
		String userId = request.getParameter("userId");
		
		String account = request.getParameter("account");
		
		String realName = request.getParameter("realName");
		
		String money = request.getParameter("money");


		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("提现用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (account == null || account.equals("")) {
			rsInfo.setMessage("提现账户不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (realName == null || realName.equals("")) {
			rsInfo.setMessage("提现姓名不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (money == null || money.equals("")) {
			rsInfo.setMessage("提现金额不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Deal deal = new Deal();
		deal.setDealType(2);
		deal.setDealStatus(2);
		deal.setUserId(Integer.parseInt(userId));
		


		int count;
		try {
			count = dealService.addDeal(deal);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("提现申请异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("提现申请成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("提现申请失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}
}
