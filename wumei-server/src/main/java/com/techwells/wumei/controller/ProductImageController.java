package com.techwells.wumei.controller;

import com.techwells.wumei.domain.ProductImage;
import com.techwells.wumei.service.ProductImageService;
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
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class ProductImageController {
	
	@Resource
	private ProductImageService productImageService;

	/**
	 * 添加商品图片
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/productImage/addProductImage")
	public @ResponseBody Object addProductImage(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String productImageName = request.getParameter("productImageName");

		if (productImageName == null || productImageName.equals("")) {
			rsInfo.setMessage("商品图片名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		ProductImage productImage = new ProductImage();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "productImage") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		//productImage.setCreatedDate(new Date());
		int count = 0;
		try {
			count = productImageService.addProductImage(productImage);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加商品图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加商品图片成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加商品图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改商品图片信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productImage/modifyProductImage")
	public @ResponseBody Object modifyProductImage(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String productImageId = request.getParameter("productImageId");
		String productImageName = request.getParameter("productImageName");

		if (productImageId == null || productImageId.equals("")) {
			rsInfo.setMessage("商品图片ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (productImageName == null || productImageName.equals("")) {
			rsInfo.setMessage("商品图片名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		ProductImage productImage = new ProductImage();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "productImage") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		//productImage.setProductImageId(Integer.parseInt(productImageId));
		//productImage.setUpdatedDate(new Date());
		
		int count = 0;
		try {
			count = productImageService.modifyProductImage(productImage);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改商品图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改商品图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改商品图片成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除商品图片
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productImage/deleteProductImage")
	public @ResponseBody Object deleteProductImage(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String productImageId = request.getParameter("productImageId");
		if (productImageId == null || productImageId.equals("")) {
			rsInfo.setMessage("商品图片Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = productImageService.delProductImage(Integer.parseInt(productImageId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除商品图片异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除商品图片成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除商品图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看商品图片详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productImage/getProductImageById")
	public @ResponseBody Object getProductImageById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String productImageId = request.getParameter("productImageId");
		if (productImageId == null || productImageId.equals("")) {
			rsInfo.setMessage("商品图片ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		ProductImage productImage = null;
		try {
			productImage = productImageService.getProductImageByProductImageId(Integer.parseInt(productImageId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取商品图片信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (productImage == null) {
			rsInfo.setMessage("商品图片信息不存在！");
			rsInfo.setData(new ProductImage());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(productImage);
		rsInfo.setMessage("获取商品图片成功！");
		return rsInfo;
	}

	/**
	 * @description 获取商品图片列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productImage/getProductImageList")
	public @ResponseBody Object getProductImageList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String productId = request.getParameter("productId");

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

		if (productId != null && !productId.equals("")) {
			params.put("productId", productId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = productImageService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品图片总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("商品图片总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}

		List<ProductImage> productImageList = null;
		try {
			productImageList = productImageService.getProductImageList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品图片列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (productImageList.size() == 0) {
			rsInfo.setMessage("商品图片列表为空！");
			rsInfo.setData(new ArrayList<ProductImage>());
			return rsInfo;
		}
		rsInfo.setData(productImageList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取商品图片列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除商品图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productImage/deleteProductImageBatch")
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
			count = productImageService.deleteBatch(idArr);
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
