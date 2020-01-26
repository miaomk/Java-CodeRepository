package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Image;
import com.techwells.wumei.util.PagingTool;

public interface ImageService {

	// 增删改查
	public int addImage(Image image);

	public int delImage(int imageId);

	public int modifyImage(Image image);

	Image getImageByImageId(int imageId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Image> getImageList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
	
	int modifyToDefault(Image image);
	
	
}
