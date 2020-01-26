package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.PopularProductVO;
import com.techwells.wumei.domain.Product;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Product> selectProductList(PagingTool pagingTool);

 	Product selectByProductName(@Param("productName") String productName);

 	int batchDelete(@Param("ids")String[] ids);

    /**
     * 批量更新商品上下架状态
     *
     * @param ids       商品id数组
     * @param activated 上下架状态 1待审核，2审核通过（上架），3被驳回  4 下架
     * @return int
     */
    int batchUpdateActivated(@Param("array") String[] ids,
                             @Param("activated") String activated);

    /**
     * 分页查询好货推荐列表
     *
     * @param pageTool 分页
     * @return int
     */
    List<PopularProductVO> getPopularList(PagingTool pageTool);

    /**
     * 分页查询好货推荐列表总数
     *
     * @param pageTool 分页
     * @return int
     */
    int popularProductCount(PagingTool pageTool);
}