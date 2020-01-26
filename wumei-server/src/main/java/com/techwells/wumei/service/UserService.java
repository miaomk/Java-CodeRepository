package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsUser;
import com.techwells.wumei.util.PagingTool;

public interface UserService {

	// 增删改查
	public int addUser(User user);

	public int delUser(int userId);

	public int modifyUser(User user);

	User getUserByUserId(int userId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<User> getUserList(PagingTool pagingTool);
	
	// 获取列表
		int countSuTotal(PagingTool pagingTool);

		List<User> getSiteUserList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);

	// 根据账号获取验证码记录
	VerificationCode getVercodeByUserName(String userName);

	int modifyVerCodeByUserName(VerificationCode vc);

	int addVerCode(VerificationCode vc);

	RsUser getUserByUsername(String userName);
	
	
	User getUserBySnsName(String snsName);
	
	User getUserByMpId(String mpId);

	/**
	 * 查询我的账户信息
	 *
	 * @param userId    用户id
	 * @param companyId 公司id
	 * @return MyAccountVO
	 */
	MyAccountVO getAccountInfo(String userId, Integer companyId);
}
