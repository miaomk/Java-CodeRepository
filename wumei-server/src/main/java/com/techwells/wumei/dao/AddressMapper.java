package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Address;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Address> selectAddressList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
 	
    Address selectUserAddress(@Param("userId") int userId,@Param("addressId") Integer addressId);
    
    Address selectDefaultAddress(@Param("userId") int userId);
    
    Integer setToNoDefault(@Param("userId") int userId);
    
}