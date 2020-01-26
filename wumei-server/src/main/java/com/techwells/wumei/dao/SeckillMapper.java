package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Seckill;
import com.techwells.wumei.domain.rs.RsSeckill;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface SeckillMapper {
    int deleteByPrimaryKey(Integer seckillId);

    int insert(Seckill record);

    int insertSelective(Seckill record);

    Seckill selectByPrimaryKey(Integer seckillId);

    int updateByPrimaryKeySelective(Seckill record);

    int updateByPrimaryKey(Seckill record);
    
 // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<RsSeckill> selectSeckillList(PagingTool pagingTool);
  	
  	int batchDelete(@Param("ids")String[] ids);

	List<RsSeckill> selectAll();

}