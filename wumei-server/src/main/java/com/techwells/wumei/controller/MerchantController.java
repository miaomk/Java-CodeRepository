package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsMerchant;
import com.techwells.wumei.service.MerchantService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
public class MerchantController {
	
	@Resource
	private MerchantService merchantService;
	@Resource
	private UserService userService;

	/**
	 * 添加商家
	 * @param request
	 * @param file
	 * @param blFile
	 * @param ifFile
	 * @param ibFile
	 * @return
	 */
	// 添加商家
	@RequestMapping(value = "/merchant/addMerchant")
	public ResultInfo addMerchant(HttpServletRequest request,
											@RequestParam(value = "file", required = false) MultipartFile file,
											@RequestParam(value = "blFile", required = false) MultipartFile blFile,
											@RequestParam(value = "ifFile", required = false) MultipartFile ifFile,
											@RequestParam(value = "ibFile", required = false) MultipartFile ibFile) {
		// file 商家头像  blFile 营业执照  ifFile 身份证正面  ibFile 身份证背面

		ResultInfo rsInfo = new ResultInfo();
		//用户ID
		String userId = request.getParameter("userId");
		//店铺名称
		String merchantName = request.getParameter("merchantName");
		//商家类型 1 个人 2公司
		String merchantType = request.getParameter("merchantType");
		// 公司名称
		String enterpriseName = request.getParameter("enterpriseName");
		//法人姓名
		String jurisdicalPerson = request.getParameter("jurisdicalPerson");
		//法人手机号
		String jurisdicalMobile = request.getParameter("jurisdicalMobile");
		//法人身份证号
		String identificationCard = request.getParameter("identificationCard");

		if (StringUtil.isEmpty(merchantName)) {
			rsInfo.setCode("10101");
			rsInfo.setMessage("店铺名称不能为空！");
			return rsInfo;
		}

		if (StringUtil.isEmpty(identificationCard)) {
			rsInfo.setCode("10101");
			rsInfo.setMessage("法人身份证不能为空！");
			return rsInfo;
		}

		if (StringUtil.isEmpty(merchantType)) {
			rsInfo.setCode("10101");
			rsInfo.setMessage("商家类型不能为空！");
			return rsInfo;
		}
		// 电活地址

		Merchant merchant = new Merchant();

		String path = WuMeiConstants.UPLOAD_PATH + "merchant//";

		if (file != null) {
			String fileName = file.getOriginalFilename();
			String filePath = System.currentTimeMillis() + fileName;
			File targetFile = new File(path, filePath);
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}
			// 商家头像
			String urlPath = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath;
			merchant.setMerchantIcon(urlPath);
		}

		if (blFile != null) {
			String fileName2 = blFile.getOriginalFilename();
			String filePath2 = System.currentTimeMillis() + fileName2;
			File targetFile2 = new File(path, filePath2);
			try {
				blFile.transferTo(targetFile2);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}

			// 营业执照
			String urlPath2 = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath2;
			merchant.setBusinessLicence(urlPath2);

		}

		if (ifFile != null) {
			String fileName3 = ifFile.getOriginalFilename();
			String filePath3 = System.currentTimeMillis() + fileName3;
			File targetFile3 = new File(path, filePath3);
			try {
				ifFile.transferTo(targetFile3);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}

			// 法人身份证图（正）
			String urlPath3 = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath3;
			merchant.setIdentificationFront(urlPath3);
		}
		if (ibFile != null) {
			String fileName4 = ibFile.getOriginalFilename();
			String filePath4 = System.currentTimeMillis() + fileName4;
			File targetFile4 = new File(path, filePath4);
			try {
				ibFile.transferTo(targetFile4);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}
			// 法人身份证图（反）
			String urlPath4 = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath4;
			merchant.setIdentificationBehind(urlPath4);
		}

		merchant.setUserId(Integer.parseInt(userId));
		merchant.setMerchantName(merchantName); // 商家名称
		merchant.setEnterpriseName(enterpriseName); // 公司名称		
		merchant.setJurisdicalPerson(jurisdicalPerson); // 法人姓名
		merchant.setIdentificationCard(identificationCard); // 法人身份证号码
		merchant.setMobile(jurisdicalMobile); // 法人手机号
		merchant.setJurisdicalMobile(jurisdicalMobile); // 法人手机号
		merchant.setCreatedDate(new Date());
		merchant.setActivated(1);


