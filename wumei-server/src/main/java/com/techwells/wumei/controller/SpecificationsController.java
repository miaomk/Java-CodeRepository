package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Specifications;
import com.techwells.wumei.service.SpecificationsService;
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
public class SpecificationsController {
	@Resource
	private SpecificationsService specificationsService;

	/**
	 * 添加商品规格
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/specifications/addSpecifications")
	public @ResponseBody Object addSpecifications(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String specificationsName = request.getParameter("specificationsName");

		if (specificationsName == null || specificationsName.equals("")) {
			rsInfo.setMessage("商品规格名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Specifications specifications = new Specifications();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "specifications") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		//specifications.setCreatedDate(new Date());
		int count = 0;
		try {
			count = specificationsService.addSpecifications(specifications);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加商品规格异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加商品规格成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加商品规格失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改商品规格信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/specifications/modifySpecifications")
	public @ResponseBody Object modifySpecifications(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String specificationsId = request.getParameter("specificationsId");
		String specificationsName = request.getParameter("specificationsName");

		if (specificationsId == null || specificationsId.equals("")) {
			rsInfo.setMessage("商品规格ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (specificationsName == null || specificationsName.equals("")) {
			rsInfo.setMessage("商品规格名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		Specifications specifications = new Specifications();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "specifications") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		specifications.setSpId(Integer.parseInt(specificationsId));
		//specifications.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = specificationsService.modifySpecifications(specifications);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改商品规格异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改商品规格失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改商品规格成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除商品规格
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/specifications/deleteSpecifications")
	public @ResponseBody Object deleteSpecifications(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String specificationsId = request.getParameter("specificationsId");
		if (specificationsId == null || specificationsId.equals("")) {
			rsInfo.setMessage("商品规格Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = specificationsService.delSpecifications(Integer.parseInt(specificationsId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除商品规格异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除商品规格成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除商品规格失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看商品规格详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/specifications/getSpecificationsById")
	public @ResponseBody Object getSpecificationsById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String specificationsId = request.getParameter("specificationsId");
		if (specificationsId == null || specificationsId.equals("")) {
			rsInfo.setMessage("商品规格ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Specifications specifications = null;
		try {
			specifications = specificationsService.getSpecificationsBySpecificationsId(Integer.parseInt(specificationsId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取商品规格信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (specifications == null) {
			rsInfo.setMessage("商品规格信息不存在！");
			rsInfo.setData(new Specifications());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(specifications);
		rsInfo.setMessage("获取商品规格成功！");
		return rsInfo;
	}

	/**
	 * @description 获取商品规格列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/specifications/getSpecificationsList")
	public @ResponseBody Object getSpecificationsList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String specificationsName = request.getParameter("specificationsName");

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

		if (specificationsName != null && !specificationsName.equals("")) {
			params.put("specificationsName", specificationsName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = specificationsService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品规格总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("商品规格总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Specifications> specificationsList = null;
		try {
			specificationsList = specificationsService.getSpecificationsList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取商品规格列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (specificationsList.size() == 0) {
			rsInfo.setMessage("商品规格列表为空！");
			rsInfo.setData(new ArrayList<Specifications>());
			return rsInfo;
		}
		rsInfo.setData(specificationsList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取商品规格列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除商品规格
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/specifications/deleteSpecificationsBatch")
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
			count = specificationsService.batchDelete(idArr);
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
