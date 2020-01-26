package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ProductImageMapper;
import com.techwells.wumei.domain.ProductImage;
import com.techwells.wumei.service.ProductImageService;
import com.techwells.wumei.util.PagingTool;


@Service("ProductImageService")
public class ProductImageServiceImpl implements ProductImageService {

	private ProductImageMapper productImageMapper;

	public ProductImageMapper getProductImageMapper() {
		return productImageMapper;
	}

	@Autowired
	public void setProductImageMapper(ProductImageMapper productImageMapper) {
		this.productImageMapper = productImageMapper;
	}

	@Override
	public int addProductImage(ProductImage productImage) {
		int count = 0;
		try {
			count = productImageMapper.insertSelective(productImage);
			if (count < 0) {
				throw new Exception("添加模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加模板异常！");
		}
		return count;
	}

	@Override
	public int delProductImage(int productImageId) {
		int count = 0;
		try {
			count = productImageMapper.deleteByPrimaryKey(productImageId);
			if (count < 0) {
				throw new Exception("删除模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除模板异常！");
		}
		return count;
	}

	@Override
	public int modifyProductImage(ProductImage productImage) {
		int count = 0;
		try {
			count = productImageMapper.updateByPrimaryKeySelective(productImage);
			if (count < 0) {
				throw new Exception("修改模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改模板异常！");
		}
		return count;
	}

	@Override
	public ProductImage getProductImageByProductImageId(int productImageId) {
		ProductImage productImage = null;
		try {
			productImage = productImageMapper.selectByPrimaryKey(productImageId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return productImage;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = productImageMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<ProductImage> getProductImageList(PagingTool pagingTool) {
		List<ProductImage> productImageList = null;

		try {
			productImageList = productImageMapper.selectProductImageList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return productImageList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchAddImage(String[] imageUrlArray, Integer productId) {
		int count = 0;

		try {
			count = productImageMapper.batchAddImage(imageUrlArray,productId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("批量增加商品图片异常！");
		}
		return count;
	}

}
