package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.CommodityTypeMapper;
import com.techwells.wumei.domain.CommodityType;
import com.techwells.wumei.service.CommodityTypeService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 租赁商品类型业务实现类
 *
 * @author miao
 */
@Service("CommodityTypeService")
public class CommodityTypeServiceImpl implements CommodityTypeService {

	@Resource
	private CommodityTypeMapper commodityTypeMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addCommodityType(CommodityType commodityType) {
		int count;
		try {
			count = commodityTypeMapper.insertSelective(commodityType);
			if (count < 0) {
				throw new Exception("添加租赁商品类型失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加租赁商品类型异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delCommodityType(int commodityTypeId) {
		int count;
		try {
			count = commodityTypeMapper.deleteByPrimaryKey(commodityTypeId);
			if (count < 0) {
				throw new Exception("删除租赁商品类型失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除租赁商品类型异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyCommodityType(CommodityType commodityType) {
		int count;
		try {
			count = commodityTypeMapper.updateByPrimaryKeySelective(commodityType);
			if (count < 0) {
				throw new Exception("修改租赁商品类型失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改租赁商品类型异常！");
		}
		return count;
	}


	@Override
	public CommodityType getCommodityTypeInfo(int commodityTypeId) {
		CommodityType commodityType;
		try {
			commodityType = commodityTypeMapper.selectByPrimaryKey(commodityTypeId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取租赁商品类型详情异常！");
		}
		return commodityType;
	}

	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = commodityTypeMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取租赁商品类型总数异常！");
		}
		return count;
	}

	@Override
	public List<CommodityType> getCommodityTypeList(PagingTool pagingTool) {
		List<CommodityType> commodityTypeList;

		try {
			commodityTypeList = commodityTypeMapper.selectCommodityTypeList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取租赁商品类型列表异常");
		}
		return commodityTypeList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteBatch(String[] idArr) {
		
		return 0;
	}

}
