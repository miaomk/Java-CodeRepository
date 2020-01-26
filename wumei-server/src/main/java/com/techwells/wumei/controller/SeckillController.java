package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Seckill;
import com.techwells.wumei.domain.rs.RsSeckill;
import com.techwells.wumei.service.SeckillService;
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
public class SeckillController {
	
	@Resource
	private SeckillService seckillService;

	/**
	 * 添加抢购
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/seckill/addSeckill")
	public @ResponseBody Object addSeckill(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String productId = request.getParameter("productId");
		String originalPrice = request.getParameter("originalPrice");
		String currentPrice = request.getParameter("currentPrice");
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
			rsInfo.setMessage("秒杀价格不能为空！");
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

		Seckill seckill = new Seckill();
		seckill.setProductId(Integer.parseInt(productId));
		seckill.setOriginalPrice(Double.parseDouble(originalPrice));
		seckill.setCurrentPrice(Double.parseDouble(currentPrice));
		seckill.setProvideNumber(Integer.parseInt(provideNumber));

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "seckill") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		seckill.setCreatedDate(new Date());
		int count = 0;
		try {
			count = seckillService.addSeckill(seckill);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加抢购异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加抢购成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加抢购失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改抢购信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/modifySeckill")
	public @ResponseBody Object modifySeckill(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String seckillId = request.getParameter("seckillId");
		String seckillName = request.getParameter("seckillName");

		if (seckillId == null || seckillId.equals("")) {
			rsInfo.setMessage("抢购ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (seckillName == null || seckillName.equals("")) {
			rsInfo.setMessage("抢购名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Seckill seckill = new Seckill();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "seckill") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		seckill.setSeckillId(Integer.parseInt(seckillId));
		seckill.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = seckillService.modifySeckill(seckill);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改抢购异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改抢购失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改抢购成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除抢购
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/deleteSeckill")
	public @ResponseBody Object deleteSeckill(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String seckillId = request.getParameter("seckillId");
		if (seckillId == null || seckillId.equals("")) {
			rsInfo.setMessage("抢购Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = seckillService.delSeckill(Integer.parseInt(seckillId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除抢购异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除抢购成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除抢购失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看抢购详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/getSeckillById")
	public @ResponseBody Object getSeckillById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String seckillId = request.getParameter("seckillId");
		if (seckillId == null || seckillId.equals("")) {
			rsInfo.setMessage("抢购ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Seckill seckill = null;
		try {
			seckill = seckillService.getSeckillBySeckillId(Integer.parseInt(seckillId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取抢购信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (seckill == null) {
			rsInfo.setMessage("抢购信息不存在！");
			rsInfo.setData(new Seckill());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(seckill);
		rsInfo.setMessage("获取抢购成功！");
		return rsInfo;
	}

	/**
	 * @description 获取抢购列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/getSeckillList")
	public @ResponseBody Object getSeckillList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String seckillName = request.getParameter("seckillName");

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

		if (seckillName != null && !seckillName.equals("")) {
			params.put("seckillName", seckillName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = seckillService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取抢购总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("抢购总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsSeckill> seckillList = null;
		try {
			seckillList = seckillService.getSeckillList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取抢购列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (seckillList.size() == 0) {
			rsInfo.setMessage("抢购列表为空！");
			rsInfo.setData(new ArrayList<RsSeckill>());
			return rsInfo;
		}
		rsInfo.setData(seckillList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取抢购列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除抢购
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/deleteSeckillBatch")
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
			count = seckillService.deleteBatch(idArr);
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
	 * @description 获取抢购列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seckill/getAll")
	public @ResponseBody Object getSeckillAll(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		List<RsSeckill> seckillList = null;
		try {
			seckillList = seckillService.getAll();
		} catch (Exception e) {
			rsInfo.setMessage("获取抢购列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (seckillList.size() == 0) {
			rsInfo.setMessage("抢购列表为空！");
			rsInfo.setTotal(0);
			rsInfo.setData(new ArrayList<RsSeckill>());
			return rsInfo;
		}
		rsInfo.setData(seckillList);
		rsInfo.setTotal(seckillList.size());
		rsInfo.setMessage("获取抢购列表成功！");
		return rsInfo;
	}

	
	
	
}
