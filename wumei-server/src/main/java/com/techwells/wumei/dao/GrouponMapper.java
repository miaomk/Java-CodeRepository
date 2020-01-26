package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Groupon;
import com.techwells.wumei.domain.rs.RsGroupon;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface GrouponMapper {
    int deleteByPrimaryKey(Integer grouponId);

    int insert(Groupon record);

    int insertSelective(Groupon record);

    Groupon selectByPrimaryKey(Integer grouponId);

    int updateByPrimaryKeySelective(Groupon record);

    int updateByPrimaryKey(Groupon record);
    
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsGroupon> selectGrouponList(PagingTool pagingTool);
	
	int batchDelete(@Param("ids")String[] ids);
	
	List<Groupon> getGrouponListByProductId(Integer productId);

}