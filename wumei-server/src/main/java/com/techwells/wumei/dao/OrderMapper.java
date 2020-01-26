package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Order;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.domain.rs.RsOrder;
import com.techwells.wumei.domain.rs.RsOrderInfo;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<RsOrder> selectOrderList(PagingTool pagingTool);

    /**
     * 批量删除订单
     * @param ids
     * @return
     */
 	int batchDelete(@Param("ids")String[] ids);

    /**
     * 批量 关闭订单，订单发货
     * @param ids 订单id
     * @param orderType 操作类型
     * @return int
     */
 	int batchOperationOrder(@Param("ids")String[] ids,@Param("orderType") String orderType);

    RsOrder selectOrderByOrderId(Integer orderId);

    int getOrderCount(PagingTool pagingTool);

    /**
     * 备注订单
     * @param orderId 订单ID
     * @param description 备注信息
     * @return int
     */
    int updateDescription(@Param("orderId")String orderId,@Param("description") String description);
    /**
     * 查询售出商品数
     *
     * @param productId 商品id
     * @return int
     */
    int countSale(@Param("productId") Integer productId);

    /**
     * 查询商户下订单数量
     * @param merchantId 商户id
     * @param orderStatus 订单状态 1表示待付款，2表示待发货，3表示待收货，4待评价，5表示交易完成
     * @return int
     */
    int countOrder(@Param("merchantId")Integer merchantId,@Param("orderStatus") Integer orderStatus);

    /**
     * 查询已完成订单列表
     *
     * @param merchantId 商户id
     * @return List
     */
    List<BillVO> merchantOrderList(@Param("merchantId") Integer merchantId);

    /**
     * 商家订单详情
     * @param orderId 订单id
     * @return RsOrderInfo
     */
    RsOrderInfo selectMerchantOrderInfo(@Param("orderId") Integer orderId);
}