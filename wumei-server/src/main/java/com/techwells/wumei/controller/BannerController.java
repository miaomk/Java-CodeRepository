package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Banner;
import com.techwells.wumei.service.BannerService;
import com.techwells.wumei.util.DateUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.UploadImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 轮播图Controller
 *
 * @author miao
 */
@Controller
@RestController
public class BannerController {

    @Resource
    private BannerService bannerService;

    /**
     * 添加轮播图
     *
     * @param request 请求
     * @param files   图片
     * @return ResultInfo
     */

    @PostMapping(value = "/banner/addBanner")
    public ResultInfo addBanner(HttpServletRequest request,
                                @RequestParam(value = "file", required = false) MultipartFile[] files) {
        ResultInfo rsInfo = new ResultInfo();

        String bannerName = request.getParameter("bannerName");
        String jumpUrl = request.getParameter("jumpUrl");
        String bannerType = request.getParameter("bannerType");
        String description = request.getParameter("description");
        String sortIndex = request.getParameter("sortIndex");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String activated = request.getParameter("status");
        String imageUrl = request.getParameter("imageUrl");

        if (bannerType == null || "".equals(bannerType)) {
            rsInfo.setMessage("banner类型不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (sortIndex == null || "".equals(sortIndex)) {
            sortIndex = "9999";
        }

        if (endDate == null || "".equals(endDate)) {
            rsInfo.setMessage("结束时间不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (startDate == null || "".equals(startDate)) {
            rsInfo.setMessage("开始时间不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        Banner banner = new Banner();
        banner.setBannerName(bannerName);
        banner.setBannerType(Integer.valueOf(bannerType));
        banner.setJumpUrl(jumpUrl);
        banner.setSortIndex(Integer.parseInt(sortIndex));
        banner.setDescription(description);
        banner.setActivated(Integer.parseInt(activated));
        banner.setImageUrl(imageUrl);

        try {
            banner.setStartDate(DateUtil.transferDate(startDate));
            banner.setEndDate(DateUtil.transferDate(endDate));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("日期格式错误！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        // 处理图片
        if (files != null && files.length > 0) {
            String fileUrl = UploadImageUtil.uploadImage("banner", files);

            if (!"".equals(fileUrl)) {

                banner.setImageUrl(fileUrl);
            }
        }

        int count;
        try {
            count = bannerService.addBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("添加轮播图异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("添加轮播图成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("添加轮播图失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 修改轮播图信息
     *
     * @param request  请求
     * @param files    图片
     * @param bannerId 轮播图id
     * @return ResultInfo
     */
    @PutMapping(value = "/banner/modifyBanner/{bannerId}")
    public ResultInfo modifyBanner(HttpServletRequest request,
                                   @RequestParam(value = "file", required = false) MultipartFile[] files,
                                   @PathVariable("bannerId") Integer bannerId) {
        ResultInfo rsInfo = new ResultInfo();

        String bannerName = request.getParameter("bannerName");
        String jumpUrl = request.getParameter("jumpUrl");
        String bannerType = request.getParameter("bannerType");
        String description = request.getParameter("description");
        String sortIndex = request.getParameter("sortIndex");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String activated = request.getParameter("status");

        if (bannerId == null) {
            rsInfo.setMessage("bannerID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (sortIndex == null || "".equals(sortIndex)) {
            sortIndex = "9999";
        }

        Banner banner = new Banner();
        banner.setBannerId(bannerId);
        banner.setBannerType(Integer.parseInt(bannerType));
        banner.setBannerName(bannerName);
        banner.setJumpUrl(jumpUrl);
        banner.setSortIndex(Integer.parseInt(sortIndex));
        banner.setDescription(description);
        banner.setActivated(Integer.parseInt(activated));

        try {
            banner.setStartDate(DateUtil.timeStampTransfer(startDate));
            banner.setEndDate(DateUtil.timeStampTransfer(endDate));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("日期格式错误！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        // 处理图片
        if (files != null && files.length > 0) {
            String fileUrl = UploadImageUtil.uploadImage("banner", files);

            if (!"".equals(fileUrl)) {

                banner.setImageUrl(fileUrl);
            }
        }

        int count;
        try {
            count = bannerService.modifyBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改轮播图异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改轮播图失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改轮播图成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除轮播图
     *
     * @param bannerId 轮播图id
     * @return ResultInfo
     */
    @RequestMapping(value = "/banner/deleteBanner")
    public ResultInfo deleteBanner(@RequestParam("bannerId") Integer bannerId) {
        ResultInfo rsInfo = new ResultInfo();

        if (bannerId == null) {
            rsInfo.setMessage("轮播图Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        int count;
        try {
            count = bannerService.delBanner(bannerId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除轮播图异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (count > 0) {
            rsInfo.setMessage("删除轮播图成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除轮播图失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看轮播图详情
     *
     * @param bannerId 轮播图id
     * @return ResultInfo
     */
    @GetMapping(value = "/banner/getBannerById/{bannerId}")
    public ResultInfo getBannerById(@PathVariable("bannerId") Integer bannerId) {
        ResultInfo rsInfo = new ResultInfo();

        Banner banner;
        try {
            banner = bannerService.getBannerByBannerId(bannerId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取轮播图信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (banner == null) {
            rsInfo.setMessage("轮播图信息不存在！");
            rsInfo.setData(new Banner());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(banner);
        rsInfo.setMessage("获取轮播图成功！");
        return rsInfo;
    }

    /**
     * 获取轮播图列表
     *
     * @param request 请求
     * @return ResultInfo
     */
    @GetMapping(value = "/banner/getBannerList")
    public ResultInfo getBannerList(HttpServletRequest request) {
        ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        String bannerName = request.getParameter("bannerName");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String activated = request.getParameter("activated");

        if (pageNum == null || "".equals(pageNum)) {
            rsInfo.setMessage("页码不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (pageSize == null || "".equals(pageSize)) {
            rsInfo.setMessage("页大小不能为空！");
            rsInfo.setCode("10002");
            return rsInfo;
        }

        if (bannerName != null && !"".equals(bannerName)) {
            params.put("bannerName", bannerName);
        }
        if (name != null && !"".equals(name)) {
            params.put("name", name);
        }
        if (type != null && !"".equals(type)) {
            params.put("type", type);
        }
        if (startTime != null && !"".equals(startTime)) {
            params.put("startTime", startTime);
        }
        if (endTime != null && !"".equals(endTime)) {
            params.put("endTime", endTime);
        }
        if (activated != null && !"".equals(activated)) {
            params.put("activated", activated);
        }

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);

        int totalCount;
        try {
            totalCount = bannerService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取轮播图总数异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (totalCount == 0) {
            rsInfo.setMessage("轮播图总数量为0！");
            rsInfo.setCode("23211");
            return rsInfo;
        }

        List<Banner> bannerList;
        try {
            bannerList = bannerService.getBannerList(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取轮播图列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (bannerList.size() == 0) {
            rsInfo.setMessage("轮播图列表为空！");
            rsInfo.setData(new ArrayList<Banner>());
            return rsInfo;
        }
        rsInfo.setData(bannerList);
        rsInfo.setTotal(totalCount);
        rsInfo.setMessage("获取轮播图列表成功！");
        return rsInfo;
    }

    /**
     * 批量删除轮播图
     *
     * @param bannerId 轮播图id
     * @return ResultInfo
     */
    @RequestMapping(value = "/banner/deleteBannerBatch")
    public ResultInfo deleteBatch(@RequestParam("bannerId") String bannerId) {
        ResultInfo rsInfo = new ResultInfo();

        String[] idArr = bannerId.split(",");

        if (idArr.length < 1) {
            rsInfo.setMessage("ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = bannerService.deleteBatch(idArr);
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
     * 修改轮播图上下线状态
     *
     * @param status   状态
     * @param bannerId 轮播图ID
     * @return ResultInfo
     */
    @ResponseBody
    @PostMapping("/banner/updateActivatedStatus/{bannerId}")
    public ResultInfo editActivatedStatus(@RequestParam("status") String status,
                                          @PathVariable("bannerId") Integer bannerId) {


        ResultInfo resultInfo = new ResultInfo();

        if (bannerId == null) {
            resultInfo.setMessage("bannerID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (status == null || "".equals(status)) {
            resultInfo.setMessage("更改的状态不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        int count = bannerService.updateActivatedStatus(bannerId, status);
        if (count < 1) {
            resultInfo.setMessage("修改状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        resultInfo.setMessage("修改状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }
}
