package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Image;
import com.techwells.wumei.service.ImageService;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.apache.commons.lang.StringUtils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class ImageController {
	
	@Resource
	private ImageService imageService;

	/**
	 * 添加图片
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/image/addImage")
	public @ResponseBody Object addImage(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

//		String imageName = request.getParameter("imageName");
//
//		if (imageName == null || "".equals(imageName)) {
//			rsInfo.setMessage("图片名称不能为空！");
//			rsInfo.setCode("10000");
//			return rsInfo;
//		}

		Image image = new Image();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "image") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);

				image.setImageUrl(fileUrl);
			}
		}

		image.setCreatedDate(new Date());
		int count = 0;
		try {
			count = imageService.addImage(image);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加图片成功！");
			rsInfo.setData(image.getImageUrl());
		} else {
			rsInfo.setMessage("添加图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改图片信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/modifyImage")
	public @ResponseBody Object modifyImage(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String imageId = request.getParameter("imageId");
		String imageName = request.getParameter("imageName");

		if (imageId == null || imageId.equals("")) {
			rsInfo.setMessage("图片ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (imageName == null || imageName.equals("")) {
			rsInfo.setMessage("图片名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Image image = new Image();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "image") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		image.setImageId(Integer.parseInt(imageId));
		image.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = imageService.modifyImage(image);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改图片成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除图片
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/deleteImage")
	public @ResponseBody Object deleteImage(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String imageId = request.getParameter("imageId");
		if (imageId == null || imageId.equals("")) {
			rsInfo.setMessage("图片Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = imageService.delImage(Integer.parseInt(imageId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除图片异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除图片成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看图片详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/getImageById")
	public @ResponseBody Object getImageById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String imageId = request.getParameter("imageId");
		if (imageId == null || imageId.equals("")) {
			rsInfo.setMessage("图片ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Image image = null;
		try {
			image = imageService.getImageByImageId(Integer.parseInt(imageId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取图片信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (image == null) {
			rsInfo.setMessage("图片信息不存在！");
			rsInfo.setData(new Image());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(image);
		rsInfo.setMessage("获取图片成功！");
		return rsInfo;
	}

	/**
	 * @description 获取图片列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/getImageList")
	public @ResponseBody Object getImageList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userId = request.getParameter("userId");

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

		if (userId != null && !userId.equals("")) {
			params.put("userId", userId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = imageService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取图片总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("图片总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Image> imageList = null;
		try {
			imageList = imageService.getImageList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取图片列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (imageList.size() == 0) {
			rsInfo.setMessage("图片列表为空！");
			rsInfo.setData(new ArrayList<Image>());
			return rsInfo;
		}
		rsInfo.setData(imageList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取图片列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/deleteImageBatch")
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
			count = imageService.deleteBatch(idArr);
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
	 * 上传用户头像
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/image/uploadUserIcon")
	public @ResponseBody Object uploadUserIcon(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Image image = new Image();
		image.setUserId(Integer.parseInt(userId));
		image.setIsDefault(2);		
		image.setImageId(1);
		
		if(file != null) {

		// 处理图片
			String fileUrl = "";
				try {
					fileUrl += FileUtil.uploadFile(file, "image") ;
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
		image.setImageUrl(fileUrl);
		}

		image.setCreatedDate(new Date());
		int count = 0;
		try {
			count = imageService.addImage(image);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加图片成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}
	
	
	/**
	 * 修改图片信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/modifyToDefault")
	public @ResponseBody Object modifyToDefault(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String imageId = request.getParameter("imageId");
		String userId = request.getParameter("userId");

		if (imageId == null || imageId.equals("")) {
			rsInfo.setMessage("图片ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Image image = new Image();
		image.setImageId(Integer.parseInt(imageId));
		image.setUserId(Integer.parseInt(userId));
		image.setIsDefault(1);
		image.setUpdatedDate(new Date());
		
		int count = 0;
		try {
			count = imageService.modifyToDefault(image);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改图片异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改图片成功！");
		rsInfo.setData(count);
		return rsInfo;
	}


	/**
	 * 富文本框 添加图片
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/image/uploadImage")
	public @ResponseBody Object ueditorAddImage(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();


		String fileUrl = "";
		// 处理图片
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "image") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!"".equals(fileUrl)) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);


			}
		}

		if (StringUtils.isNotEmpty(fileUrl)) {
			rsInfo.setMessage("添加图片成功！");
			rsInfo.setData(fileUrl);
		} else {
			rsInfo.setMessage("添加图片失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}
}
