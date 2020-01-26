package com.techwells.wumei.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techwells.wumei.domain.SpecificationsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.SpecificationsMapper;
import com.techwells.wumei.domain.Specifications;
import com.techwells.wumei.service.SpecificationsService;
import com.techwells.wumei.util.PagingTool;


@Service("SpecificationsService")
public class SpecificationsServiceImpl implements SpecificationsService {

	private SpecificationsMapper specificationsMapper;

	public SpecificationsMapper getSpecificationsMapper() {
		return specificationsMapper;
	}

	@Autowired
	public void setSpecificationsMapper(SpecificationsMapper specificationsMapper) {
		this.specificationsMapper = specificationsMapper;
	}

	@Override
	public int addSpecifications(Specifications specifications) {
		int count = 0;
		try {
			count = specificationsMapper.insertSelective(specifications);
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
	public int delSpecifications(int specificationsId) {
		int count = 0;
		try {
			count = specificationsMapper.deleteByPrimaryKey(specificationsId);
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
	public int modifySpecifications(Specifications specifications) {
		int count = 0;
		try {
			count = specificationsMapper.updateByPrimaryKeySelective(specifications);
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
	public Specifications getSpecificationsBySpecificationsId(int specificationsId) {
		Specifications specifications = null;
		try {
			specifications = specificationsMapper.selectByPrimaryKey(specificationsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return specifications;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = specificationsMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Specifications> getSpecificationsList(PagingTool pagingTool) {
		List<Specifications> specificationsList = null;

		try {
			specificationsList = specificationsMapper.selectSpecificationsList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return specificationsList;
	}

	@Override
	public int deleteBatch(List<SpecificationsProduct> specificationsProductList) {
		int count;
		try {
			count = specificationsMapper.batchDelete(specificationsProductList);
		}catch (Exception e){
			e.printStackTrace();
			throw new ApplicationContextException("批量删除商品规格异常");
		}
		return count;
	}
	
	@Override
	 public Object getSpecificationVoList(Integer productId) {
	        List<Specifications> productsSpecificationList = queryByProductid(productId);

	        Map<String, VO> map = new HashMap<>();
	        List<VO> specificationVoList = new ArrayList<>();

	        for (Specifications productsSpecification : productsSpecificationList) {
	            String specification = productsSpecification.getSpecification();
	            VO productsSpecificationVo = map.get(specification);
	            if (productsSpecificationVo == null) {
	                productsSpecificationVo = new VO();
	                productsSpecificationVo.setName(specification);
	                List<Specifications> valueList = new ArrayList<>();
	                valueList.add(productsSpecification);
	                productsSpecificationVo.setValueList(valueList);
	                map.put(specification, productsSpecificationVo);
	                specificationVoList.add(productsSpecificationVo);
	            } else {
	                List<Specifications> valueList = productsSpecificationVo.getValueList();
	                valueList.add(productsSpecification);
	            }
	        }

	        return specificationVoList;
	    }
	
	private class VO {
        private String name;
        private List<Specifications> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Specifications> getValueList() {
            return valueList;
        }

        public void setValueList(List<Specifications> valueList) {
            this.valueList = valueList;
        }
    }
	
	@Override
	public List<Specifications> queryByProductid(Integer productId) {
		return specificationsMapper.queryByProductid(productId);
	}

	@Override
	public int batchInsertSpecification(List<SpecificationsProduct> specificationsProductList) {
		int count;
		try{
			count = specificationsMapper.batchInsert(specificationsProductList);
		}catch (Exception e){
			e.printStackTrace();
			throw new ApplicationContextException("插入商品参数异常");
		}

		return count;
	}

	@Override
	public List<SpecificationsProduct> getSpecificationsList(Integer productId) {

		List<SpecificationsProduct> specificationsList;

		try {
			specificationsList = specificationsMapper.getSpecificationsList(productId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}

		return specificationsList;
	}

	@Override
	public int batchUpdateSpecification(List<SpecificationsProduct> specificationsProductList) {
		int count;
		try{
			count = specificationsMapper.batchUpdate(specificationsProductList);
		}catch (Exception e){
			e.printStackTrace();
			throw new ApplicationContextException("插入商品参数异常");
		}

		return count;
	}

	@Override
	public int batchDelete(String[] idArray) {

		int count;
		try{
			count = specificationsMapper.deleteBatch(idArray);
		}catch (Exception e){
			e.printStackTrace();
			throw new ApplicationContextException("插入商品参数异常");
		}

		return count;
	}


}
