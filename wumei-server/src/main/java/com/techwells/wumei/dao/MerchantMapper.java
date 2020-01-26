package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.RsMerchant;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface MerchantMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Merchant> selectMerchantList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);

    /**
     * 获取商户信息
     *
     * @param merchantId 商户id（用户id）
     * @return RsMerchant
     */
    RsMerchant getMerchantInfo(@Param(("merchantId")) String merchantId);
}