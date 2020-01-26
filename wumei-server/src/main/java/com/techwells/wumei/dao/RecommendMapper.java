package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Recommend;
import com.techwells.wumei.domain.rs.RsRecommend;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface RecommendMapper {
    int deleteByPrimaryKey(Integer recommendId);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    Recommend selectByPrimaryKey(Integer recommendId);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<RsRecommend> selectRecommendList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);

	int batchUpdateStatus(@Param("ids")String[] ids, @Param("activated")boolean activated);

    /**
     * 通过商品id数组批量删除推荐
     *
     * @param ids 商品id数组
     * @return int
     */
	int batchDeleteByProductId(@Param("productIds")String[] ids);

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
}