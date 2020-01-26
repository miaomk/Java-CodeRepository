package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Recommend;
import com.techwells.wumei.domain.rs.RsRecommend;
import com.techwells.wumei.service.RecommendService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class RecommendController {
	
	@Resource
	private RecommendService recommendService;

	/**
	 * 添加商品推荐
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/recommend/addRecommend")
	public ResultInfo addRecommend(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		Integer userId = (Integer) session.getAttribute("userId");

		String startDate = request.getParameter("startDate");
		String endDate =  request.getParameter("endDate");

		//String userId = request.getParameter("userId");
		String[] productIds = request.getParameterValues("productIds");
		
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("商家ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productIds == null || productIds.length<=0) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
			rsInfo.setMessage("时间不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if(productIds[0].contains(",")) {
			
			productIds =  productIds[0].split(",");
		}
		int count;
		try {

			count = recommendService.addRecommend(startDate,endDate,productIds,userId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加商品推荐异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加商品推荐成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改商品推荐信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/modifyRecommend/{recommendId}")
	public ResultInfo modifyRecommend(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,@PathVariable("recommendId") Integer recommendId) {
		ResultInfo rsInfo = new ResultInfo();

		String productId = request.getParameter("productId");

		String userId = request.getParameter("userId");

		String sortIndex = request.getParameter("sortIndex");


		if (recommendId == null || recommendId.equals("")) {
			rsInfo.setMessage("推荐ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (sortIndex == null || sortIndex.equals("")) {
			sortIndex = "0";
		}
		
		Recommend recommend = new Recommend();
		recommend.setRecommendId(recommendId);

		if (productId != null &&! productId.equals("")) {
			recommend.setProductId(Integer.parseInt(productId));
		}

		if (userId != null &&! userId.equals("")) {
			recommend.setUserId(Integer.parseInt(userId));
		}

		recommend.setSortIndex(Integer.parseInt(sortIndex));
		recommend.setUpdatedDate(new Date());
		int count;
		try {
			count = recommendService.modifyRecommend(recommend);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改商品推荐异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改商品推荐成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * 修改商品推荐信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/modifyStatus")
	public ResultInfo modifyStatus(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String activated = request.getParameter("activated");
		
		String[] ids = request.getParameterValues("ids");
		if (ids == null || ids.length<=0) {
			rsInfo.setMessage("商品推荐ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (activated == null || activated.equals("")) {
			rsInfo.setMessage("状态不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if(ids[0].contains(",")) {
			ids = ids[0].split(",");
		}
		Recommend recommend = new Recommend();

		recommend.setUpdatedDate(new Date());
		int count;
		try {
			count = recommendService.modifyRecommendStatus(ids,Boolean.parseBoolean(activated));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改商品推荐异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改商品推荐成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	
	
	/**
	 * Description: 删除商品推荐
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/deleteRecommend")
	public ResultInfo deleteRecommend(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] recommendIds = request.getParameterValues("ids");
		if (recommendIds == null || recommendIds.length<=0) {
			rsInfo.setMessage("商品推荐Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if(recommendIds[0].contains(",")) {
			recommendIds = recommendIds[0].split(",");
		}
		int count;
		try {
			count = recommendService.deleteBatch(recommendIds);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除商品推荐异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除商品推荐成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看商品推荐详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/getRecommendById")
	public ResultInfo getRecommendById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String recommendId = request.getParameter("recommendId");
		if (recommendId == null || recommendId.equals("")) {
			rsInfo.setMessage("商品推荐ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Recommend recommend = null;
		try {
			recommend = recommendService.getRecommendByRecommendId(Integer.parseInt(recommendId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取商品推荐信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (recommend == null) {
			rsInfo.setMessage("商品推荐信息不存在！");
			rsInfo.setData(new Recommend());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(recommend);
		rsInfo.setMessage("获取商品推荐成功！");
		return rsInfo;
	}

	/**
	 * @description 获取商品推荐列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/getRecommendList")
	public ResultInfo getRecommendList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String productName = request.getParameter("productName");
		String activated = request.getParameter("activated");

		if (pageNum == null || "".equals(pageNum)) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || "".equals(pageSize)) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10002");
			return rsInfo;
		}
		if (productName != null && !"".equals(productName)) {
			params.put("productName",productName);
		}
		if (activated != null && !"".equals(activated)) {
			params.put("activated",activated);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = recommendService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品推荐总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("商品推荐总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsRecommend> recommendList = null;
		try {
			recommendList = recommendService.getRecommendList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品推荐列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (recommendList.size() == 0) {
			rsInfo.setMessage("商品推荐列表为空！");
			rsInfo.setData(new ArrayList<RsRecommend>());
			return rsInfo;
		}
		rsInfo.setData(recommendList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取商品推荐列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除商品推荐
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recommend/deleteRecommendBatch")
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
			count = recommendService.deleteBatch(idArr);
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
	 * 添加商品推荐
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/recommend/batchAddRecommend")
	public ResultInfo batchAddRecommend(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		Integer userId = (Integer) session.getAttribute("userId");

		String productIds = request.getParameter("ids");
		String recommendStatus = request.getParameter("recommendStatus");

		if (StringUtil.isEmpty(productIds)) {
			rsInfo.setMessage("商品Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		String[] productIdArrays = productIds.split(",");


		int count = 0;
		try {

			//count = recommendService.addRecommend(productIdArrays,userId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加商品推荐异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加商品推荐成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 批量删除推荐
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/recommend/batchDeleteRecommendByProductId")
	public ResultInfo batchDeleteRecommendByProductId(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();


		String productIds = request.getParameter("ids");

		if (StringUtil.isEmpty(productIds)) {
			rsInfo.setMessage("商品Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		String[] productIdArrays = productIds.split(",");


		int count;
		try {

			count = recommendService.batchDelete(productIdArrays);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除商品推荐异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除商品推荐成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除商品推荐失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

}
