package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Report;
import com.techwells.wumei.service.ReportService;
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
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * 新增举报
     *
     * @param report 举报信息实体类
     * @param files  举报图片
     * @return Object
     */

    @RequestMapping(value = "/report/addReport")
    public @ResponseBody
    Object addReport(
            @RequestBody Report report,
            @RequestParam(value = "file", required = false) MultipartFile[] files) {
        ResultInfo rsInfo = new ResultInfo();

		if (null == report.getUserId()) {

			rsInfo.setMessage("举报人id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (null == report.getReportType()) {

			rsInfo.setMessage("举报类型不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (null == report.getRelationId()) {

			rsInfo.setMessage("被举报者id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

        // 处理图片
        if (files != null && files.length > 0) {
            StringBuilder fileUrl = new StringBuilder();
            for (MultipartFile file : files) {
                try {
                    fileUrl.append(FileUtil.uploadFile(file, "report")).append(",");
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!"".equals(fileUrl.toString())) {
                fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));

                report.setImageUrl(fileUrl.toString());
            }
        }

        report.setCreatedDate(new Date());
        int count;
        try {
            count = reportService.addReport(report);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("举报异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("举报成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("举报失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 修改模板信息
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @PutMapping(value = "/report/modifyReport/{reportId}")
    public @ResponseBody
    Object modifyReport(
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response, @PathVariable("reportId") Integer reportId) {
        ResultInfo rsInfo = new ResultInfo();

        String remarks = request.getParameter("remarks");

        if (remarks == null || remarks.equals("")) {
            rsInfo.setMessage("备注不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        Report report = new Report();

        report.setRemarks(remarks);
        report.setReportId(reportId);
        report.setUpdatedDate(new Date());
        int count = 0;
        try {
            count = reportService.modifyReport(report);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改模板异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改模板失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改模板成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * Description: 删除模板
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/report/deleteReport")
    public @ResponseBody
    Object deleteReport(HttpServletRequest request,
                        HttpSession session, HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        String reportId = request.getParameter("reportId");
        if (reportId == null || reportId.equals("")) {
            rsInfo.setMessage("模板Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        int count = 0;
        try {
            count = reportService.delReport(Integer.parseInt(reportId));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除模板异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除模板成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除模板失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * Description: 查看模板详情
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/report/getReportById")
    public @ResponseBody
    Object getReportById(HttpServletRequest request,
                         HttpSession session, HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        String reportId = request.getParameter("reportId");
        if (reportId == null || reportId.equals("")) {
            rsInfo.setMessage("模板ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        Report report = null;
        try {
            report = reportService.getReportByReportId(Integer.parseInt(reportId));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取模板信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (report == null) {
            rsInfo.setMessage("模板信息不存在！");
            rsInfo.setData(new Report());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(report);
        rsInfo.setMessage("获取模板成功！");
        return rsInfo;
    }

    /**
     * @param request
     * @param response
     * @return
     * @description 获取模板列表
     */
    @GetMapping(value = "/report/getReportList")
    public @ResponseBody
    Object getAdminList(HttpServletRequest request,
                        HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<String, Object>();

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        //举报人
        String reportName = request.getParameter("reportName");
        //举报类型
        String reportType = request.getParameter("reportName");

        if (pageNum == null || "".equals(pageNum)) {
            rsInfo.setMessage("页码不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (pageSize == null || pageSize.equals("")) {
            rsInfo.setMessage("页大小不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (reportName != null && !reportName.equals("")) {
            params.put("reportName", reportName);
        }

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);
        int totalCount = 0;

        try {
            totalCount = reportService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取模板总数异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (totalCount == 0) {
            rsInfo.setMessage("模板总数量为0！");
            rsInfo.setCode("23211");
            return rsInfo;
        }

        List<Report> reportList = null;
        try {
            reportList = reportService.getReportList(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取模板列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (reportList.size() == 0) {
            rsInfo.setMessage("模板列表为空！");
            rsInfo.setData(new ArrayList<Report>());
            return rsInfo;
        }
        rsInfo.setData(reportList);
        rsInfo.setTotal(totalCount);
        rsInfo.setMessage("获取模板列表成功！");
        return rsInfo;
    }

    /**
     * 批量删除模板
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/report/deleteReportBatch")
    public @ResponseBody
    Object deleteBatch(HttpServletRequest request,
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
            count = reportService.deleteBatch(idArr);
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
