package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Feedback;
import com.techwells.wumei.service.FeedbackService;
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
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class FeedbackController {
	
	@Resource
	private FeedbackService feedbackService;

	/**
	 * 添加反馈
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/feedback/addFeedback")
	public @ResponseBody Object addFeedback(HttpServletRequest request,
											@RequestParam(value = "file", required = false) MultipartFile file) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String content = request.getParameter("content");
		

		if (userId == null || "".equals(userId)) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setUserId(Integer.parseInt(userId));
		

		// 处理图片
		if (file != null) {
			String fileUrl = "";
			
				try {
					fileUrl += FileUtil.uploadFile(file, "feedback");
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);

			}
		}

		int count;
		try {
			count = feedbackService.addFeedback(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加反馈异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加反馈成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加反馈失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改反馈信息
	 * 
	 * @param request
	 * @return
	 */
	@PutMapping(value = "/feedback/modifyFeedback/{feedbackId}")
	public @ResponseBody Object modifyFeedback(HttpServletRequest request,
											   @RequestParam(value = "file", required = false) MultipartFile[] files,@PathVariable("feedbackId") Integer feedbackId) {
		ResultInfo rsInfo = new ResultInfo();

		String replyContent = request.getParameter("replyContent");


		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		feedback.setReplyContent(replyContent);

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "feedback") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		
		int count = 0;
		try {
			count = feedbackService.modifyFeedback(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改反馈异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改反馈失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改反馈成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除反馈
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/feedback/deleteFeedback/{feedbackId}")
	public @ResponseBody Object deleteFeedback(HttpServletRequest request,
			HttpSession session, HttpServletResponse response, @PathVariable("feedbackId") Integer feedbackId) {
		ResultInfo rsInfo = new ResultInfo();

		
		int count = 0;
		try {
			count = feedbackService.delFeedback(feedbackId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除反馈异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除反馈成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除反馈失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看反馈详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/feedback/getFeedbackById")
	public @ResponseBody Object getFeedbackById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String feedbackId = request.getParameter("feedbackId");
		if (feedbackId == null || feedbackId.equals("")) {
			rsInfo.setMessage("反馈ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Feedback feedback = null;
		try {
			feedback = feedbackService.getFeedbackByFeedbackId(Integer.parseInt(feedbackId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取反馈信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (feedback == null) {
			rsInfo.setMessage("反馈信息不存在！");
			rsInfo.setData(new Feedback());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(feedback);
		rsInfo.setMessage("获取反馈成功！");
		return rsInfo;
	}

	/**
	 * @description 获取反馈列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/feedback/getFeedbackList")
	public @ResponseBody Object getAdminList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String feedbackName = request.getParameter("feedbackName");

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

		if (feedbackName != null && !feedbackName.equals("")) {
			params.put("feedbackName", feedbackName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = feedbackService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取反馈总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("反馈总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Feedback> feedbackList = null;
		try {
			feedbackList = feedbackService.getFeedbackList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取反馈列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (feedbackList.size() == 0) {
			rsInfo.setMessage("反馈列表为空！");
			rsInfo.setData(new ArrayList<Feedback>());
			return rsInfo;
		}
		rsInfo.setData(feedbackList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取反馈列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除反馈
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/feedback/batchDelete")
	public @ResponseBody Object deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] ids = request.getParameterValues("ids");

		if (ids == null || ids.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = feedbackService.batchDelete(ids);
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
