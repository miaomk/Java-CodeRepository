package com.techwells.wumei.service;

import com.techwells.wumei.domain.CommodityType;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 租赁商品类型业务层
 *
 * @author miao
 */
public interface CommodityTypeService {
    /**
     * 增加租赁商品类型
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int addCommodityType(CommodityType commodityType);

    /**
     * 删除租赁商品类型
     *
     * @param commodityTypeId 租赁商品类型id
     * @return int
     */
    int delCommodityType(int commodityTypeId);

    /**
     * 修改租赁商品类型
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int modifyCommodityType(CommodityType commodityType);

    /**
     * 获取租赁商品类型信息
     *
     * @param commodityTypeId 租赁商品类型id
     * @return int
     */
    CommodityType getCommodityTypeInfo(int commodityTypeId);

    /**
     * 分页查询租赁商品类型列表总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页查询租赁商品类型列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<CommodityType> getCommodityTypeList(PagingTool pagingTool);

    /**
     * 批量删除租赁商品类型
     *
     * @param idArr 租赁商品类型id数组
     * @return int
     */
    int deleteBatch(String[] idArr);
}
