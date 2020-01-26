package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.AuthorityMapper;
import com.techwells.wumei.domain.Authority;
import com.techwells.wumei.service.AuthorityService;
import com.techwells.wumei.util.PagingTool;


@Service("AuthorityService")
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityMapper authorityMapper;

	public AuthorityMapper getAuthorityMapper() {
		return authorityMapper;
	}

	@Autowired
	public void setAuthorityMapper(AuthorityMapper authorityMapper) {
		this.authorityMapper = authorityMapper;
	}

	@Override
	public int addAuthority(Authority authority) {
		int count = 0;
		try {
			count = authorityMapper.insertSelective(authority);
			if (count < 0) {
				throw new Exception("添加权限失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加权限异常！");
		}
		return count;
	}

	@Override
	public int delAuthority(int authorityId) {
		int count = 0;
		try {
			count = authorityMapper.deleteByPrimaryKey(authorityId);
			if (count < 0) {
				throw new Exception("删除权限失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除权限异常！");
		}
		return count;
	}

	@Override
	public int modifyAuthority(Authority authority) {
		int count = 0;
		try {
			count = authorityMapper.updateByPrimaryKeySelective(authority);
			if (count < 0) {
				throw new Exception("修改权限失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改权限异常！");
		}
		return count;
	}

	@Override
	public Authority getAuthorityByAuthorityId(int authorityId) {
		Authority authority = null;
		try {
			authority = authorityMapper.selectByPrimaryKey(authorityId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取权限详情异常！");
		}
		return authority;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = authorityMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取权限总数异常！");
		}
		return count;
	}

	@Override
	public List<Authority> getAuthorityList(PagingTool pagingTool) {
		List<Authority> authorityList = null;

		try {
			authorityList = authorityMapper.selectAuthorityList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取权限列表异常");
		}
		return authorityList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Authority> getAuthoritys() {
		List<Authority> authorityList = null;

		try {
			authorityList = authorityMapper.selectAuthoritys();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取权限列表异常");
		}
		return authorityList;
	}


}
