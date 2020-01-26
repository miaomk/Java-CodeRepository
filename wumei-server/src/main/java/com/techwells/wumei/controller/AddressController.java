package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Address;
import com.techwells.wumei.service.AddressService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 添加收货地址
     *
     * @param address 收货地址实体类
     * @return ResultInfo
     */

    @PostMapping(value = "/address/addAddress")
    public ResultInfo addAddress(@RequestBody Address address) {
        ResultInfo rsInfo = new ResultInfo();

        if (address.getAddress() == null || "".equals(address.getAddress())) {
            rsInfo.setMessage("收货地址不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (address.getUserId() == null) {
            rsInfo.setMessage("用户id不能为空！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (address.getAddressee() == null || "".equals(address.getAddressee())) {
            rsInfo.setMessage("收件人姓名不能为空！");
            rsInfo.setCode("10002");
            return rsInfo;
        }
        if (address.getAddresseePhone() == null || "".equals(address.getAddresseePhone())) {
            rsInfo.setMessage("收件人联系方式不能为空！");
            rsInfo.setCode("10003");
            return rsInfo;
        }
        if (address.getProvinceCity() == null || "".equals(address.getProvinceCity())) {
            rsInfo.setMessage("省市区不能为空！");
            rsInfo.setCode("10004");
            return rsInfo;
        }

        HashMap<String, Object> params = new HashMap<>(16);

        params.put("userId", address.getUserId());

        PagingTool pageTool = new PagingTool(1, 9999);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = addressService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取收货地址总数异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (totalCount == 0) {
            //如果数据库中没有收货地址则第一个地址为默认地址
            address.setIsDefault(1);
        } else if (null != address.getIsDefault() && 1 == address.getIsDefault() && totalCount > 0) {
            //如果添加默认地址且已经有收货地址了则判断，之前的收货地址是否含有默认的地址，有则更改默认状态
            //查询收货地址列表
            Address defaultAddress = addressService.getDefaultAddress(address.getUserId());
            //之前的收货地址中有默认的收货地址，则修改默认状态
            if (null != defaultAddress) {
                defaultAddress.setIsDefault(2);
                addressService.modifyAddress(defaultAddress);
            }
        } else if (null != address.getIsDefault() && 2 == address.getIsDefault() && totalCount > 0) {
            Address defaultAddress = addressService.getDefaultAddress(address.getUserId());
            //之前的收货地址中没有默认的收货地址，则将现在的收货地址设置为默认地址
            if (null == defaultAddress) {
                address.setIsDefault(1);
            }
        }

        int count;
        try {
            count = addressService.addAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("添加收货地址异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (count > 0) {
            rsInfo.setMessage("添加收货地址成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("添加收货地址失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 修改收货地址信息
     *
     * @param address 修改收货地址信息
     * @return ResultInfo
     */
    @RequestMapping(value = "/address/modifyAddress")
    public ResultInfo modifyAddress(@RequestBody Address address) {
        ResultInfo rsInfo = new ResultInfo();

        if (address.getAddress() == null || "".equals(address.getAddress())) {
            rsInfo.setMessage("收货地址不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (address.getUserId() == null) {
            rsInfo.setMessage("用户Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        //如果现在的收货地址为默认
        if (1 == address.getIsDefault()) {
            //查询之前的收货地址中是否已经存在了默认地址
            Address defaultAddress = addressService.getDefaultAddress(address.getUserId());
            //之前的收货地址中有默认的收货地址且不是现在这个收货地址，则修改默认状态
            if (null != defaultAddress && !defaultAddress.getAddressId().equals(address.getAddressId())) {
                defaultAddress.setIsDefault(2);
                addressService.modifyAddress(defaultAddress);
            }

        }

        int count;
        try {
            count = addressService.modifyAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改收货地址异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (address.getIsDefault() != null && 1 == address.getIsDefault()) {
            try {
                count = addressService.setDefaultAddress(address);
            } catch (Exception e) {
                e.printStackTrace();

                rsInfo.setMessage("设置默认地址失败！");
                rsInfo.setCode("15213");
                return rsInfo;
            }

        }

        if (count < 1) {
            rsInfo.setMessage("修改收货地址失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改收货地址成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除收货地址
     *
     * @param addressId 收货地址id
     * @return ResultInfo
     */
    @RequestMapping(value = "/address/deleteAddress")
    public ResultInfo deleteAddress(@RequestParam("addressId") Integer addressId) {
        ResultInfo rsInfo = new ResultInfo();

        if (null == addressId) {
            rsInfo.setMessage("收货地址id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = addressService.delAddress(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除收货地址异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除收货地址成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除收货地址失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看收货地址详情
     *
     * @param addressId 地址id
     * @return ResultInfo
     */
    @RequestMapping(value = "/address/getAddressById")
    public ResultInfo getAddressById(@RequestParam("addressId") Integer addressId) {
        ResultInfo rsInfo = new ResultInfo();

        if (addressId == null) {
            rsInfo.setMessage("收货地址ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        Address address;
        try {
            address = addressService.getAddressByAddressId(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取收货地址信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (address == null) {
            rsInfo.setMessage("收货地址信息不存在！");
            rsInfo.setData(new Address());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(address);
        rsInfo.setMessage("获取收货地址成功！");
        return rsInfo;
    }

    /**
     * 获取收货地址列表
     *
     * @param pageNum  页数
     * @param pageSize 页大小
     * @param userId   用户id
     * @return ResultInfo
     */
    @RequestMapping(value = "/address/getAddressList")
    public ResultInfo getAddressList(@RequestParam("pageNum") String pageNum,
                                     @RequestParam("pageSize") String pageSize,
                                     @RequestParam("userId") String userId) {
        ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        if (pageNum == null || "".equals(pageNum)) {
            rsInfo.setMessage("页码不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        if (pageSize == null || "".equals(pageSize)) {
            rsInfo.setMessage("页大小不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (userId != null && !"".equals(userId)) {
            params.put("userId", userId);
        }

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = addressService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取收货地址总数异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (totalCount == 0) {
            rsInfo.setMessage("收货地址总数量为0！");
            rsInfo.setCode("200");
            return rsInfo;
        }

        List<Address> addressList;
        try {
            addressList = addressService.getAddressList(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取收货地址列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (addressList.size() == 0) {
            rsInfo.setMessage("收货地址列表为空！");
            rsInfo.setData(new ArrayList<Address>());
            return rsInfo;
        }
        rsInfo.setData(addressList);
        rsInfo.setTotal(totalCount);
        rsInfo.setMessage("获取收货地址列表成功！");
        return rsInfo;
    }

    /**
     * 批量删除收货地址
     *
     * @param id 收货id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/address/deleteAddressBatch")
    public ResultInfo deleteBatch(@RequestParam("id") String id) {
        ResultInfo rsInfo = new ResultInfo();
        if (StringUtil.isEmpty(id)) {
            rsInfo.setMessage("ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        String[] idArr = id.split(",");
        if (idArr.length < 1) {
            rsInfo.setMessage("ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = addressService.deleteBatch(idArr);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("批量删除异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (count > 0) {
            rsInfo.setMessage("批量删除成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("批量删除失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }


    /**
     * Description: 设置默认地址
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     * @return count
     */
    @RequestMapping(value = "/address/setDefaultAddress")
    public @ResponseBody
    Object setDefaultAddress(@RequestParam("userId") String userId,
                             @RequestParam("addressId") Integer addressId) {

        ResultInfo rsInfo = new ResultInfo();

        if (userId == null || "".equals(userId)) {
            rsInfo.setMessage("用户ID不能为空！");
            rsInfo.setCode("15121");
            return rsInfo;
        }
        if (addressId == null) {
            rsInfo.setMessage("收货地址不能为空！");
            rsInfo.setCode("15122");
            return rsInfo;
        }

        Address address;
        try {
            address = addressService.getAddressByAddressId(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取收货地址信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (address == null) {
            rsInfo.setMessage("收货地址信息不存在！");
            rsInfo.setData(new Address());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        if (address.getUserId() != Integer.parseInt(userId)) {
            rsInfo.setMessage("你不能修改别人的地址！");
            rsInfo.setCode("10001");
            return rsInfo;
        }


        //查询该用户之前的收货地址中是否已经存在了默认地址
        Address defaultAddress = addressService.getDefaultAddress(address.getUserId());
        //之前的收货地址中有默认的收货地址且不是现在这个收货地址，则修改默认状态
        if (null != defaultAddress && !defaultAddress.getAddressId().equals(addressId)) {
            defaultAddress.setIsDefault(2);
            addressService.modifyAddress(defaultAddress);
        }

        address.setIsDefault(1);

        int count;
        try {
            count = addressService.setDefaultAddress(address);
        } catch (Exception e) {
            e.printStackTrace();

            rsInfo.setMessage("设置默认地址失败！");
            rsInfo.setCode("15213");
            return rsInfo;
        }
        rsInfo.setData(count);
        rsInfo.setMessage("设置默认地址成功！");
        return rsInfo;
    }

    /**
     * 获取默认地址
     *
     * @param userId 用户ID
     * @return address
     */
    @RequestMapping(value = "/address/getDefaultAddress/{userId}")
    public @ResponseBody
    Object getDefaultAddress(@PathVariable("userId") Integer userId) {

        ResultInfo rsInfo = new ResultInfo();

        Address address;
        try {
            address = addressService.getDefaultAddress(userId);
            if (address == null) {
                rsInfo.setMessage("用户未设置默认地址！");
                rsInfo.setCode("15312");
                return rsInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取默认地址失败！");
            rsInfo.setCode("15214");
            return rsInfo;
        }
        rsInfo.setData(address);
        rsInfo.setMessage("获取默认地址成功！");
        return rsInfo;
    }

}
