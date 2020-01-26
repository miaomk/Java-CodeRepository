package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Stock;
import com.techwells.wumei.service.StockService;
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
public class StockController {
	
	@Resource
	private StockService stockService;

	/**
	 * 添加库存SKU
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/stock/addStock")
	public @ResponseBody Object addStock(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String stockName = request.getParameter("stockName");

		if (stockName == null || stockName.equals("")) {
			rsInfo.setMessage("库存SKU名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Stock stock = new Stock();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "stock") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		stock.setCreatedDate(new Date());
		int count = 0;
		try {
			count = stockService.addStock(stock);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加库存SKU异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加库存SKU成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加库存SKU失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改库存SKU信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/stock/modifyStock/{productId}")
	public @ResponseBody Object modifyStock(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,@PathVariable("productId") Integer productId,
			@RequestBody List<Stock> stockList) {
		ResultInfo rsInfo = new ResultInfo();
		
		if (productId == null) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if(stockList == null || stockList.size() ==0) {
			rsInfo.setMessage("商品库存SKU为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}

		int count = 0;
		try {
			count = stockService.modifyStockList(stockList);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改库存SKU异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改库存SKU失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改库存SKU成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除库存SKU
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stock/deleteStock")
	public @ResponseBody Object deleteStock(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String stockId = request.getParameter("stockId");
		if (stockId == null || stockId.equals("")) {
			rsInfo.setMessage("库存SKUId不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = stockService.delStock(Integer.parseInt(stockId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除库存SKU异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除库存SKU成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除库存SKU失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看库存SKU详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stock/getStockById")
	public @ResponseBody Object getStockById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String stockId = request.getParameter("stockId");
		if (stockId == null || stockId.equals("")) {
			rsInfo.setMessage("库存SKUID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Stock stock = null;
		try {
			stock = stockService.getStockByStockId(Integer.parseInt(stockId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取库存SKU信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (stock == null) {
			rsInfo.setMessage("库存SKU信息不存在！");
			rsInfo.setData(new Stock());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(stock);
		rsInfo.setMessage("获取库存SKU成功！");
		return rsInfo;
	}

	/**
	 * @description 获取库存SKU列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stock/getStockList/{productId}")
	public @ResponseBody Object getStockList(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("productId") Integer productId) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String stockId = request.getParameter("stockId");

		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		if (pageSize == null || pageSize.equals("")) {
			
			pageSize = "20";
		}

		if (stockId != null && !stockId.equals("")) {
			params.put("stockId", stockId);
		}
		if (productId != null && !productId.equals("")) {
			params.put("productId", productId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = stockService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取库存SKU总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("库存SKU总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}

		List<Stock> stockList = null;
		try {
			stockList = stockService.getStockList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取库存SKU列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (stockList.size() == 0) {
			rsInfo.setMessage("库存SKU列表为空！");
			rsInfo.setData(new ArrayList<Stock>());
			return rsInfo;
		}
		rsInfo.setData(stockList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取库存SKU列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除库存SKU
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stock/deleteStockBatch")
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
			count = stockService.deleteBatch(idArr);
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
