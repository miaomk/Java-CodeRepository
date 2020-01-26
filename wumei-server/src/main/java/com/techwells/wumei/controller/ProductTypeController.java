package com.techwells.wumei.controller;

import com.techwells.wumei.domain.ProductType;
import com.techwells.wumei.domain.rs.BoProductType;
import com.techwells.wumei.service.ProductTypeService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
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
import java.util.stream.Collectors;

@Controller
//@RequestMapping(value = "*.do")
public class ProductTypeController {

	@Resource
	private ProductTypeService productTypeService;

	/**
	 * 添加分类
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/productType/addProductType")
	public @ResponseBody Object addProductType(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String typeName = request.getParameter("typeName");
		String parentId = request.getParameter("parentId");
		String level = request.getParameter("level");
		String sortIndex = request.getParameter("sortIndex");

		String imageUrl = request.getParameter("imageUrl");


		ProductType productType = new ProductType();

		if (typeName == null || "".equals(typeName)) {
			rsInfo.setMessage("分类名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (level == null || "".equals(level)) {
			rsInfo.setMessage("层级不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if ("2".equals(level)) {
			if (parentId == null || "".equals(parentId)) {
				rsInfo.setMessage("一级id不能为空！");
				rsInfo.setCode("10000");
				return rsInfo;
			}

			productType.setParentId(Integer.parseInt(parentId));
		}else{
			productType.setParentId(0);
		}
		if (sortIndex == null || "".equals(sortIndex)) {
			rsInfo.setMessage("排序不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (StringUtil.isEmpty(imageUrl)) {
			rsInfo.setMessage("图片不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}


		productType.setTypeName(typeName);

		productType.setSortIndex(Integer.parseInt(sortIndex));
		productType.setLevel(Integer.parseInt(level));
		productType.setImageUrl(imageUrl);

		

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "productType") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		productType.setCreatedDate(new Date());
		int count = 0;
		try {
			count = productTypeService.addProductType(productType);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加分类异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加分类成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加分类失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改分类信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productType/modifyProductType/{typeId}")
	public @ResponseBody Object modifyProductType(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile[] files,
			@PathVariable("typeId") Integer typeId) {
		ResultInfo rsInfo = new ResultInfo();

		String typeName = request.getParameter("typeName");
		String parentId = request.getParameter("parentId");
		String level = request.getParameter("level");
		String sortIndex = request.getParameter("sortIndex");
		String imageUrl = request.getParameter("imageUrl");


		ProductType productType = new ProductType();
		productType.setTypeId(typeId);
		productType.setTypeName(typeName);
		
		if (parentId != null && !parentId.equals("")) {
			productType.setParentId(Integer.parseInt(parentId));
		}
		
		if (sortIndex != null && !sortIndex.equals("")) {
			productType.setSortIndex(Integer.parseInt(sortIndex));

		}
		
		if (level != null && !level.equals("")) {
			productType.setLevel(Integer.parseInt(level));
		}

		if (!StringUtil.isEmpty(imageUrl)) {
			productType.setImageUrl(imageUrl);
		}

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "productType") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		// productType.setProductTypeId(Integer.parseInt(productTypeId));
		productType.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = productTypeService.modifyProductType(productType);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改分类异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改分类失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改分类成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除分类
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productType/deleteProductType/{typeId}")
	public @ResponseBody Object deleteProductType(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @PathVariable("typeId") Integer typeId) {
		ResultInfo rsInfo = new ResultInfo();
		ProductType productType = productTypeService.getProductTypeByProductTypeId(typeId);

		if (null == productType) {
			rsInfo.setMessage("商品类型不存在!");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		int count = 0;
		try {


			if (productType.getLevel() == 1) {

				List<ProductType> childrenProductTypeList = productTypeService.queryByPid(typeId);

				//该类型下的 二级商品类型
				List<ProductType> collect = childrenProductTypeList.stream().
						filter(data -> data.getParentId().equals(typeId)).collect(Collectors.toList());

				List<String> productTypeList = new ArrayList<>();
				productTypeList.add(String.valueOf(typeId));

				for (ProductType type : collect) {

					productTypeList.add(String.valueOf(type.getTypeId()));
				}

				String[] productArray = new String[productTypeList.size()];
				productArray = productTypeList.toArray(productArray);

				count = productTypeService.deleteBatch(productArray);
			}else {

				count = productTypeService.delProductType(typeId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除分类异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除分类成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除分类失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看分类详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productType/getProductTypeById")
	public @ResponseBody Object getProductTypeById(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String productTypeId = request.getParameter("productTypeId");
		if (productTypeId == null || productTypeId.equals("")) {
			rsInfo.setMessage("分类ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		ProductType productType = null;
		try {
			productType = productTypeService.getProductTypeByProductTypeId(Integer.parseInt(productTypeId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取分类信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (productType == null) {
			rsInfo.setMessage("分类信息不存在！");
			rsInfo.setData(new ProductType());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(productType);
		rsInfo.setMessage("获取分类成功！");
		return rsInfo;
	}

	/**
	 * @description 获取分类列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productType/getProductTypeList")
	public @ResponseBody Object getProductTypeList(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String typeName = request.getParameter("typeName");
		String level = request.getParameter("level");

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

		if (typeName != null && !typeName.equals("")) {
			params.put("typeName", typeName);
		}
		if (level != null && !level.equals("")) {
			params.put("level", level);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = productTypeService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取分类总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("分类总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<ProductType> productTypeList = null;
		try {
			productTypeList = productTypeService.getProductTypeList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取分类列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (productTypeList.size() == 0) {
			rsInfo.setMessage("分类列表为空！");
			rsInfo.setData(new ArrayList<ProductType>());
			return rsInfo;
		}
		rsInfo.setData(productTypeList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取分类列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/productType/deleteProductTypeBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = productTypeService.deleteBatch(idArr);
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
	 * 所有分类数据
	 *
	 * @return 所有分类数据
	 */
	@GetMapping(value ="/productType/all")
	public @ResponseBody Object queryAll() {
		ResultInfo rsInfo = new ResultInfo();
		
		List<BoProductType> productTypeList ;
		try {
			productTypeList = productTypeService.listWithChildren();
			
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取分类列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setData(productTypeList);
		return rsInfo;
	}

}
