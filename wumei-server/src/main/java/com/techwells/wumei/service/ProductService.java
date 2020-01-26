package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.PopularProductVO;
import com.techwells.wumei.domain.Product;
import com.techwells.wumei.util.PagingTool;

public interface ProductService {

    // 增删改查
    public int addProduct(Product product);

    public int delProduct(int productId, String deleteStatus);

    public int modifyProduct(Product product);

    Product getProductByProductId(int productId);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<Product> getProductList(PagingTool pagingTool);

    // 批量删除
    int deleteBatch(String[] idArr);

    /**
     * 批量更新上下架状态
     *
     * @param productIdArray 商品id数组
     * @param status         更改的状态值
     * @return int
     */
    int batchUpdateIsOnSale(String[] productIdArray, String status);

    /**
     * 查询好货推荐列表
     *
     * @param pageTool 分页
     * @return List
     */
    List<PopularProductVO> getPopularList(PagingTool pageTool);

    /**
     * 分页好货推荐列表总数
     *
     * @param pageTool 分页
     * @return int
     */
    int popularProductCountTotal(PagingTool pageTool);
}