		int count;
		try {
			count = merchantService.addMerchant(merchant);
		} catch (Exception e) {
			rsInfo.setMessage("添加商家失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (count == 0) {
			rsInfo.setMessage("参与计划失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		rsInfo.setData(count);
		rsInfo.setMessage("参与计划成功！");

		return rsInfo;
	}

	/**
	 * 删除店铺
	 *
	 * @param merchantId 店铺id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/merchant/delMerchant")
	public ResultInfo delMerchant(@RequestParam("merchantId")Integer merchantId) {

		ResultInfo rsInfo = new ResultInfo();


		if (merchantId == null) {
			rsInfo.setMessage("merchantId不能为空！");
			rsInfo.setCode("23115");
			return rsInfo;
		}

		int count;
		try {
			count = merchantService.delMerchant(merchantId);
		} catch (Exception e) {
			rsInfo.setMessage("删除商家失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (count == 0) {
			rsInfo.setMessage("删除商家失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}
		rsInfo.setData(count);
		rsInfo.setMessage("删除商家成功！");
		return rsInfo;
	}

	// 后台修改店铺信息
    @RequestMapping(value = "/merchant/modifyMerchant", method = RequestMethod.POST)
	public ResultInfo modifyMerchant(HttpServletRequest request,
											   @RequestParam(value = "file", required = false) MultipartFile file,
											   @RequestParam(value = "blFile", required = false) MultipartFile blFile,
											   @RequestParam(value = "ifFile", required = false) MultipartFile ifFile,
											   @RequestParam(value = "ibFile", required = false) MultipartFile ibFile) {

		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String merchantName = request.getParameter("merchantName");
		String enterpriseName = request.getParameter("enterpriseName");
		String jurisdicalPerson = request.getParameter("jurisdicalPerson");
		String jurisdicalMobile = request.getParameter("jurisdicalMobile");
		String identificationCard = request.getParameter("identificationCard");
		String isRecommend = request.getParameter("isRecommend");
		String address = request.getParameter("address");
		String activated = request.getParameter("activated");
		String manager = request.getParameter("manager");
		String mobile = request.getParameter("mobile");
		String description = request.getParameter("description");
		String merchantLogo = request.getParameter("merchantLogo");

		
		if (StringUtil.isEmpty(userId)) {
			rsInfo.setCode("10101");
			rsInfo.setMessage("用户ID不能为空！");
			return rsInfo;
		}

		Merchant merchant = new Merchant();

		String path = WuMeiConstants.UPLOAD_PATH + "merchant//";

		if (file != null) {
			String fileName = file.getOriginalFilename();
			String filePath = System.currentTimeMillis() + fileName;
			File targetFile = new File(path, filePath);
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}
			// 商家头像
			String urlPath = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath;
			merchant.setMerchantIcon(urlPath);
		}
		
		if (blFile != null) {
			String fileName2 = blFile.getOriginalFilename();
			String filePath2 = System.currentTimeMillis() + fileName2;
			File targetFile2 = new File(path, filePath2);
			try {
				blFile.transferTo(targetFile2);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}
			
			// 营业执照
			String urlPath2 = WuMeiConstants.UPLOAD_URL + "merchant/"
								+ filePath2;
			merchant.setBusinessLicence(urlPath2);
		}
		
		if (ifFile != null) {
			String fileName3 = ifFile.getOriginalFilename();
			String filePath3 = System.currentTimeMillis() + fileName3;
			File targetFile3 = new File(path, filePath3);
			try {
				ifFile.transferTo(targetFile3);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}

			// 法人身份证图（正）
			String urlPath3 = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath3;
			merchant.setIdentificationFront(urlPath3);
		}
		if (ibFile != null) {
			String fileName4 = ibFile.getOriginalFilename();
			String filePath4 = System.currentTimeMillis() + fileName4;
			File targetFile4 = new File(path, filePath4);
			try {
				ibFile.transferTo(targetFile4);
			} catch (Exception e) {
				rsInfo.setMessage("上传头像出错");
				rsInfo.setCode("11230");
				return rsInfo;
			}
			// 法人身份证图（反）
			String urlPath4 = WuMeiConstants.UPLOAD_URL + "merchant/"
					+ filePath4;
			merchant.setIdentificationBehind(urlPath4);
		}

		merchant.setUserId(Integer.parseInt(userId));
		// 商家名称
		merchant.setMerchantName(merchantName);
		// 公司名称
		merchant.setEnterpriseName(enterpriseName);
		// 法人姓名
		merchant.setJurisdicalPerson(jurisdicalPerson);
		// 法人身份证号码
		merchant.setIdentificationCard(identificationCard);
		// 法人手机号
		merchant.setMobile(jurisdicalMobile);
		// 法人手机号
		merchant.setJurisdicalMobile(jurisdicalMobile);
		merchant.setActivated(1);
		merchant.setMerchantLogo(merchantLogo);
        if (!StringUtil.isEmpty(isRecommend)) {

            merchant.setIsRecommend(Integer.parseInt(isRecommend));
        }
        if (!StringUtil.isEmpty(activated)) {

            merchant.setActivated(Integer.parseInt(activated));
        }
        if (!StringUtil.isEmpty(address)) {

            merchant.setAddress(address);
        }
        if (!StringUtil.isEmpty(manager)) {

            merchant.setManager(manager);
        }
        if (!StringUtil.isEmpty(mobile)) {

            merchant.setMobile(mobile);
        }
        if (!StringUtil.isEmpty(description)) {

            merchant.setDescription(description);
        }

		int count;
		try {
			count = merchantService.modifyMerchant(merchant);
		} catch (Exception e) {
			rsInfo.setMessage("修改商家失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (count == 0) {
			rsInfo.setMessage("修改商家失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}

		rsInfo.setData(count);
		rsInfo.setMessage("参与计划成功！");

		return rsInfo;
	}

	/**
	 * 店铺设置
	 *
	 * @param merchantId  店铺id
	 * @param logo        店铺logo
	 * @param icon        店铺封面
	 * @param description 店铺简介
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/editMerchant")
	public ResultInfo editMerchant(@RequestParam("merchantId") Integer merchantId,
								   @RequestParam(value = "logo", required = false) MultipartFile[] logo,
								   @RequestParam(value = "icon", required = false) MultipartFile[] icon,
								   @RequestParam(value = "description", required = false) String description) {
		ResultInfo resultInfo = new ResultInfo();

		Merchant merchant = new Merchant();
		merchant.setUserId(merchantId);
		// 处理店铺logo
		if (logo != null && logo.length > 0) {

			String merchantLogo = UploadImageUtil.uploadImage("merchant", logo);
			if (!StringUtil.isEmpty(merchantLogo)) {

				merchant.setMerchantLogo(merchantLogo);
			} else {
				resultInfo.setMessage("店铺logo上传出错！");
				resultInfo.setCode("10001");
				return resultInfo;
			}
		}
		//处理店铺封面
		if (icon != null && icon.length > 0) {

			String merchantIcon = UploadImageUtil.uploadImage("merchant", icon);
			if (!StringUtil.isEmpty(merchantIcon)) {

				merchant.setMerchantLogo(merchantIcon);
			} else {
				resultInfo.setMessage("店铺封面上传出错！");
				resultInfo.setCode("10001");
				return resultInfo;
			}
		}
		if (!StringUtil.isEmpty(description)) {
			merchant.setDescription(description);
		}

		int count;
		try {
			count = merchantService.modifyMerchant(merchant);
		} catch (Exception e) {
			resultInfo.setMessage("店铺设置失败！");
			resultInfo.setCode("23211");
			return resultInfo;
		}
		if (count == 0) {
			resultInfo.setMessage("店铺设置失败！");
			resultInfo.setCode("28121");
			return resultInfo;
		}

		resultInfo.setData(count);
		resultInfo.setMessage("店铺设置成功！");
		return resultInfo;
	}


	/**
	 * 获取商铺信息
	 *
	 * @param request 请求
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/merchant/getMerchantByMerchantId")
	public ResultInfo getMerchantByMerchantId(HttpServletRequest request) {
		
		ResultInfo rsInfo = new ResultInfo();
		String merchantId = request.getParameter("merchantId");

		if (merchantId == null || "".equals(merchantId)) {
			rsInfo.setMessage("merchantId不能为空！");
			rsInfo.setCode("110");
			return rsInfo;
		}

		Merchant merchant;
		try {
			merchant = merchantService.getMerchantByMerchantId(Integer
					.parseInt(merchantId));
		} catch (Exception e) {
			rsInfo.setMessage("通过商家ID获取商家失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (merchant == null) {
			rsInfo.setMessage("通过商家ID获取商家失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}
		rsInfo.setData(merchant);
		rsInfo.setMessage("通过商家ID获取商家成功！");

		return rsInfo;
	}
	
	
	// 商家审核
	@RequestMapping(value = "/merchant/reviewMerchant")
	public ResultInfo reviewMerchant(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		
		String merchantId = request.getParameter("merchantId");
		String reviewStatus = request.getParameter("reviewStatus");

		if (merchantId == null || "".equals(merchantId)) {
			rsInfo.setMessage("商家ID不能为空！");
			rsInfo.setCode("110");
			return rsInfo;
		}
		if (reviewStatus == null || "".equals(reviewStatus)) {
			rsInfo.setMessage("审核状态不能为空！");
			rsInfo.setCode("111");
			return rsInfo;
		}

		Merchant merchant = new Merchant();
		merchant.setUserId(Integer.parseInt(merchantId));
		merchant.setActivated(Integer.parseInt(reviewStatus));

		int count;
		try {
			count = merchantService.modifyMerchant(merchant);
		} catch (Exception e) {
			rsInfo.setMessage("审核商家失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (count == 0) {
			rsInfo.setMessage("审核商家失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}
		rsInfo.setData(count);
		rsInfo.setMessage("审核商家成功！");

		return rsInfo;
	}
	
	
	/**
	 * @description 获取店铺列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantList")
	public ResultInfo getMerchantList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        String merchantName = request.getParameter("merchantName");
        String recommend = request.getParameter("recommend");
        String userId = request.getParameter("userId");

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
        if (userId != null && !"".equals(userId)) {
            params.put("userId", userId);
        }
        if (recommend != null && !"".equals(recommend)) {
            params.put("isRecommend", recommend);
        }
        if (merchantName != null && !"".equals(merchantName)) {
            params.put("merchantName", merchantName);
        }
		

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = merchantService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取店铺总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("店铺总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Merchant> merchantList;
		try {
			merchantList = merchantService.getMerchantList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取店铺列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (merchantList.size() == 0) {
			rsInfo.setMessage("店铺列表为空！");
			rsInfo.setData(new ArrayList<Merchant>());
			return rsInfo;
		}
		rsInfo.setData(merchantList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取店铺列表成功！");
		return rsInfo;
	}


	/**
	 * 店铺详细信息
	 *
	 * @param merchantId 商户id
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/getMerchantInfo")
	public ResultInfo getMerchantInfo(@RequestParam("merchantId") String merchantId) {
		ResultInfo rsInfo = new ResultInfo();
		RsMerchant rsMerchant;

		try {
			Merchant merchant = merchantService.getMerchantByMerchantId(Integer.parseInt(merchantId));
			if (null == merchant) {

				rsInfo.setMessage("店铺不存在！");
				rsInfo.setCode("23211");
				return rsInfo;
			}

			rsMerchant = merchantService.getMerchantInfo(merchantId);
		} catch (Exception e) {
			rsInfo.setMessage("查询店铺信息失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		rsInfo.setData(rsMerchant);
		rsInfo.setMessage("查询店铺信息成功！");

		return rsInfo;
	}


	/**
	 * 商家中心
	 *
	 * @param merchantId 商家id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/merchant/getMerchantInfo/{merchantId}")
	@ResponseBody
	public ResultInfo getMerchantInfo(@PathVariable Integer merchantId) {

		ResultInfo rsInfo = new ResultInfo();

		if (merchantId == null) {
			rsInfo.setMessage("merchantId不能为空！");
			rsInfo.setCode("110");
			return rsInfo;
		}
		Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
		if (null == merchant) {

			rsInfo.setMessage("店铺不存在！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		RsMerchant rsMerchant;
		try {
			rsMerchant = merchantService.getMerchantCenterInfo(merchantId);
		} catch (Exception e) {
			rsInfo.setMessage("查询商家中心信息失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}
		if (rsMerchant == null) {
			rsInfo.setMessage("查询商家中心信息失败！");
			rsInfo.setCode("28121");
			return rsInfo;
		}
		rsInfo.setData(rsMerchant);
		rsInfo.setMessage("查询商家中心信息成功！");

		return rsInfo;
	}

	/**
	 * 获取商户的我的账号信息
	 * @param merchantId 商户id
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantAccount")
	@ResponseBody
	public ResultInfo getMerchantAccount(@RequestParam("merchantId") Integer merchantId) {

		ResultInfo rsInfo = new ResultInfo();

		Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
		if (null == merchant) {

			rsInfo.setMessage("商户不存在！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		MyAccountVO myAccountVO;
		try {
			myAccountVO = merchantService.getMerchantAccountInfo(merchantId);
		} catch (Exception e) {
			rsInfo.setMessage("查询商家中心信息失败！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		rsInfo.setData(myAccountVO);
		rsInfo.setMessage("查询商家账户信息成功！");

		return rsInfo;
	}

}
