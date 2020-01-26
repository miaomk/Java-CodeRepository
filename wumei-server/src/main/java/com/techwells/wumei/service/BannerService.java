package com.techwells.wumei.service;

import com.techwells.wumei.domain.Banner;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 轮播图接口
 *
 * @author miao
 */
public interface BannerService {

    /**
     * 增加轮播图
     *
     * @param banner 轮播图信息
     * @return int
     */
    int addBanner(Banner banner);

    /**
     * 删除轮播图
     *
     * @param bannerId 轮播图Id
     * @return int
     */
    int delBanner(int bannerId);

    /**
     * 修改轮播图
     *
     * @param banner 轮播图信息
     * @return int
     */
    int modifyBanner(Banner banner);

    /**
     * 根据轮播图id获取轮播图信息
     *
     * @param bannerId 轮播图id
     * @return Banner
     */
    Banner getBannerByBannerId(int bannerId);

    /**
     * 分页获取轮播图总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页获取轮播图列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<Banner> getBannerList(PagingTool pagingTool);

    /**
     * 批量删除广告
     *
     * @param idArr ID数组
     * @return int
     */
    int deleteBatch(String[] idArr);

    /**
     * 修改广告上下线状态
     *
     * @param bannerId 广告ID
     * @param status   广告状态
     * @return int
     */
    int updateActivatedStatus(int bannerId, String status);

}
