package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Collect;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    int deleteByPrimaryKey(Integer collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    // 获取列表
    int countTotal(PagingTool pagingTool);

    List<RsCollect> selectCollectList(PagingTool pagingTool);

    Collect selectByCollect(Collect collect);

    int batchDelete(@Param("ids") String[] ids, @Param("userId") Integer userId);

    /**
     * 查询收藏人数
     *
     * @param relationId  被收藏的id
     * @param collectType 收藏类型
     * @return int
     */
    int selectCollectCount(@Param("relationId") String relationId, @Param("collectType") int collectType);

    /**
     * 获取收藏id
     *
     * @param relationId  被收藏id
     * @param userId      用户id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return int
     */
    Integer getCollectId(@Param("relationId") String relationId,
                         @Param("userId") String userId,
                         @Param("collectType") Integer collectType);

    /**
     * 取消收藏
     *
     * @param userId      收藏者id
     * @param relationId  被收藏id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return int
     */
    int deleteCollect(@Param("userId") String userId,
                      @Param("relationId") String relationId,
                      @Param("collectType") String collectType);

    /**
     * 查询被收藏者id列表
     *
     * @param userId      收藏者id
     * @param collectType 收藏类型  1 收藏商品 2 收藏活动 3 收藏租赁商品
     * @return List
     */
    List<Integer> getRelationIdList(@Param("userId") String userId,
                                    @Param("collectType") Integer collectType);
}