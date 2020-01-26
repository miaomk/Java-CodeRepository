package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Authority;
import com.techwells.wumei.service.AuthorityService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
public class AuthorityController {
	private AuthorityService authorityService;

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * 添加权限
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@PostMapping(value = "/authority/addAuthority")
	public @ResponseBody Object addAuthority(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String pageName = request.getParameter("pageName");
		String pageNameEn = request.getParameter("pageNameEn");
		String pageUrl = request.getParameter("pageUrl");
		String parentId = request.getParameter("parentId");
		String level = request.getParameter("level");
		String activated = request.getParameter("activated");
		String description = request.getParameter("description");
		

		if (pageName == null || "".equals(pageName)) {
			rsInfo.setMessage("权限名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if(pageNameEn == null ||"".equals(pageNameEn)) {
			rsInfo.setMessage("权限标识不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if(pageUrl == null ||"".equals(pageUrl)) {
			rsInfo.setMessage("请求url不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		if(level == null ||"".equals(level)) {
			rsInfo.setMessage("层级不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}
		Authority authority = new Authority();
		authority.setPageName(pageName);
		authority.setCreatedDate(new Date());
		//authority.setPageNameEn(pageNameEn);
		authority.setPageUrl(pageUrl);
		if(parentId !=null &&"".equals(parentId)) {
			authority.setParentId(Integer.parseInt(parentId));
		}
		if(description !=null &&"".equals(description)) {
			authority.setDescription(description);
		}
		
		int count = 0;
		try {
			count = authorityService.addAuthority(authority);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加权限异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加权限成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加权限失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改权限信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/authority/modifyAuthority/{authorityId}")
	public @ResponseBody Object modifyAuthority(HttpServletRequest request,
			HttpSession session, HttpServletResponse response,@PathVariable("authorityId") Integer authorityId) {
		ResultInfo rsInfo = new ResultInfo();

		String pageName = request.getParameter("pageName");
		String pageNameEn = request.getParameter("pageNameEn");
		String pageUrl = request.getParameter("pageUrl");
		String parentId = request.getParameter("parentId");
		String level = request.getParameter("level");
		String activated = request.getParameter("activated");
		String description = request.getParameter("description");
		

		if (pageName == null || "".equals(pageName)) {
			rsInfo.setMessage("权限名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if(pageNameEn == null ||"".equals(pageNameEn)) {
			rsInfo.setMessage("权限标识不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if(pageUrl == null ||"".equals(pageUrl)) {
			rsInfo.setMessage("请求url不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		if(level == null ||"".equals(level)) {
			rsInfo.setMessage("层级不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}
		Authority authority = new Authority();
		authority.setAuthorityId(authorityId);
		authority.setPageName(pageName);
		authority.setCreatedDate(new Date());
		//authority.setPageNameEn(pageNameEn);
		authority.setPageUrl(pageUrl);
		if(parentId !=null &&"".equals(parentId)) {
			authority.setParentId(Integer.parseInt(parentId));
		}
		if(description !=null &&"".equals(description)) {
			authority.setDescription(description);
		}
		int count = 0;
		try {
			count = authorityService.modifyAuthority(authority);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改权限异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改权限失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改权限成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除权限
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authority/deleteAuthority/{authorityId}")
	public @ResponseBody Object deleteAuthority(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String authorityId = request.getParameter("authorityId");
		if (authorityId == null || authorityId.equals("")) {
			rsInfo.setMessage("权限Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = authorityService.delAuthority(Integer.parseInt(authorityId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除权限异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除权限成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除权限失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 查看权限详情
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authority/getAuthorityById")
	public @ResponseBody Object getAuthorityById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String authorityId = request.getParameter("authorityId");
		if (authorityId == null || authorityId.equals("")) {
			rsInfo.setMessage("权限ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Authority authority = null;
		try {
			authority = authorityService.getAuthorityByAuthorityId(Integer.parseInt(authorityId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取权限信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (authority == null) {
			rsInfo.setMessage("权限信息不存在！");
			rsInfo.setData(new Authority());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(authority);
		rsInfo.setMessage("获取权限成功！");
		return rsInfo;
	}

	/**
	 * @description 获取权限列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authority/getAuthorityList")
	public @ResponseBody Object getAuthorityList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String authorityName = request.getParameter("authorityName");

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

		if (authorityName != null && !authorityName.equals("")) {
			params.put("authorityName", authorityName);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = authorityService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取权限总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("权限总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Authority> authorityList = null;
		try {
			authorityList = authorityService.getAuthorityList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取权限列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (authorityList.size() == 0) {
			rsInfo.setMessage("权限列表为空！");
			rsInfo.setData(new ArrayList<Authority>());
			return rsInfo;
		}
		rsInfo.setData(authorityList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取权限列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authority/deleteAuthorityBatch")
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
			count = authorityService.deleteBatch(idArr);
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
	 * @description 获取所有权限(无分页)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authority/getAuthoritys")
	public @ResponseBody Object getAuthoritys(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		List<Authority> authorityList = null;
		try {
			authorityList = authorityService.getAuthoritys();
		} catch (Exception e) {
			rsInfo.setMessage("获取权限列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (authorityList.size() == 0) {
			rsInfo.setMessage("权限为空！");
			rsInfo.setData(new ArrayList<Authority>());
			return rsInfo;
		}
		rsInfo.setData(authorityList);
		rsInfo.setMessage("获取权限成功！");
		return rsInfo;
	}
}
