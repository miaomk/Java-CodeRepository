package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.SpecificationsProduct;
import com.techwells.wumei.domain.Specifications;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface SpecificationsMapper {
    int deleteByPrimaryKey(Integer specificationsId);

    int insert(Specifications record);

    int insertSelective(Specifications record);

    Specifications selectByPrimaryKey(Integer specificationsId);

    int updateByPrimaryKeySelective(Specifications record);

    int updateByPrimaryKey(Specifications record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Specifications> selectSpecificationsList(PagingTool pagingTool);
  	
  	int batchDelete(List<SpecificationsProduct> specificationsProductList);

	List<Specifications> queryByProductid(Integer productId);

    int batchInsert(List<SpecificationsProduct> specificationsProductList);

    List<SpecificationsProduct> getSpecificationsList(Integer productId);

    int batchUpdate(List<SpecificationsProduct> specificationsProductList);

    int deleteBatch(@Param("idArray") String[] idArray);
}