package com.techwells.wumei.service;

import com.techwells.wumei.domain.Collect;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

public interface CollectService {

    // 增删改查
    public int addCollect(Collect collect);

    int delCollect(int collectId);

    public int modifyCollect(Collect collect);

    Collect getCollectByCollectId(int collectId);

    Collect getCollectByCollect(Collect collect);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<RsCollect> getCollectList(PagingTool pagingTool);

    // 批量删除
    int batchDelete(String[] ids, Integer userId);

    /**
     * 获取收藏id
     *
     * @param relationId  被收藏id
     * @param userId      用户id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return int
     */
    Integer getCollectId(String relationId, String userId, Integer collectType);

    /**
     * 取消收藏
     *
     * @param userId      收藏者id
     * @param relationId  被收藏id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return int
     */
    int deleteCollect(String userId, String relationId, String collectType);

	/**
	 * 查询被收藏者id列表
	 *
	 * @param userId      收藏者id
	 * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
	 * @return List
	 */
    List<Integer> getRelationIdList(String userId,Integer collectType);
}
