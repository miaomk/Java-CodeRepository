package com.techwells.wumei.service;

import com.techwells.wumei.domain.Address;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface AddressService {

	// 增删改查
	public int addAddress(Address address);

	public int delAddress(int addressId);

	public int modifyAddress(Address address);

	Address getAddressByAddressId(int addressId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Address> getAddressList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	int resetDefault(int parseInt);

	Address getUserAddress(int userId, Integer addressId);
	
	int setDefaultAddress(Address address);

	Address getDefaultAddress(int userId);
}
