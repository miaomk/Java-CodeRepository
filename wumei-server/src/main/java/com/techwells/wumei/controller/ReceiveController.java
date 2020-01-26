package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.domain.Receive;
import com.techwells.wumei.domain.rs.RsReceive;
import com.techwells.wumei.service.CouponService;
import com.techwells.wumei.service.ReceiveService;
import com.techwells.wumei.util.DateUtil;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
public class ReceiveController {

	@Resource
	private ReceiveService receiveService;

	@Autowired
	private CouponService couponService;

	/**
	 * 添加领取优惠券记录
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@PostMapping(value = "/receive/addReceive")
	public ResultInfo addReceive(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		String couponId = request.getParameter("couponId");
		String userId = request.getParameter("userId");
		String receiveType = request.getParameter("receiveType");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}
		if (couponId == null || couponId.equals("")) {
			rsInfo.setMessage("优惠券ID不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}

		Coupon coupon = couponService.getCouponByCouponId(Integer.parseInt(couponId));
		if (coupon == null) {
			rsInfo.setMessage("该优惠券不存在！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		// 当前已领取数量和总数量比较
		Integer receiveCount = coupon.getReceiveCount();
		Integer publishCount = coupon.getPublishCount();
		if ((publishCount != 0) && (publishCount <= receiveCount)) {
			rsInfo.setMessage("优惠券已领完");
			rsInfo.setCode("9998");
			return rsInfo;
		}

		// 当前用户已领取数量和用户限领数量比较
		Integer limit = coupon.getPerLimit();
		Integer userCounpons = receiveService.countUserAndCoupon(Integer.parseInt(userId), Integer.parseInt(couponId));
		if ((limit != 0) && (userCounpons >= limit)) {
			rsInfo.setMessage("已经领取过该优惠券");
			rsInfo.setCode("9997");
			return rsInfo;
		}

		// 优惠券状态，已下架或者过期不能领取
		Date enableTime = coupon.getEnableTime();
		if (!DateUtil.isFutureTime(enableTime)) {
			rsInfo.setMessage("优惠券已经过期");
			rsInfo.setCode("9996");
			return rsInfo;
		}

		Receive receive = new Receive();
		if (receiveType == null || receiveType.equals("")) {
			receive.setReceiveType(2); //1->后台赠送；2->主动获取
		}
		receive.setUserId(Integer.parseInt(userId));
		receive.setCouponId(Integer.parseInt(couponId));
		receive.setUseStatus(new Integer(1));// 1->未使用；2->已使用；3->已过期
		receive.setCreatedDate(new Date());
		int count = 0;
		try {
			count = receiveService.addReceive(receive);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("领取优惠券异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("领取优惠券成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("领取优惠券失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改领取优惠券记录信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/receive/modifyReceive")
	public ResultInfo modifyReceive(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String receiveId = request.getParameter("receiveId");
		String couponId = request.getParameter("couponId");

		if (receiveId == null || receiveId.equals("")) {
			rsInfo.setMessage("领取优惠券记录ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (couponId == null || couponId.equals("")) {
			rsInfo.setMessage("领取优惠券记录名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Receive receive = new Receive();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "receive") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		receive.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = receiveService.modifyReceive(receive);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改领取优惠券记录异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改领取优惠券记录失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改领取优惠券记录成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除领取优惠券记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receive/deleteReceive")
	public ResultInfo deleteReceive(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String receiveId = request.getParameter("receiveId");
		if (receiveId == null || receiveId.equals("")) {
			rsInfo.setMessage("领取优惠券记录Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = receiveService.delReceive(Integer.parseInt(receiveId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除领取优惠券记录异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除领取优惠券记录成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除领取优惠券记录失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看领取优惠券记录详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/receive/getReceiveById")
	public ResultInfo getReceiveById(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String receiveId = request.getParameter("receiveId");
		if (receiveId == null || receiveId.equals("")) {
			rsInfo.setMessage("领取优惠券记录ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Receive receive = null;
		try {
			receive = receiveService.getReceiveByReceiveId(Integer.parseInt(receiveId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取领取优惠券记录信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (receive == null) {
			rsInfo.setMessage("领取优惠券记录信息不存在！");
			rsInfo.setData(new Receive());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(receive);
		rsInfo.setMessage("获取领取优惠券记录成功！");
		return rsInfo;
	}

	/**
	 * @description 获取领取优惠券记录列表
	 *
	 * @param userStatus 使用状态：1->未使用；2->已使用；3->已过期
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/receive/getReceiveList")
	public ResultInfo getReceiveList(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String userId = request.getParameter("userId");
		String useStatus = request.getParameter("useStatus");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String couponId = request.getParameter("couponId");

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

		if (userId != null && !"".equals(userId)) {
			params.put("userId", userId);
		}
		if (useStatus != null && !"".equals(useStatus)) {
			params.put("useStatus", useStatus);
		}
		if (couponId != null && !"".equals(couponId)) {
			params.put("couponId", couponId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = receiveService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取领取优惠券记录总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("领取优惠券记录总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}

		List<RsReceive> receiveList;
		try {
			receiveList = receiveService.getReceiveList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取领取优惠券记录列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (receiveList.size() == 0) {
			rsInfo.setMessage("领取优惠券记录列表为空！");
			rsInfo.setData(new ArrayList<RsReceive>());
			return rsInfo;
		}
		rsInfo.setData(receiveList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取领取优惠券记录列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除领取优惠券记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/receive/deleteReceiveBatch")
	public ResultInfo deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = receiveService.deleteBatch(idArr);
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
	 * 获取用户优惠券列表 使用状态：1->未使用；2->已使用；3->已过期
	 *
	 * @param pageNum  页数
	 * @param pageSize 页大小
	 * @param userId   用户id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/receive/myCouponList")
	public ResultInfo getUserCouponList(@RequestParam("pageNum") Integer pageNum,
										@RequestParam("pageSize") Integer pageSize,
										@RequestParam("userId") Integer userId) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		if (userId == null) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		} else {
			params.put("userId", userId);
		}

		if (pageNum == null) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if (pageSize == null) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		PagingTool pageTool = new PagingTool(pageNum, pageSize);
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = receiveService.myReceiveCount(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取优惠券总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("优惠券总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsReceive> couponList;
		try {
			couponList = receiveService.getMyReceiveList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取优惠券列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (couponList.size() == 0) {
			rsInfo.setMessage("优惠券列表为空！");
			rsInfo.setData(new ArrayList<RsReceive>());
			return rsInfo;
		}
		rsInfo.setData(couponList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取优惠券列表成功！");
		return rsInfo;
	}

}
