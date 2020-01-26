package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.ActivityOrderMapper;
import com.techwells.wumei.dao.UserMapper;
import com.techwells.wumei.dao.VerificationCodeMapper;
import com.techwells.wumei.dao.WithdrawalRecordMapper;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsUser;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;


	@Resource
	private VerificationCodeMapper verificationCodeMapper;
	@Resource
	private ActivityOrderMapper activityOrderMapper;
	@Resource
	private WithdrawalRecordMapper withdrawalRecordMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	@Resource
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addUser(User user) {
		int count;
		try {
			count = userMapper.insertSelective(user);
			if (count < 0) {
				throw new Exception("添加用户失败!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加用户异常！");
		}
		return count;
	}

	@Override
	public int delUser(int userId) {
		int count;
		try {
			count = userMapper.deleteByPrimaryKey(userId);
			if (count < 0) {
				throw new Exception("删除用户失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除用户异常！");
		}
		return count;
	}

	@Override
	public int modifyUser(User user) {
		int count;
		try {
			count = userMapper.updateByPrimaryKeySelective(user);
			if (count < 0) {
				throw new Exception("修改用户失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改用户异常！");
		}
		return count;
	}

	@Override
	public User getUserByUserId(int userId) {
		User user;
		try {
			user = userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取用户详情异常！");
		}
		return user;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count;

		try {
			count = userMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取用户总数异常！");
		}
		return count;
	}

	@Override
	public List<User> getUserList(PagingTool pagingTool) {
		List<User> userList;

		try {
			userList = userMapper.selectUserList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取用户列表异常");
		}
		return userList;
	}

	// 获取列表
		@Override
		public int countSuTotal(PagingTool pagingTool) {
			int count;

			try {
				count = userMapper.countSuTotal(pagingTool);

			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("获取用户总数异常！");
			}
			return count;
		}

		@Override
		public List<User> getSiteUserList(PagingTool pagingTool) {
			List<User> userList = null;

			try {
				userList = userMapper.selectSiteUserList(pagingTool);

			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationContextException("获取用户列表异常");
			}
			return userList;
		}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VerificationCode getVercodeByUserName(String userName) {
		VerificationCode verCode = null;
		verCode = verificationCodeMapper.selectByPrimaryKey(userName);
		return verCode;
	}

	@Override
	public int modifyVerCodeByUserName(VerificationCode vc) {
		int count = 0;
		count = verificationCodeMapper.updateByPrimaryKeySelective(vc);
		return count;
	}

	@Override
	public int addVerCode(VerificationCode vc) {
		int count = 0;
		count = verificationCodeMapper.insertSelective(vc);
		return count;
	}

	@Override
	public RsUser getUserByUsername(String userName) {
		RsUser user = null;
		user = userMapper.selectUserByUserName(userName);
		return user;
	}

	@Override
	public User getUserBySnsName(String snsName) {
		User user;
		try {
			user = userMapper.selectBySnsName(snsName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("通过用户名得到用户错误");
		}
		return user;
	}

	@Override
	public User getUserByMpId(String mpId) {
		User user;
		try {
			user = userMapper.selectByMpId(mpId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("通过openId得到用户错误");
		}

		return user;
	}

	@Override
	public MyAccountVO  getAccountInfo(String userId, Integer companyId) {
		MyAccountVO myAccountVO = new MyAccountVO();

		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal balance;
		BigDecimal withdrawalCount;
		List<BillVO> billList = new ArrayList<>();
		try {

			if (null == companyId) {
				//走查询技术人员账户信息接口


			} else {
				//走查询公司账户信息接口

				//查询售活动票所得
				BigDecimal ticketIncome = activityOrderMapper.getTicketIncome(companyId);
				List<BillVO> ticketIncomeList = activityOrderMapper.getTicketIncomeList(companyId);
				ticketIncomeList.forEach(data->{
					data.setBillType(2);
					billList.add(data);
				});

				totalIncome = ticketIncome;
			}
			//查询提现的钱
			withdrawalCount = withdrawalRecordMapper.getWithdrawalCount(userId,1);
			if (withdrawalCount == null) {
				withdrawalCount = BigDecimal.ZERO;
			}
			List<BillVO> withdrawalList = withdrawalRecordMapper.getWithdrawalList(userId,1);
			for (BillVO billVO : withdrawalList) {
				billVO.setBillType(1);

				billList.add(billVO);
			}

			//剩下的钱
			balance = totalIncome.subtract(withdrawalCount);

			//
			if (totalIncome.compareTo(BigDecimal.ZERO) < 1) {

				totalIncome = BigDecimal.ZERO;
			}
			if (balance.compareTo(BigDecimal.ZERO) < 1) {

				balance = BigDecimal.ZERO;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("通过用户名得到用户错误");
		}

		myAccountVO.setUserId(Integer.parseInt(userId));
		myAccountVO.setTotalIncome(totalIncome);
		myAccountVO.setBalance(balance);
		myAccountVO.setWithdrawalCount(withdrawalCount);
		myAccountVO.setBillList(billList);

		return myAccountVO;
	}

}
