package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsMerchant;
import com.techwells.wumei.util.PagingTool;

public interface MerchantService {

	// 增删改查
	public int addMerchant(Merchant merchant);

	public int delMerchant(int merchantId);

	public int modifyMerchant(Merchant merchant);

	Merchant getMerchantByMerchantId(int merchantId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Merchant> getMerchantList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	/**
	 * 店铺详细信息
	 *
	 * @param merchantId 商户id
	 * @return RsMerchant
	 */
	RsMerchant getMerchantInfo(String merchantId);

	/**
	 * 商家中心
	 * @param merchantId 商户id
	 * @return RsMerchant
	 */
	RsMerchant getMerchantCenterInfo(Integer merchantId);

	/**
	 * 商户的我的账户信息
	 *
	 * @param merchantId 商户id
	 * @return MyAccountVO
	 */
	MyAccountVO getMerchantAccountInfo(Integer merchantId);

	/**
	 * 厂家推荐列表
	 *
	 * @param pagingTool 分页
	 * @return List
	 */
	List<Merchant> getRecommendMerchant(PagingTool pagingTool);
}
