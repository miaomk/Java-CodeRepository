package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.ProductImage;
import com.techwells.wumei.util.PagingTool;

public interface ProductImageService {

	// 增删改查
	public int addProductImage(ProductImage productImage);

	public int delProductImage(int productImageId);

	public int modifyProductImage(ProductImage productImage);

	ProductImage getProductImageByProductImageId(int productImageId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<ProductImage> getProductImageList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	/**
	 * 批量插入商品图片
	 * @param imageUrlArray 商品图片数组
	 * @param productId 商品id
	 * @return int
	 */
	int batchAddImage(String[] imageUrlArray,Integer productId);
}
