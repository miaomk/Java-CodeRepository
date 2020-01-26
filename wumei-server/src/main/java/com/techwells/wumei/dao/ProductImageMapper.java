package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.ProductImage;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface ProductImageMapper {
    int deleteByPrimaryKey(Integer imageId);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    ProductImage selectByPrimaryKey(Integer imageId);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<ProductImage> selectProductImageList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);

    /**
     * 批量插入商品图片
     * @param imageUrlArray 商品图片数组
     * @param productId 商品id
     * @return int
     */
 	int batchAddImage(@Param("imageUrlArray") String[] imageUrlArray,@Param("productId") Integer productId);
}