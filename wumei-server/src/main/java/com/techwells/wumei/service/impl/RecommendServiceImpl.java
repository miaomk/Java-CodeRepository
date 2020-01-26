package com.techwells.wumei.service.impl;

import java.util.Date;
import java.util.List;

import com.techwells.wumei.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.RecommendMapper;
import com.techwells.wumei.domain.Recommend;
import com.techwells.wumei.domain.rs.RsRecommend;
import com.techwells.wumei.service.RecommendService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("RecommendService")
public class RecommendServiceImpl implements RecommendService {
	@Resource
	private RecommendMapper recommendMapper;

	@Override
	public int addRecommend(Recommend recommend) {
		int count;
		try {
			count = recommendMapper.insertSelective(recommend);
			if (count < 0) {
				throw new Exception("添加推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加推荐异常！");
		}
		return count;
	}

	@Override
	public int delRecommend(int recommendId) {
		int count;
		try {
			count = recommendMapper.deleteByPrimaryKey(recommendId);
			if (count < 0) {
				throw new Exception("删除推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除推荐异常！");
		}
		return count;
	}

	@Override
	public int modifyRecommend(Recommend recommend) {
		int count = 0;
		try {
			count = recommendMapper.updateByPrimaryKeySelective(recommend);
			if (count < 0) {
				throw new Exception("修改推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改推荐异常！");
		}
		return count;
	}

	@Override
	public Recommend getRecommendByRecommendId(int recommendId) {
		Recommend recommend = null;
		try {
			recommend = recommendMapper.selectByPrimaryKey(recommendId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取推荐详情异常！");
		}
		return recommend;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = recommendMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取推荐总数异常！");
		}
		return count;
	}

	@Override
	public List<RsRecommend> getRecommendList(PagingTool pagingTool) {
		List<RsRecommend> recommendList;

		try {
			recommendList = recommendMapper.selectRecommendList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取推荐列表异常");
		}
		return recommendList;
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
    public int updateEndRecommend() {
		int count;

		try {
			count = recommendMapper.updateEndRecommend();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更新推荐商品结束状态异常！");
		}
		return count;
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateStartRecommend() {
		int count;

		try {
			count = recommendMapper.updateStartRecommend();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更新推荐商品开始状态异常！");
		}
		return count;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		int count;
		try {
			count = recommendMapper.batchDelete(idArr);
			if (count < 0) {
				throw new Exception("删除推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改推荐异常！");
		}
		return count;
	}

	@Override
	public int modifyRecommendStatus(String[] ids, boolean activated) {
		int count;
		try {
			count = recommendMapper.batchUpdateStatus(ids,activated);
			if (count < 0) {
				throw new Exception("修改推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改推荐异常！");
		}
		return count;
	}

	@Override
	public int addRecommend(String startDate,String endDate,String[] productIds, Integer userId) {
		int count = 0;
		try {


			Date startTime = DateUtil.getDateFromString(startDate);
			Date endTime = DateUtil.getDateFromString(endDate);

			for (String productId : productIds) {
				Recommend recommend = new Recommend();
				recommend.setProductId(Integer.parseInt(productId));
				recommend.setStartTime(startTime);
				recommend.setEndTime(endTime);
				recommend.setUserId(userId);
				recommend.setCreatedDate(new Date());
				count = recommendMapper.insertSelective(recommend);
			}
			
			if (count < 0) {
				throw new Exception("添加推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加推荐异常！");
		}
		return count;
	}

	@Override
	public int batchDelete(String[] productIdArray) {
		int count;
		try {
			count = recommendMapper.batchDeleteByProductId(productIdArray);
			if (count < 0) {
				throw new Exception("删除推荐失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改推荐异常！");
		}
		return count;
	}

}
