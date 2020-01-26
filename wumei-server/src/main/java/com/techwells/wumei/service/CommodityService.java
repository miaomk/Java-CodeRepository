package com.techwells.wumei.service;

import com.techwells.wumei.domain.Commodity;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.domain.rs.RsCommodity;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface CommodityService {
    int addCommodity(Commodity commodity);

    int deleteCommodity(String commodityId);

    int modifyCommodity(Commodity commodity);

    /**
     * 修改上下架状态
     *
     * @param commodityId 商品id
     * @param activated   状态
     * @return int
     */
    int modifyCommodityStatus(String commodityId, String activated);

    /**
     * 分页查询租赁商品列表
     *
     * @param pageTool 分页查询
     * @return List
     */
    List<RsCommodity> getCommodityList(PagingTool pageTool);

    /**
     * 通过商品id查询商品信息
     *
     * @param commodityId 租赁商品id
     * @param userId      用户id
     * @return RsCommodity
     */
    RsCommodity getCommodityInfoById(String commodityId, String userId);

    int countTotal(PagingTool pageTool);

    /**
     * 修改推荐状态
     *
     * @param commodityId 商品id
     * @param recommend   推荐状态
     * @return int
     */
    int editCommodityRecommend(String commodityId, String recommend);

    /**
     * 获取收藏的严选租赁商品列表
     *
     * @param relationIdList 被收藏的严选租赁商品id列表
     * @param pageTool 分页
     * @return List
     */
    List<RsCollect> getCollectCommodityList(List<Integer> relationIdList,PagingTool pageTool);
}
