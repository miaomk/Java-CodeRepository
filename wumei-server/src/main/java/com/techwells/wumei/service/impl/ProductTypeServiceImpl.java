package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ProductTypeMapper;
import com.techwells.wumei.domain.ProductType;
import com.techwells.wumei.domain.rs.BoProductType;
import com.techwells.wumei.service.ProductTypeService;
import com.techwells.wumei.util.PagingTool;


@Service("ProductTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

	private ProductTypeMapper productTypeMapper;

	public ProductTypeMapper getProductTypeMapper() {
		return productTypeMapper;
	}

	@Autowired
	public void setProductTypeMapper(ProductTypeMapper productTypeMapper) {
		this.productTypeMapper = productTypeMapper;
	}

	@Override
	public int addProductType(ProductType productType) {
		int count = 0;
		try {
			count = productTypeMapper.insertSelective(productType);
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
	public int delProductType(int productTypeId) {
		int count = 0;
		try {
			count = productTypeMapper.deleteByPrimaryKey(productTypeId);
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
	public int modifyProductType(ProductType productType) {
		int count = 0;
		try {
			count = productTypeMapper.updateByPrimaryKeySelective(productType);
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
	public ProductType getProductTypeByProductTypeId(int productTypeId) {
		ProductType productType = null;
		try {
			productType = productTypeMapper.selectByPrimaryKey(productTypeId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return productType;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = productTypeMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<ProductType> getProductTypeList(PagingTool pagingTool) {
		List<ProductType> productTypeList = null;

		try {
			productTypeList = productTypeMapper.selectProductTypeList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return productTypeList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		int count = 0;

		try {
			count = productTypeMapper.batchDelete(idArr);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("商品类型删除异常！");
		}
		return count;
	}

	@Override
	public List<ProductType> queryL1() {
		return productTypeMapper.queryL1();
	}

	@Override
	public List<ProductType> queryByPid(Integer typeId) {
		return productTypeMapper.queryByPid(typeId);
	}

	@Override
	public List<BoProductType> listWithChildren() {
		return productTypeMapper.listWithChildren();
	}

}
