package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Brand;
import com.techwells.wumei.service.BrandService;
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
public class BrandController {
	
	@Resource
	private BrandService brandService;

	/**
	 * 添加品牌
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/brand/addBrand")
	public @ResponseBody Object addBrand(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String brandName = request.getParameter("brandName");
		String company = request.getParameter("company");

		if (brandName == null || brandName.equals("")) {
			rsInfo.setMessage("品牌名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Brand brand = new Brand();
		brand.setBrandName(brandName);
		brand.setCompany(company);

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "brand") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
			brand.setBrandIcon(fileUrl);
		}

		brand.setCreatedDate(new Date());
		int count = 0;
		try {
			count = brandService.addBrand(brand);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加品牌异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加品牌成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加品牌失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改品牌信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/brand/modifyBrand/{brandId}")
	public @ResponseBody Object modifyBrand(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@PathVariable("brandId") Integer brandId) {
		ResultInfo rsInfo = new ResultInfo();

		String brandName = request.getParameter("brandName");
		String company = request.getParameter("company");

		if (brandName == null || brandName.equals("")) {
			rsInfo.setMessage("品牌名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Brand brand = new Brand();
		brand.setBrandId(brandId);
		
		brand.setBrandName(brandName);
		brand.setCompany(company);
		

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "brand") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
			brand.setBrandIcon(fileUrl);
		}
		
		brand.setBrandId(brandId);
		brand.setUpdatedDate(new Date());
		
		int count = 0;
		try {
			count = brandService.modifyBrand(brand);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改品牌异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改品牌失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改品牌成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除品牌
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/brand/deleteBrand/{brandId}")
	public @ResponseBody Object deleteBrand(HttpServletRequest request,
			HttpSession session, HttpServletResponse response,@PathVariable("brandId") Integer brandId) {
		ResultInfo rsInfo = new ResultInfo();

		int count = 0;
		try {
			count = brandService.delBrand(brandId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除品牌异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除品牌成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除品牌失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看品牌详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/brand/getBrandById")
	public @ResponseBody Object getBrandById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String brandId = request.getParameter("brandId");
		if (brandId == null || brandId.equals("")) {
			rsInfo.setMessage("品牌ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Brand brand = null;
		try {
			brand = brandService.getBrandByBrandId(Integer.parseInt(brandId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取品牌信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (brand == null) {
			rsInfo.setMessage("品牌信息不存在！");
			rsInfo.setData(new Brand());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(brand);
		rsInfo.setMessage("获取品牌成功！");
		return rsInfo;
	}

	/**
	 * @description 获取品牌列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/brand/getBrandList")
	public @ResponseBody Object getBrandList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String brandName = request.getParameter("brandName");

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

		if (brandName != null && !brandName.equals("")) {
			params.put("brandName", brandName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = brandService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取品牌总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("品牌总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Brand> brandList = null;
		try {
			brandList = brandService.getBrandList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取品牌列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (brandList.size() == 0) {
			rsInfo.setMessage("品牌列表为空！");
			rsInfo.setData(new ArrayList<Brand>());
			return rsInfo;
		}
		rsInfo.setData(brandList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取品牌列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除品牌
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/brand/deleteBrandBatch")
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
			count = brandService.deleteBatch(idArr);
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
