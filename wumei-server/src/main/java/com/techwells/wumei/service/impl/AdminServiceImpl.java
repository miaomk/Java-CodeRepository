package com.techwells.wumei.service.impl;

import java.util.List;

import com.techwells.wumei.domain.vo.AdminVO;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.AdminMapper;
import com.techwells.wumei.domain.Admin;
import com.techwells.wumei.service.AdminService;
import com.techwells.wumei.util.PagingTool;

import javax.annotation.Resource;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {
	@Resource
	private AdminMapper adminMapper;

	@Override
	public int addAdmin(Admin admin) {
		int count;
		try {
			count = adminMapper.insertSelective(admin);
			if (count < 0) {
				throw new Exception("添加管理员失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加管理员异常！");
		}
		return count;
	}

	@Override
	public int delAdmin(int adminId) {
		int count = 0;
		try {
			count = adminMapper.deleteByPrimaryKey(adminId);
			if (count < 0) {
				throw new Exception("删除管理员失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除管理员异常！");
		}
		return count;
	}

	@Override
	public int modifyAdmin(Admin admin) {
		int count = 0;
		try {
			count = adminMapper.updateByPrimaryKeySelective(admin);
			if (count < 0) {
				throw new Exception("修改管理员失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改管理员异常！");
		}
		return count;
	}

	@Override
	public Admin getAdminByAdminId(int adminId) {
		Admin admin = null;
		try {
			admin = adminMapper.selectByPrimaryKey(adminId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取管理员详情异常！");
		}
		return admin;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = adminMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取管理员总数异常！");
		}
		return count;
	}

	@Override
	public List<Admin> getAdminList(PagingTool pagingTool) {
		List<Admin> adminList = null;

		try {
			adminList = adminMapper.selectAdminList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取管理员列表异常");
		}
		return adminList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public AdminVO getAdminByAdminName(String adminName) {
		AdminVO admin;
		try {
			admin = adminMapper.selectByAdminName(adminName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取管理员详情异常！");
		}
		return admin;
	}

}
