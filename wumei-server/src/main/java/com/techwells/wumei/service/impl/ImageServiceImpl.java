package com.techwells.wumei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ImageMapper;
import com.techwells.wumei.dao.UserMapper;
import com.techwells.wumei.domain.Image;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.service.ImageService;
import com.techwells.wumei.util.PagingTool;


@Service("ImageService")
public class ImageServiceImpl implements ImageService {

	private ImageMapper imageMapper;
	
	@Resource
	private UserMapper userMapper;

	public ImageMapper getImageMapper() {
		return imageMapper;
	}

	@Resource
	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}

	@Override
	public int addImage(Image image) {
		int count = 0;
		try {
			count = imageMapper.insertSelective(image);
			if (count < 0) {
				throw new Exception("添加图片失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加图片异常！");
		}
		return count;
	}

	@Override
	public int delImage(int imageId) {
		int count = 0;
		try {
			count = imageMapper.deleteByPrimaryKey(imageId);
			if (count < 0) {
				throw new Exception("删除图片失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除图片异常！");
		}
		return count;
	}

	@Override
	public int modifyImage(Image image) {
		int count = 0;
		try {
			count = imageMapper.updateByPrimaryKeySelective(image);
			if (count < 0) {
				throw new Exception("修改图片失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改图片异常！");
		}
		return count;
	}

	@Override
	public Image getImageByImageId(int imageId) {
		Image image = null;
		try {
			image = imageMapper.selectByPrimaryKey(imageId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取图片详情异常！");
		}
		return image;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = imageMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取图片总数异常！");
		}
		return count;
	}

	@Override
	public List<Image> getImageList(PagingTool pagingTool) {
		List<Image> imageList = null;

		try {
			imageList = imageMapper.selectImageList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取图片列表异常");
		}
		return imageList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public int modifyToDefault(Image image) {
		int count = 0;
		try {
			
			count = imageMapper.setDefault(image.getUserId());			
			count = imageMapper.updateByPrimaryKeySelective(image);
			
			User user = new User();
			user.setUserId(image.getUserId());
			user.setUserIcon(image.getImageUrl());
			userMapper.updateByPrimaryKeySelective(user);
			if (count < 0) {
				throw new Exception("设为默认头像失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("设为默认头像失败！");
		}
		return count;
	}

}
