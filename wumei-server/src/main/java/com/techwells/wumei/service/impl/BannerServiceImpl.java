package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.BannerMapper;
import com.techwells.wumei.domain.Banner;
import com.techwells.wumei.service.BannerService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * 轮播图接口实现类
 *
 * @author miao
 */
@Service("BannerService")
public class BannerServiceImpl implements BannerService {
	@Resource
	private BannerMapper bannerMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addBanner(Banner banner) {
		int count;
		try {
			count = bannerMapper.insertSelective(banner);
			if (count < 0) {
				throw new Exception("添加轮播图失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加轮播图异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delBanner(int bannerId) {
		int count;
		try {
			count = bannerMapper.deleteByPrimaryKey(bannerId);
			if (count < 0) {
				throw new Exception("删除轮播图失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除轮播图异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyBanner(Banner banner) {
		int count;
		try {
			count = bannerMapper.updateByPrimaryKeySelective(banner);
			if (count < 0) {
				throw new Exception("修改轮播图失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改轮播图异常！");
		}
		return count;
	}

	@Override
	public Banner getBannerByBannerId(int bannerId) {
		Banner banner;
		try {
			banner = bannerMapper.selectByPrimaryKey(bannerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取轮播图详情异常！");
		}
		return banner;
	}


	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = bannerMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取轮播图总数异常！");
		}
		return count;
	}

	@Override
	public List<Banner> getBannerList(PagingTool pagingTool) {
		List<Banner> bannerList;

		try {
			bannerList = bannerMapper.selectBannerList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取轮播图列表异常");
		}
		return bannerList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteBatch(String[] idArr) {
		int count;
		try {
			count = bannerMapper.batchDelete(idArr);
			if (count < 0) {
				throw new Exception("批量操作失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("批量操作异常！");
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateActivatedStatus(int bannerId,String status) {
		int count;
		try {
			count = bannerMapper.updateActivatedStatus(bannerId,status);
			if (count < 0) {
				throw new Exception("修改上下线状态失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改上下线状态异常！");
		}
		return count;
	}



}
