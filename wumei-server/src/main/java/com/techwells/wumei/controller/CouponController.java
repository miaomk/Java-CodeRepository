package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Coupon;
import com.techwells.wumei.service.CouponService;
import com.techwells.wumei.util.*;
import okhttp3.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 商家优惠券Controller
 *
 * @author Administrator
 */
@RestController
public class CouponController {


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private CouponService couponService;

	/**
	 * 添加优惠券
	 *
	 * @param coupon 优惠券信息
	 * @return ResultInfo
	 */

	@RequestMapping(value = "/coupon/addCoupon", method = RequestMethod.POST)
	public ResultInfo addCoupon(@RequestBody Coupon coupon) {
		ResultInfo rsInfo = new ResultInfo();

		String couponName = coupon.getCouponName();
		Date startTime = coupon.getStartTime();
		Date endTime = coupon.getEndTime();
		if (couponName == null || "".equals(couponName)) {
			rsInfo.setMessage("优惠券名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if(DateUtil.compareTime(startTime, endTime)) {
			rsInfo.setMessage("开始时间不能大于结束时间！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if(null == coupon.getMerchantId()){
			rsInfo.setMessage("商铺id不能为空！");
			rsInfo.setCode("10002");
			return rsInfo;
		}

		int count;
		try {
			count = couponService.addCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加优惠券异常！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加优惠券成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加优惠券失败！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改优惠券信息
	 * 
	 * @param request 请求
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/coupon/modifyCoupon/{couponId}")
	public ResultInfo modifyCoupon(HttpServletRequest request,
								   @PathVariable("couponId") String couponId) {
		ResultInfo rsInfo = new ResultInfo();

        String couponType = request.getParameter("couponType");
        String couponName = request.getParameter("couponName");
		String platform = request.getParameter("platform");
		String publishCount = request.getParameter("publishCount");
        String amount = request.getParameter("amount");
        String perLimit = request.getParameter("perLimit");
        String minPoint = request.getParameter("minPoint");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String useType = request.getParameter("useType");
        String productIds = request.getParameter("productIds");
        String description = request.getParameter("description");

        if (couponId == null || "".equals(couponId)) {
			rsInfo.setMessage("优惠券ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (couponName == null || "".equals(couponName)) {
			rsInfo.setMessage("优惠券名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Coupon coupon = new Coupon();

        coupon.setCouponId(Integer.parseInt(couponId));
		coupon.setCouponName(couponName);
		coupon.setAmount(new BigDecimal(amount));
		coupon.setCouponType(Integer.parseInt(couponType));
		coupon.setUseType(Integer.parseInt(useType));
		coupon.setPlatform(Integer.parseInt(platform));
		coupon.setPublishCount(Integer.parseInt(publishCount));
		coupon.setPerLimit(Integer.parseInt(perLimit));
		coupon.setMinPoint(new BigDecimal(minPoint));
		coupon.setProductIds(productIds);
		coupon.setDescription(description);

		int count;
		try {

            coupon.setStartTime(simpleDateFormat.parse(startTime));
            coupon.setEnableTime(simpleDateFormat.parse(endTime));
            coupon.setEndTime(simpleDateFormat.parse(endTime));

			count = couponService.modifyCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改优惠券异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改优惠券失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改优惠券成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * 删除优惠券
	 *
	 * @param couponId 优惠券id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/coupon/deleteCoupon/{couponId}",method = RequestMethod.POST)
	public ResultInfo deleteCoupon(@PathVariable("couponId") String couponId) {
		ResultInfo rsInfo = new ResultInfo();

		if (couponId == null || "".equals(couponId)) {
			rsInfo.setMessage("优惠券Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = couponService.delCoupon(Integer.parseInt(couponId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除优惠券异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除优惠券成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除优惠券失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

    /**
     * 查看优惠券详情
     *
     * @param couponId 优惠券id
     * @return ResultInfo
     */
	@RequestMapping(value = "/coupon/getCouponById/{couponId}")
	public ResultInfo getCouponById(@PathVariable("couponId") String couponId) {
		ResultInfo rsInfo = new ResultInfo();

		if (couponId == null || "".equals(couponId)) {
			rsInfo.setMessage("优惠券ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Coupon coupon;
		try {
			coupon = couponService.getCouponByCouponId(Integer.parseInt(couponId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取优惠券信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (coupon == null) {
			rsInfo.setMessage("优惠券信息不存在！");
			rsInfo.setData(new Coupon());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(coupon);
		rsInfo.setMessage("获取优惠券成功！");
		return rsInfo;
	}

	/**
	 * 获取优惠券列表
	 *
	 * @param request 请求
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/coupon/getCouponList")
	public ResultInfo getCouponList(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String couponName = request.getParameter("couponName");
		String couponType = request.getParameter("couponType");
		String merchantId = request.getParameter("merchantId");

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

		if (couponName != null && !"".equals(couponName)) {
			params.put("couponName", couponName);
		}
		if (couponType != null && !"".equals(couponType)) {
			params.put("couponType", Integer.parseInt(couponType));
		}
		if (merchantId != null && !"".equals(merchantId)) {
			params.put("merchantId", merchantId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = couponService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取优惠券总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("优惠券总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}

		List<Coupon> couponList;
		try {
			couponList = couponService.getCouponList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取优惠券列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (couponList.size() == 0) {
			rsInfo.setMessage("优惠券列表为空！");
			rsInfo.setData(new ArrayList<Coupon>());
			return rsInfo;
		}
		rsInfo.setData(couponList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取优惠券列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除优惠券
	 * 
	 * @param id 优惠券id字符串
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/coupon/deleteCouponBatch")
	public ResultInfo deleteBatch(@RequestParam("id")String id) {
		ResultInfo rsInfo = new ResultInfo();

		if(StringUtil.isEmpty(id)){
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		String[] idArr =id.split(",");

		int count;
		try {

			count =  couponService.deleteBatch(idArr);
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
