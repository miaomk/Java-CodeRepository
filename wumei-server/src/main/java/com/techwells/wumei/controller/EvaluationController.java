package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Evaluation;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.domain.rs.RsMerchantEvaluation;
import com.techwells.wumei.service.EvaluationService;
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
import java.util.*;

/**
 * 商品评价Controller
 *
 * @author miao
 */
@RestController
public class EvaluationController {

	@Resource
	private EvaluationService evaluationService;

	/**
	 * 添加评价
	 * @param request
	 * @param files
	 * @return
	 */

	@RequestMapping(value = "/evaluation/addEvaluation")
	public ResultInfo addEvaluation(HttpServletRequest request,
									@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo resultInfo = new ResultInfo();

		String content = request.getParameter("content");
		String orderId = request.getParameter("orderId");
		String userId = request.getParameter("userId");
		String productScore = request.getParameter("productScore");
		String expressScore = request.getParameter("expressScore");
		String serviceScore = request.getParameter("serviceScore");

		String evaluationLevel = request.getParameter("evaluationLevel");

		if (userId == null || "".equals(userId)) {
			resultInfo.setMessage("未登录！");
			resultInfo.setCode("9999");
			return resultInfo;
		}
		if (content == null || "".equals(content)) {
			resultInfo.setMessage("评价内容不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		Evaluation evaluation = new Evaluation();
		evaluation.setUserId(Integer.parseInt(userId));
		evaluation.setOrderId(Integer.parseInt(orderId));

		if (productScore != null && !"".equals(productScore)) {
			evaluation.setProductScore(Integer.parseInt(productScore));
		}
		if (expressScore != null && !"".equals(expressScore)) {
			evaluation.setExpressScore(Integer.parseInt(expressScore));
		}
		if (serviceScore != null && !"".equals(serviceScore)) {
			evaluation.setServiceScore(Integer.parseInt(serviceScore));
		}
        if (evaluationLevel != null && !"".equals(evaluationLevel)) {
            evaluation.setEvaluationLevel(Integer.parseInt(evaluationLevel));
        }
		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "evaluation") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
			evaluation.setImageUrl(fileUrl);
		}

		evaluation.setCreatedDate(new Date());
		int count;
		try {
			count = evaluationService.addEvaluation(evaluation);
			//TODO添加积分
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("添加评价异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (count > 0) {
			resultInfo.setMessage("添加评价成功！");
			resultInfo.setData(count);
		} else {
			resultInfo.setMessage("添加评价失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * 修改评价信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/evaluation/modifyEvaluation")
	public ResultInfo modifyEvaluation(HttpServletRequest request,
									   @RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo resultInfo = new ResultInfo();

		String evaluationId = request.getParameter("evaluationId");
		String content = request.getParameter("content");

		if (StringUtil.isEmpty(evaluationId)) {
			resultInfo.setMessage("评价ID不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		if (content == null || "".equals(content)) {
			resultInfo.setMessage("评价内容不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		Evaluation evaluation = new Evaluation();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "evaluation") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		evaluation.setEvaluationId(Integer.parseInt(evaluationId));
		evaluation.setUpdatedDate(new Date());
		int count;
		try {
			count = evaluationService.modifyEvaluation(evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("修改评价异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (count < 1) {
			resultInfo.setMessage("修改评价失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		resultInfo.setMessage("修改评价成功！");
		resultInfo.setData(count);
		return resultInfo;
	}

	/**
	 * Description: 删除评价
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/evaluation/deleteEvaluation")
	public ResultInfo deleteEvaluation(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String evaluationId = request.getParameter("evaluationId");
		if (evaluationId == null || evaluationId.equals("")) {
			rsInfo.setMessage("评价Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = evaluationService.delEvaluation(Integer.parseInt(evaluationId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除评价异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除评价成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除评价失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看评价详情
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/evaluation/getEvaluationById")
	public ResultInfo getEvaluationById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String evaluationId = request.getParameter("evaluationId");
		if (evaluationId == null || evaluationId.equals("")) {
			rsInfo.setMessage("评价ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Evaluation evaluation = null;
		try {
			evaluation = evaluationService.getEvaluationByEvaluationId(Integer.parseInt(evaluationId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取评价信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (evaluation == null) {
			rsInfo.setMessage("评价信息不存在！");
			rsInfo.setData(new Evaluation());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(evaluation);
		rsInfo.setMessage("获取评价成功！");
		return rsInfo;
	}

	/**
	 * @description 获取评价列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/evaluation/getEvaluationList")
	public ResultInfo getEvaluationList(HttpServletRequest request,
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
		int totalCount;

		try {
			totalCount = evaluationService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取评价总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("评价总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsEvaluation> evaluationList;
		try {
			evaluationList = evaluationService.getEvaluationList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取评价列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (evaluationList.size() == 0) {
			rsInfo.setMessage("评价列表为空！");
			rsInfo.setData(new ArrayList<RsEvaluation>());
			return rsInfo;
		}
		rsInfo.setData(evaluationList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取评价列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除评价
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/evaluation/deleteEvaluationBatch")
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
			count = evaluationService.deleteBatch(idArr);
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
	@RequestMapping(value = "/evaluation/getMerchantEvaluation")
	public ResultInfo getEvaluationService(@RequestParam("merchantId") Integer merchantId,
										   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
										   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
										   @RequestParam(value = "haveImage", required = false) Integer haveImage,
										   @RequestParam(value = "orderByTime", required = false) Integer orderByTime) {
		ResultInfo resultInfo = new ResultInfo();
		RsMerchantEvaluation rsMerchantEvaluation;
		try {
			PagingTool pagingTool = new PagingTool(pageNum, pageSize);
			Map<String, Object> map = new HashMap<>(16);
			map.put("merchantId", merchantId);
			if (null != haveImage) {
				map.put("haveImage", haveImage);
			}
			if (null != orderByTime) {
				map.put("orderByTime", orderByTime);
			}
			pagingTool.setParams(map);

			rsMerchantEvaluation = evaluationService.getMerchantEvaluationList(pagingTool);
		} catch (Exception e) {
			e.printStackTrace();

			resultInfo.setMessage("获取店铺评价异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setData(rsMerchantEvaluation);
		return resultInfo;
	}
}
