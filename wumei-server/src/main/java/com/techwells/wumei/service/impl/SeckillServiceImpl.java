package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.SeckillMapper;
import com.techwells.wumei.domain.Seckill;
import com.techwells.wumei.domain.rs.RsSeckill;
import com.techwells.wumei.service.SeckillService;
import com.techwells.wumei.util.PagingTool;


@Service("SeckillService")
public class SeckillServiceImpl implements SeckillService {

	private SeckillMapper seckillMapper;

	public SeckillMapper getSeckillMapper() {
		return seckillMapper;
	}

	@Autowired
	public void setSeckillMapper(SeckillMapper seckillMapper) {
		this.seckillMapper = seckillMapper;
	}

	@Override
	public int addSeckill(Seckill seckill) {
		int count = 0;
		try {
			count = seckillMapper.insertSelective(seckill);
			if (count < 0) {
				throw new Exception("添加抢购失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加抢购异常！");
		}
		return count;
	}

	@Override
	public int delSeckill(int seckillId) {
		int count = 0;
		try {
			count = seckillMapper.deleteByPrimaryKey(seckillId);
			if (count < 0) {
				throw new Exception("删除抢购失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除抢购异常！");
		}
		return count;
	}

	@Override
	public int modifySeckill(Seckill seckill) {
		int count = 0;
		try {
			count = seckillMapper.updateByPrimaryKeySelective(seckill);
			if (count < 0) {
				throw new Exception("修改抢购失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改抢购异常！");
		}
		return count;
	}

	@Override
	public Seckill getSeckillBySeckillId(int seckillId) {
		Seckill seckill = null;
		try {
			seckill = seckillMapper.selectByPrimaryKey(seckillId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取抢购详情异常！");
		}
		return seckill;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = seckillMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取抢购总数异常！");
		}
		return count;
	}

	@Override
	public List<RsSeckill> getSeckillList(PagingTool pagingTool) {
		List<RsSeckill> seckillList = null;

		try {
			seckillList = seckillMapper.selectSeckillList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取抢购列表异常");
		}
		return seckillList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RsSeckill> getAll() {
		List<RsSeckill> seckillList = null;

		try {
			seckillList = seckillMapper.selectAll();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取抢购列表异常");
		}
		return seckillList;
	}

}
