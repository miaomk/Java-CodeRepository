package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Product;
import com.techwells.wumei.domain.Purchase;
import com.techwells.wumei.service.ProductService;
import com.techwells.wumei.service.PurchaseService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
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
public class PurchaseController {
	
	@Resource
	private PurchaseService purchaseService;

	@Resource
	private ProductService productService;
	/**
	 * 添加进货
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/purchase/addPurchase")
	public @ResponseBody Object addPurchase(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String productId = request.getParameter("productId");

		String purchasePrice = request.getParameter("purchasePrice");
		String amount = request.getParameter("amount");
		String turnover = request.getParameter("turnover");

		if (StringUtil.isEmpty(productId)) {
			rsInfo.setMessage("进货商品名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}


		if (StringUtil.isEmpty(purchasePrice)) {
			rsInfo.setMessage("进价不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (StringUtil.isEmpty(amount)) {
			rsInfo.setMessage("进货数量称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (StringUtil.isEmpty(turnover)) {
			rsInfo.setMessage("进货总额不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Product product = productService.getProductByProductId(Integer.parseInt(productId));

		if (null == product) {
			rsInfo.setMessage("商品不存在为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Integer stock = product.getStock() + Integer.parseInt(amount);
		product.setStock(stock);

		Purchase purchase = new Purchase();

		purchase.setProductId(Integer.parseInt(productId));
		purchase.setPurchasePrice(Double.parseDouble(purchasePrice));
		purchase.setAmount(Integer.parseInt(amount));
		purchase.setTurnover(Double.parseDouble(turnover));

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "purchase") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		purchase.setCreatedDate(new Date());
		int count = 0;
		try {
			count = purchaseService.addPurchase(purchase);
			//更新商品库存
			 productService.modifyProduct(product);

		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加进货异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加进货成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加进货失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改进货信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/purchase/modifyPurchase")
	public @ResponseBody Object modifyPurchase(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseId = request.getParameter("purchaseId");
		String purchaseName = request.getParameter("purchaseName");

		if (purchaseId == null || purchaseId.equals("")) {
			rsInfo.setMessage("进货ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (purchaseName == null || purchaseName.equals("")) {
			rsInfo.setMessage("进货名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Purchase purchase = new Purchase();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "purchase") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		purchase.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = purchaseService.modifyPurchase(purchase);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改进货异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改进货失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改进货成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除进货
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/purchase/deletePurchase")
	public @ResponseBody Object deletePurchase(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseId = request.getParameter("purchaseId");
		if (purchaseId == null || purchaseId.equals("")) {
			rsInfo.setMessage("进货Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = purchaseService.delPurchase(Integer.parseInt(purchaseId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除进货异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除进货成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除进货失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看进货详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/purchase/getPurchaseById")
	public @ResponseBody Object getPurchaseById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String purchaseId = request.getParameter("purchaseId");
		if (purchaseId == null || purchaseId.equals("")) {
			rsInfo.setMessage("进货ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Purchase purchase = null;
		try {
			purchase = purchaseService.getPurchaseByPurchaseId(Integer.parseInt(purchaseId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取进货信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (purchase == null) {
			rsInfo.setMessage("进货信息不存在！");
			rsInfo.setData(new Purchase());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(purchase);
		rsInfo.setMessage("获取进货成功！");
		return rsInfo;
	}

	/**
	 * @description 获取进货列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/purchase/getPurchaseList")
	public @ResponseBody Object getPurchaseList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String purchaseName = request.getParameter("keyword");

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

		if (purchaseName != null && !purchaseName.equals("")) {
			params.put("purchaseName", purchaseName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = purchaseService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取进货总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("进货总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Purchase> purchaseList = null;
		try {
			purchaseList = purchaseService.getPurchaseList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取进货列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (purchaseList.size() == 0) {
			rsInfo.setMessage("进货列表为空！");
			rsInfo.setData(new ArrayList<Purchase>());
			return rsInfo;
		}
		rsInfo.setData(purchaseList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取进货列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除进货
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/purchase/deletePurchaseBatch")
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
			count = purchaseService.deleteBatch(idArr);
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
