package com.techwells.wumei.dao;

import com.techwells.wumei.domain.CommodityType;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 租赁商品类型Mapper
 *
 * @author miao
 */
public interface CommodityTypeMapper {
    /**
     * 删除租赁商品类型
     *
     * @param typeId 租赁商品类型id
     * @return int
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * 增加租赁商品类型
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int insert(CommodityType commodityType);

    /**
     * 增加租赁商品类型
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int insertSelective(CommodityType commodityType);

    /**
     * 获取租赁商品类型信息
     *
     * @param typeId 租赁商品类型id
     * @return int
     */
    CommodityType selectByPrimaryKey(Integer typeId);

    /**
     * 修改租赁商品类型部分信息
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int updateByPrimaryKeySelective(CommodityType commodityType);

    /**
     * 修改租赁商品类型信息
     *
     * @param commodityType 租赁商品类型实体类
     * @return int
     */
    int updateByPrimaryKey(CommodityType commodityType);

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
    List<CommodityType> selectCommodityTypeList(PagingTool pagingTool);
}