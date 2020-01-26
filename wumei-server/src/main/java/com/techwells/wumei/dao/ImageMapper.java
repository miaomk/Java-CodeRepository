package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Image;
import com.techwells.wumei.util.PagingTool;

public interface ImageMapper {
    int deleteByPrimaryKey(Integer imageId);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer imageId);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
    // 获取列表
  	int countTotal(PagingTool pagingTool);

  	List<Image> selectImageList(PagingTool pagingTool);
  	
  	int setDefault(int userId);
  	
}