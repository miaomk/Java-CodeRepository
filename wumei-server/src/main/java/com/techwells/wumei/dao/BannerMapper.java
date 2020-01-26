package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Banner;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer bannerId);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer bannerId);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

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
    List<Banner> selectBannerList(PagingTool pagingTool);

    /**
     * 修改广告上下线状态
     *
     * @param bannerId 广告ID
     * @param status   广告状态
     * @return int
     */
    int updateActivatedStatus(@Param("bannerId") int bannerId, @Param("status") String status);

    /**
     * 批量删除广告
     *
     * @param ids 广告id数组
     * @return int
     */
    int batchDelete(String[] ids);
}