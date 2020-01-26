package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.RsUser;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	
	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<User> selectUserList(PagingTool pagingTool);
	
	// 获取列表
	int countSuTotal(PagingTool pagingTool);

	List<User> selectSiteUserList(PagingTool pagingTool);

	RsUser selectUserByUserName(@Param("userName") String userName);
	
	User selectBySnsName(@Param("snsName") String snsName);
	
	User selectByMpId(@Param("mpId") String mpId);

}