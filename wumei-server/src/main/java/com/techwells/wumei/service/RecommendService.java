package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Recommend;
import com.techwells.wumei.domain.rs.RsRecommend;
import com.techwells.wumei.util.PagingTool;

public interface RecommendService {

	// 增删改查
	public int addRecommend(Recommend recommend);

	public int delRecommend(int recommendId);

	public int modifyRecommend(Recommend recommend);

	Recommend getRecommendByRecommendId(int recommendId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsRecommend> getRecommendList(PagingTool pagingTool);

	/**
	 * 结束商品推荐状态
	 *
	 * @return int
	 */
	int updateEndRecommend();
	/**
	 * 开始商品推荐状态
	 *
	 * @return int
	 */
	int updateStartRecommend();

	// 批量删除
	int deleteBatch(String[] idArr);

	public int modifyRecommendStatus(String[] ids, boolean activated);


	int addRecommend(String startDate,String endDate,String[] productIds, Integer userId);

	/**
	 * 根据商品id数组批量删除
	 * @param productIdArray 商品id数组
	 * @return int
	 */
	int batchDelete(String[] productIdArray);
}
