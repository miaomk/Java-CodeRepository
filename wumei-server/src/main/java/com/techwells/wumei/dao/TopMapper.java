package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Top;
import com.techwells.wumei.domain.rs.RsTop;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface TopMapper {
    int deleteByPrimaryKey(Integer topId);

    int insert(Top record);

    int insertSelective(Top record);

    Top selectByPrimaryKey(Integer topId);

    int updateByPrimaryKeySelective(Top record);

    int updateByPrimaryKey(Top record);
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<RsTop> selectTopList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);

    /***
     * 批量插入榜单
     * @param topList
     * @return
     */
  	int batchInsertTop(List<Top> topList);

    /**
     * 批量更新榜单推荐状态
     * @param idArrays
     * @param recommendStatus
     * @return
     */
    int batchUpdateStatus(@Param("array") String[] idArrays,@Param("status") String recommendStatus);
}