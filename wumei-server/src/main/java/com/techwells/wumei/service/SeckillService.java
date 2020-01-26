package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Seckill;
import com.techwells.wumei.domain.rs.RsSeckill;
import com.techwells.wumei.util.PagingTool;

public interface SeckillService {

	// 增删改查
	public int addSeckill(Seckill seckill);

	public int delSeckill(int seckillId);

	public int modifySeckill(Seckill seckill);

	Seckill getSeckillBySeckillId(int seckillId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<RsSeckill> getSeckillList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	public List<RsSeckill> getAll();
}
