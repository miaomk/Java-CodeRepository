package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.AddressMapper;
import com.techwells.wumei.domain.Address;
import com.techwells.wumei.service.AddressService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("AddressService")
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addAddress(Address address) {
        int count;
        try {
            count = addressMapper.insertSelective(address);
            if (count < 0) {
                throw new Exception("添加收货地址失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加收货地址异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delAddress(int addressId) {
        int count;
        try {
            count = addressMapper.deleteByPrimaryKey(addressId);
            if (count < 0) {
                throw new Exception("删除收货地址失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除收货地址异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyAddress(Address address) {
        int count;
        try {
            count = addressMapper.updateByPrimaryKeySelective(address);
            if (count < 0) {
                throw new Exception("修改收货地址失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改收货地址异常！");
        }
        return count;
    }

    @Override
    public Address getAddressByAddressId(int addressId) {
        Address address;
        try {
            address = addressMapper.selectByPrimaryKey(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址详情异常！");
        }
        return address;
    }

    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = addressMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址总数异常！");
        }
        return count;
    }

    @Override
    public List<Address> getAddressList(PagingTool pagingTool) {
        List<Address> addressList;

        try {
            addressList = addressMapper.selectAddressList(pagingTool);
            StringBuilder addressBuilder;
            if (CollectionUtils.isNotEmpty(addressList)) {
                for (Address address : addressList) {
                    //拼接省市区详细地址
                    addressBuilder = new StringBuilder(address.getProvinceCity());
                    addressBuilder.append(address.getAddress());
                    address.setAddress(addressBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址列表异常");
        }
        return addressList;
    }

    @Override
    public int resetDefault(int userId) {
        int count;
        try {
            Address address = new Address();
            address.setUserId(userId);
            address.setIsDefault(2);
            count = addressMapper.updateByPrimaryKeySelective(address);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址总数异常！");
        }
        return count;
    }

    @Override
    public Address getUserAddress(int userId, Integer addressId) {
        Address address;
        try {
            address = addressMapper.selectUserAddress(userId, addressId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址详情异常！");
        }
        return address;
    }

    @Override
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int setDefaultAddress(Address address) {

        int count;
        try {
            address.setIsDefault(1);
            count = addressMapper.updateByPrimaryKeySelective(address);
		} catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址详情异常！");
        }
        return count;

    }

    @Override
    public Address getDefaultAddress(int userId) {
        Address address;
        try {
            address = addressMapper.selectDefaultAddress(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收货地址详情异常！");
        }
        return address;
    }

}
