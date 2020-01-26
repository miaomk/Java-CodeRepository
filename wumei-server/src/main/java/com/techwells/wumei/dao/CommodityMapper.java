package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Commodity;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.domain.rs.RsCommodity;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityMapper {
    int deleteByPrimaryKey(Integer commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Integer commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    int batchDelete(@Param("ids") String[] commodityIdArrays);

    int deleteCommodity(@Param("commodityId") String commodityId);

    /**
     * 批量修改租赁商品状态
     *
     * @param commodityIdArrays 商品id数组
     * @param activated         上下架状态
     * @return int
     */
    int batchUpdateCommodityStatus(@Param("ids") String[] commodityIdArrays, @Param("activated") String activated);

    /**
     * 分页查询租赁商品list
     *
     * @param pageTool 分页
     * @return List
     */
    List<RsCommodity> getCommodityList(PagingTool pageTool);

    /**
     * 通过商品id查询商品信息
     *
     * @param commodityId 租赁商品id
     * @return RsCommodity
     */
    RsCommodity getCommodityInfo(@Param("commodityId") String commodityId);

    int countTotal(PagingTool pageTool);

    /**
     * 批量修改租赁商品状态
     *
     * @param commodityIdArrays 商品id数组
     * @param recommend         推荐状态
     * @return int
     */
    int batchUpdateCommodityRecommend(@Param("ids") String[] commodityIdArrays, @Param("recommend") String recommend);

    /**
     * 获取收藏的严选租赁商品列表
     *
     * @param relationIdList 被收藏的严选租赁商品id列表
     * @param pageTool       分页
     * @return List
     */
    List<RsCollect> getCollectCommodityList(@Param("list") List<Integer> relationIdList,
                                            @Param("pageTool") PagingTool pageTool);
}