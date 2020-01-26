package com.techwells.wumei.controller;

import com.techwells.wumei.domain.City;
import com.techwells.wumei.service.CityService;
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
public class CityController {

	@Resource
	private CityService cityService;

	/**
	 * 添加城市
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/city/addCity")
	public @ResponseBody Object addCity(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String cityName = request.getParameter("cityName");

		if (cityName == null || cityName.equals("")) {
			rsInfo.setMessage("城市名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		City city = new City();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "city") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		city.setCreatedDate(new Date());
		int count = 0;
		try {
			count = cityService.addCity(city);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加城市异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加城市成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加城市失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改城市信息
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/city/modifyCity")
	public @ResponseBody Object modifyCity(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");

		if (cityId == null || cityId.equals("")) {
			rsInfo.setMessage("城市ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (cityName == null || cityName.equals("")) {
			rsInfo.setMessage("城市名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		City city = new City();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "city") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		city.setCityId(Integer.parseInt(cityId));
		city.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = cityService.modifyCity(city);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改城市异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改城市失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改城市成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除城市
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/city/deleteCity")
	public @ResponseBody Object deleteCity(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String cityId = request.getParameter("cityId");
		if (cityId == null || cityId.equals("")) {
			rsInfo.setMessage("城市Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = cityService.delCity(Integer.parseInt(cityId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除城市异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除城市成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除城市失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看城市详情
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/city/getCityById")
	public @ResponseBody Object getCityById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String cityId = request.getParameter("cityId");
		if (cityId == null || cityId.equals("")) {
			rsInfo.setMessage("城市ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		City city = null;
		try {
			city = cityService.getCityByCityId(Integer.parseInt(cityId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取城市信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (city == null) {
			rsInfo.setMessage("城市信息不存在！");
			rsInfo.setData(new City());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(city);
		rsInfo.setMessage("获取城市成功！");
		return rsInfo;
	}

	/**
	 * 获取城市列表
	 *
	 * @param pageNum    页数
	 * @param pageSize   页大小
	 * @param parentCode 父级编码
	 * @param level      等级 1 热门城市 2 省 3 市 4 区
	 * @return ResultInfo
	 */
	@GetMapping(value = "/city/getCityList")
	public ResultInfo getCityList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
								  @RequestParam(value = "pageSize", defaultValue = "10000") Integer pageSize,
								  @RequestParam(value = "parentCode", required = false) Integer parentCode,
								  @RequestParam(value = "level", required = false) Integer level) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);


		if (parentCode != null) {
			params.put("parentCode", parentCode);
		}

		if (level != null) {
			params.put("level", level);
		}

		PagingTool pageTool = new PagingTool(pageNum, pageSize);
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = cityService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取城市总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("城市总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<City> cityList;
		try {
			cityList = cityService.getCityList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取城市列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (cityList.size() == 0) {
			rsInfo.setMessage("城市列表为空！");
			rsInfo.setData(new ArrayList<City>());
			return rsInfo;
		}
		rsInfo.setData(cityList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取城市列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除城市
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/city/deleteCityBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = cityService.deleteBatch(idArr);
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
