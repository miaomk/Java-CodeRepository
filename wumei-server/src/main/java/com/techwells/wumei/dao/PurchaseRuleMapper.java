package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.PurchaseRule;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface PurchaseRuleMapper {
    int deleteByPrimaryKey(Integer ruleId);

    int insert(PurchaseRule record);

    int insertSelective(PurchaseRule record);

    PurchaseRule selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(PurchaseRule record);

    int updateByPrimaryKey(PurchaseRule record);
    
    // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<PurchaseRule> selectPurchaseRuleList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}