package com.techwells.wumei.controller;

import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.VerificationCode;
import com.techwells.wumei.domain.rs.MyAccountVO;
import com.techwells.wumei.domain.rs.RsUser;
import com.techwells.wumei.service.CollectService;
import com.techwells.wumei.service.FocusService;
import com.techwells.wumei.service.ReceiveService;
import com.techwells.wumei.service.UserService;
import com.techwells.wumei.util.*;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
//@RequestMapping(value = "*.do")
public class UserController {
	@Resource
	private UserService userService;

	@Resource
	private CollectService collectService;

	@Resource
	private ReceiveService receiveService;

	@Resource
	private FocusService focusService;


	/**
	 * 添加用户
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/user/addUser" )
	public @ResponseBody Object addUser(HttpServletRequest request,
										@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");

		if (userName == null || "".equals(userName)) {
			rsInfo.setMessage("用户名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (StringUtil.isEmpty(mobile)) {
			rsInfo.setMessage("手机号不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		User user = new User();

		// 处理图片
		if (files != null && files.length > 0) {

			String userIcon = UploadImageUtil.uploadImage("user", files);
			if (!StringUtil.isEmpty(userIcon)) {

				user.setUserIcon(userIcon);
			}
		}

		user.setCreatedDate(LocalDateTime.now());
		int count;
		try {
			count = userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加用户异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加用户成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加用户失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改用户信息
	 *
	 * @param request 请求
	 * @param files   头像图片
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/user/modifyUser")
	public @ResponseBody Object modifyUser(HttpServletRequest request,
										   @RequestParam(value = "userIcon", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();
		//用户ID
		String userId = request.getParameter("userId");
		//昵称
		String nickName = request.getParameter("nickName");
		//性别
		String gender = request.getParameter("gender");
		//年龄
		String age = request.getParameter("age");
		//所在地
		String cityCode = request.getParameter("cityCode");
		//个人简介
		String personalProfile = request.getParameter("personalProfile");
		//生日
		String birthday = request.getParameter("birthday");
		//头像
		String avatarUrl = request.getParameter("avatarUrl");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		if (userId == null || "".equals(userId)) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (nickName == null || "".equals(nickName)) {
			rsInfo.setMessage("用户昵称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		User user = new User();

		user.setUserId(Integer.parseInt(userId));
		user.setNickName(nickName);

		if (gender != null && !"".equals(gender)) {
			user.setGender(gender);
		}
		if (age != null && !"".equals(age)) {
			user.setAge(Integer.parseInt(age));
		}
		if (cityCode != null && !"".equals(cityCode)) {
			user.setCityCode(cityCode);
		}
		if (personalProfile != null && !"".equals(personalProfile)) {
			user.setPersonalProfile(personalProfile);
		}

		if (latitude != null && !"".equals(latitude)) {
			user.setLatitude(Double.parseDouble(latitude));
		}
		if (avatarUrl != null && !"".equals(avatarUrl)) {
			user.setUserIcon(avatarUrl);
		}
		
		if (longitude != null && !"".equals(longitude)) {
			user.setLatitude(Double.parseDouble(longitude));
		}
		
		if (birthday != null && !"".equals(birthday)) {
			user.setBirthday(new Date(Long.parseLong(birthday)));
		}	

		// 处理图片
		if (files != null && files.length > 0) {

			String userIcon = UploadImageUtil.uploadImage("user", files);
			if (!StringUtil.isEmpty(userIcon)) {

				user.setUserIcon(userIcon);
			}
		}
		user.setUserId(Integer.parseInt(userId));
		user.setCreatedDate(LocalDateTime.now());
		int count;
		try {
			count = userService.modifyUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改用户异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改用户失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改用户成功！");
		rsInfo.setData(user);
		return rsInfo;
	}

	/**
	 * Description: 删除用户
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/deleteUser")
	public @ResponseBody Object deleteUser(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = userService.delUser(Integer.parseInt(userId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除用户异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除用户成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除用户失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 查看用户详情
	 * @param userId 用户id
	 * @return ResultInfo
	 */
	@GetMapping(value = "/user/getUserById/{userId}")
	public @ResponseBody Object getUserById(@PathVariable("userId") Integer userId) {
		ResultInfo rsInfo = new ResultInfo();


		if (userId == null) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		User user;
		try {
			user = userService.getUserByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取用户信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (user == null) {
			rsInfo.setMessage("用户不存在！");
			rsInfo.setData(new User());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(user);
		rsInfo.setMessage("获取用户成功！");
		return rsInfo;
	}
	
	@GetMapping("/findUser/{userId}")
	public Object findStudentRestful(@PathVariable("userId") Integer userId) {
	
		ResultInfo rsInfo = new ResultInfo();
		User user = null;
		try {
			user = userService.getUserByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取用户信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (user == null) {
			rsInfo.setMessage("用户信息不存在！");
			rsInfo.setData(new User());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(user);
		rsInfo.setMessage("获取用户成功！");
		return rsInfo;
		
		
	}

	/**
	 * @description 获取用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/user/getUserList")
	public @ResponseBody Object getUserList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String nickName = request.getParameter("nickName");
		String mobile = request.getParameter("mobile");
		
		String userId = request.getParameter("userId");
		System.out.println(userId);

		if (pageNum == null || "".equals(pageNum)) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || "".equals(pageSize)) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (nickName != null && !nickName.equals("")) {
			params.put("nickName", nickName);
		}
		
		if (mobile != null && !mobile.equals("")) {
			params.put("mobile", mobile);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = userService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("用户总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<User> userList;
		try {
			userList = userService.getUserList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (userList.size() == 0) {
			rsInfo.setMessage("用户列表为空！");
			rsInfo.setData(new ArrayList<User>());
			return rsInfo;
		}
		rsInfo.setData(userList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取用户列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/deleteUserBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = userService.deleteBatch(idArr);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("批量删除异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (count > 0) {
			rsInfo.setMessage("批量删除成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("批量删除失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 获取验证码
	 * 
	 * @param mobile
	 */
	@RequestMapping("/user/getVerCode")
	@ResponseBody
	public Object getVerCode(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();
		//String userName = request.getParameter("userName");
		String userName = request.getParameter("phone");
		if (userName == null || "".equals(userName)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("账号不能为空！");
			return rsInfo;
		}

		// 检测验证码是否存在
		VerificationCode verCode;
		verCode = userService.getVercodeByUserName(userName);
		if (verCode != null) {
			// 判断时间是否过期
			if ((Duration.between(verCode.getUpdatedDate(), LocalDateTime.now())).toMinutes() < 10) {
				// 获取次数频繁
				rsInfo.setCode("10002");
				rsInfo.setMessage("对不起，您获取验证码的频率太高。");
				return rsInfo;
			} else {
				// 重新生成验证码并发送到用户手机/邮箱
				String vCode = SendSmsUtil.getRandom();
				try {
					
						// 发送手机验证码
					SendSmsUtil.sendUserCrCode(userName, vCode);
					
					// 验证码入库
					VerificationCode vc = new VerificationCode();
					Date time = new Date();
					int count;
					vc.setUserName(userName);
					vc.setVerCode(vCode);

					count = userService.modifyVerCodeByUserName(vc);
					if (count != 1) {
						rsInfo.setCode("10001");
						rsInfo.setMessage("获取验证码异常，请联系客服人员。");
						return rsInfo;
					}
				} catch (Exception e) {
					e.printStackTrace();
					rsInfo.setCode("10001");
					rsInfo.setMessage("手机号码/邮箱有误。");
					return rsInfo;
				}
			}
		} else {
			// 重新生成验证码并发送到用户手机
			String vCode = SendSmsUtil.getRandom();
			try {
				
				// 发送手机验证码
				SendSmsUtil.sendUserCrCode(userName, vCode);
				
				// 验证码入库
				VerificationCode vc = new VerificationCode();
				Date time = new Date();
				vc.setUserName(userName);
				vc.setVerCode(vCode);
				userService.addVerCode(vc);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setCode("10001");
				rsInfo.setMessage("手机号码/邮箱有误。");
				return rsInfo;
			}

			rsInfo.setData(vCode);
		}

		rsInfo.setMessage("获取验证码成功！");
		return rsInfo;
	}
	
	/**
	 * 验证码登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/acLogin")
	public @ResponseBody Object acLogin(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userName = request.getParameter("userName"); // 用户名
		String verCode = request.getParameter("verCode"); // 验证码

		if (verCode == null || verCode.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("验证码不能为空");
			return rsInfo;
		}
		if (userName == null || userName.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if(!RegexUtil.isMobileExact(userName)) {
			rsInfo.setCode("9999");
			rsInfo.setMessage("手机号格式不正确");
			return rsInfo;	
	}

		// 验证码校验
		VerificationCode verificationCode = null;
		verificationCode = userService.getVercodeByUserName(userName);
		if (verificationCode != null) {
			
			if(verificationCode.getCodeType() != 1) {
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码不是登录用验证码，请重新获取。");
				return rsInfo;
			}

			// 判断时间是否过期
			if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
				// 超过10分钟， 验证码过期
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码过期。");
				return rsInfo;

			} else {
				
				// 验证码校验
				if (verCode.equals(verificationCode.getVerCode())) {
					// 校验成功，开始注册
					// 判断账号是否已经注册
					RsUser user = null;
					try {
						user = userService.getUserByUsername(userName);
					} catch (Exception e) {
						e.printStackTrace();
						rsInfo.setCode("10001");
						rsInfo.setMessage("获取用户信息出错");
						return rsInfo;
					}

					if (user != null) {
						
						rsInfo.setMessage("登录成功！");
						rsInfo.setData(user);
						return rsInfo;
						
					} else {
						rsInfo.setCode("10001");
						rsInfo.setMessage("用户未注册，请先注册");
						return rsInfo;
						
					}
					

				} else {
					// 校验失败
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码错误。");
					return rsInfo;
				}
			}
		} else {
			// 验证码不存在，重新获取
			rsInfo.setCode("10001");
			rsInfo.setMessage("验证码不存在。");
			return rsInfo;
		}

	}

	/**
	 * 手机注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/register")
	public @ResponseBody Object register(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userName = request.getParameter("userName"); // 用户名
		String verCode = request.getParameter("verCode"); // 验证码
		String password = request.getParameter("password"); // 密码
		String confPassword = request.getParameter("confPassword"); // 确认密码

		if (verCode == null || "".equals(verCode)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("验证码不能为空");
			return rsInfo;
		}
		if (StringUtil.isEmpty(userName)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if (StringUtil.isEmpty(password)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("密码不能为空");
			return rsInfo;
		}
		
		if (confPassword == null || !confPassword.equals(password)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("两次输入密码不一致");
			return rsInfo;
		}

		// 验证码校验
		VerificationCode verificationCode = null;
		verificationCode = userService.getVercodeByUserName(userName);
		if (verificationCode != null) {
			// 判断时间是否过期
			if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
				// 超过10分钟， 验证码过期
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码过期。");
				return rsInfo;
			} else {
				// 验证码校验
				if (verCode.equals(verificationCode.getVerCode())) {
					// 校验成功，开始注册
					// 判断账号是否已经注册
					User user = null;
					try {
						user = userService.getUserByUsername(userName);
					} catch (Exception e) {
						e.printStackTrace();
						rsInfo.setCode("10001");
						rsInfo.setMessage("用户名不正确");
						return rsInfo;
					}

					if (user != null) {
						rsInfo.setCode("10001");
						rsInfo.setMessage("账号已被注册");
						return rsInfo;
					} else {
						int count = 0;
						user = new User();
						user.setPassword(password);
						user.setUserName(userName);
											
//						EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
//
//					    RegisterUsers users = new RegisterUsers();
//					    io.swagger.client.model.User hxUser = new io.swagger.client.model.User().username(userName).password(password);
//					    users.add(hxUser);
//					    Object result = easemobIMUsers.createNewIMUserSingle(hxUser);
						
						//EasemobIMUsers.registerHwUser(userName, password);
						
						//user.setHxUsername(userName);
						//user.setHxPassword(password);		

						user.setCreatedDate(LocalDateTime.now());
						try {
							count = userService.addUser(user);
						} catch (Exception e) {
							e.printStackTrace();
							rsInfo.setCode("10002");
							rsInfo.setMessage("注册失败");
							return rsInfo;
						}
						if (count != 1) {
							rsInfo.setCode("10002");
							rsInfo.setMessage("注册失败");
							return rsInfo;
						}
					}
					rsInfo.setMessage("注册成功！");
					rsInfo.setData(user);
					return rsInfo;

				} else {
					// 校验失败
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码错误。");
					return rsInfo;
				}
			}
		} else {
			// 验证码不存在，重新获取
			rsInfo.setCode("10001");
			rsInfo.setMessage("验证码不存在。");
			return rsInfo;
		}

	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public Object login(HttpServletRequest request, HttpSession session) {
		ResultInfo rsInfo = new ResultInfo();

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (userName == null || "".equals(userName)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if (password == null || "".equals(password)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("密码不能为空");
			return rsInfo;
		}
		

		RsUser user = userService.getUserByUsername(userName);
		if (user != null) {
			if (!password.equals(user.getPassword())) {
				rsInfo.setCode("10001");
				rsInfo.setMessage("密码错误");
				return rsInfo;
			}
		} else {
			rsInfo.setCode("10001");
			rsInfo.setMessage("账号不存在！");
			return rsInfo;
		}
		rsInfo.setMessage("登录成功");
		rsInfo.setData(user);
		return rsInfo;
	}

	/**
	 * 忘记密码
	 */
	@RequestMapping("/user/forgetPassword")
	@ResponseBody
	public Object forgetPassword(HttpServletRequest request, HttpSession session) {
		ResultInfo rsInfo = new ResultInfo();
		String userName = request.getParameter("userName");
		String newPassword = request.getParameter("newPassword");
		String confPassword = request.getParameter("confPassword"); // 确认密码
		String verCode = request.getParameter("verCode"); // 验证码
		if (userName == null || userName.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if (newPassword == null || newPassword.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("新密码不能为空");
			return rsInfo;
		}
		
		if (confPassword == null || !confPassword.equals(newPassword)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("两次输入密码不一致");
			return rsInfo;
		}

		// 验证码校验
		VerificationCode verificationCode = null;
		verificationCode = userService.getVercodeByUserName(userName);
		if (verificationCode != null) {
			// 判断时间是否过期
			if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
				// 超过10分钟， 验证码过期
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码过期。");
				return rsInfo;
			} else {
				// 验证码校验
				if (verCode.equals(verificationCode.getVerCode())) {
					// 校验成功
					// 判断账号是否已经注册
					User user = null;
					try {
						user = userService.getUserByUsername(userName);
					} catch (Exception e) {
						e.printStackTrace();
						rsInfo.setCode("10001");
						rsInfo.setMessage("用户名不正确");
						return rsInfo;
					}

					if (user == null) {
						rsInfo.setCode("10001");
						rsInfo.setMessage("账号不存在");
						return rsInfo;
					} else {
						int count = 0;
						User u = new User();
						u.setUserId(user.getUserId());
						u.setPassword(newPassword);

						try {
							count = userService.modifyUser(u);
						} catch (Exception e) {
							e.printStackTrace();
							rsInfo.setCode("10002");
							rsInfo.setMessage("注册失败");
							return rsInfo;
						}
						if (count != 1) {
							rsInfo.setCode("10002");
							rsInfo.setMessage("注册失败");
							return rsInfo;
						}

						rsInfo.setMessage("修改成功");
						rsInfo.setData(u);
						return rsInfo;
					}

				} else {
					// 校验失败
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码错误。");
					return rsInfo;
				}
			}
		} else {
			// 验证码不存在，重新获取
			rsInfo.setCode("10001");
			rsInfo.setMessage("验证码不存在。");
			return rsInfo;
		}

	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/user/modifyPassword")
	@ResponseBody
	public Object modifyPassword(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();
		String userName = request.getParameter("userName");
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		if (userName == null || "".equals(userName)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if (newPassword == null || newPassword.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("新密码不能为空");
			return rsInfo;
		}
		if (oldPassword == null || oldPassword.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("旧密码不能为空");
			return rsInfo;
		}

		// 判断账号是否已经注册
		User user = null;
		try {
			user = userService.getUserByUsername(userName);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("10001");
			rsInfo.setMessage("用户名不正确");
			return rsInfo;
		}

		if (user == null) {
			rsInfo.setCode("10001");
			rsInfo.setMessage("账号不存在");
			return rsInfo;
		} else {
			if (!oldPassword.equals(user.getPassword())) {
				rsInfo.setCode("10001");
				rsInfo.setMessage("旧密码不正确");
				return rsInfo;
			}
			int count = 0;
			User u = new User();
			u.setUserId(user.getUserId());
			u.setPassword(newPassword);
			try {
				count = userService.modifyUser(u);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setCode("10002");
				rsInfo.setMessage("修改 密码失败");
				return rsInfo;
			}
			if (count != 1) {
				rsInfo.setCode("10002");
				rsInfo.setMessage("修改 密码失败");
				return rsInfo;
			}

			rsInfo.setMessage("修改成功");
			rsInfo.setData(u);
			return rsInfo;
		}
	}
	
	
	/**
	 * @description 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/resetPassword")
	public @ResponseBody Object resetPassword(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		String userName = request.getParameter("userName");
		String newPassword = request.getParameter("newPassword");
		String confPassword = request.getParameter("confPassword");
		if (userName == null || userName.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("用户名不能为空");
			return rsInfo;
		}
		if (newPassword == null || newPassword.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("新密码不能为空");
			return rsInfo;
		}
		if (confPassword == null || confPassword.equals("")) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("确认密码不能为空");
			return rsInfo;
		}
		if (confPassword == null || !confPassword.equals(newPassword)) {
			rsInfo.setCode("10000");
			rsInfo.setMessage("两次输入密码不一致");
			return rsInfo;
		}

		// 判断账号是否已经注册
		User user = null;
		try {
			user = userService.getUserByUsername(userName);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("10001");
			rsInfo.setMessage("用户名不正确");
			return rsInfo;
		}

		user = new User();
		user.setPassword(newPassword);
		user.setUserName(userName);
		user.setUpdatedDate(LocalDateTime.now());
		int count  = 0;
		try {
			count = userService.modifyUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("10002");
			rsInfo.setMessage("重置密码失败");
			return rsInfo;
		}
		if (count != 1) {
			rsInfo.setCode("10002");
			rsInfo.setMessage("重置密码失败");
			return rsInfo;
		}
		rsInfo.setMessage("修改成功");
		return rsInfo;
		
	}
	
	
	/**
	 * Description: 第三方登录
	 *  
	 * @param snsId 登录名
	 * @param nickName 昵称
	 * @param icon 头像
	 * 
	 * @return user
	 * 
	 */
	@RequestMapping(value = "/user/thirdPartyLogin")
	public @ResponseBody
	Object thirdPartyLogin(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		String snsId = request.getParameter("snsId");
		// 1 第一步直接登录  2 第二步绑定手机后登录
		String type = request.getParameter("type");

		String nickName = request.getParameter("nickName");
		String userIcon = request.getParameter("userIcon");		
		String mobile = request.getParameter("mobile");
		String verCode = request.getParameter("verCode");
		
		ResultInfo rsInfo = new ResultInfo();

		if (snsId == null || snsId.equals("")) {
			rsInfo.setMessage("sns ID不能为空！");
			rsInfo.setCode("11112");
			return rsInfo;

		}
		
		
		if (type == null || "".equals(type)) {
			rsInfo.setMessage("第三方登录 类型不能为空！");
			rsInfo.setCode("11112");
			return rsInfo;

		}

		User user = null;
		int count;
		if(Integer.parseInt(type) == 1) {
			try {
				user = userService.getUserBySnsName(snsId);

			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("登录出错！");
				rsInfo.setCode("11211");
				return rsInfo;
			}
			if (user != null) {

				rsInfo.setData(user);
				rsInfo.setMessage("登录成功！");
				

			}else {
				
				rsInfo.setMessage("没有绑定手机号，进入第2步绑定手机号！");
				rsInfo.setCode("99999");
				return rsInfo;
							
			}
			

		} else if(Integer.parseInt(type) == 2) {
						
			if(mobile == null || mobile.equals("")) {			
				rsInfo.setMessage("请输入绑定手机号！");
				rsInfo.setCode("11211");
				return rsInfo;
			}
			
			if(verCode == null || verCode.equals("")) {			
				rsInfo.setMessage("请输入验证码！");
				rsInfo.setCode("11211");
				return rsInfo;
			}
			
			// 验证码校验
			VerificationCode verificationCode;
			verificationCode = userService.getVercodeByUserName(mobile);
			
			if(verificationCode == null){
					// 验证码不存在，重新获取
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码不存在。");
					return rsInfo;
				}
			
		
				// 判断时间是否过期
			if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
					// 超过10分钟， 验证码过期
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码过期。");
					return rsInfo;
				} 
					// 验证码校验
					if (!verCode.equals(verificationCode.getVerCode())) {
						
						rsInfo.setCode("10001");
						rsInfo.setMessage("验证码不正确。");
						return rsInfo;
						
					}
					
					
			
			User u;
			try {
				
				u = userService.getUserByUsername(mobile);

			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取用户信息失败");
				rsInfo.setCode("11212");
				return rsInfo;
			}
			
			if(u != null) {
				
				u.setSnsId(snsId);
				
				count = 0;
				try {
					
					count = userService.modifyUser(user);

				} catch (Exception e) {
					e.printStackTrace();
					rsInfo.setMessage(e.getMessage());
					rsInfo.setCode("11212");
					return rsInfo;
				}
				
				if(count == 0) {
					
					rsInfo.setMessage("修改用户失败");
					rsInfo.setCode("11212");
					return rsInfo;
					
				}
				
			}else {
				
				count = 0;
				
				try {
					u = new User();	
					
					
					u.setSnsId(snsId);									
					u.setUserName(mobile);
					String emoji = EmojiFilter.filterEmoji(nickName);
					u.setNickName(emoji);
					u.setUserIcon(userIcon);
					u.setCreatedDate(LocalDateTime.now());
					
					count = userService.addUser(u);

				} catch (Exception e) {
					e.printStackTrace();
					rsInfo.setMessage(e.getMessage());
					rsInfo.setCode("11212");
					return rsInfo;
				}
				
				if(count == 0) {
					
					rsInfo.setMessage("添加用户失败");
					rsInfo.setCode("11212");
					return rsInfo;
					
				}				
				
			}
			
			if (count > 0) {
				try {
					user = userService.getUserBySnsName(snsId);
				} catch (Exception e) {
					e.printStackTrace();
					rsInfo.setMessage("获取用户出错！");
					rsInfo.setCode("11213");
					return rsInfo;
				}
				
				rsInfo.setData(user);
				rsInfo.setMessage("登录成功！");
				return rsInfo;
			}
		}
		return rsInfo;
	}


	/**
	 * 小程序登录
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/user/mpLogin")
	public @ResponseBody
	Object mpLogin(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String code = request.getParameter("code");

		ResultInfo rsInfo = new ResultInfo();

		if (code == null || "".equals(code)) {
			rsInfo.setMessage("code不能为空！");
			rsInfo.setCode("11112");
			return rsInfo;

		}

		JSONObject result;
		try {

			result = (JSONObject) WechatUtil.getMpAccessToken(code);
			if (null == result) {

				rsInfo.setMessage("结果解析为空！");
				rsInfo.setCode("15115");
				return rsInfo;
			}
		} catch (JSONException e) {

			rsInfo.setMessage("结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		String openId = result.getString("openid");

		User user;
		try {

			user = userService.getUserByMpId(openId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("登录出错！");
			rsInfo.setCode("11211");
			return rsInfo;
		}
		if (user != null) {

			rsInfo.setData(user);
			rsInfo.setMessage("登录成功！");

		}else{
			rsInfo.setCode("50000");
			rsInfo.setMessage("用户尚未注册！");
		}
		return rsInfo;
	}


	/**
	 * 微信登录
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/user/wxLogin")
	public @ResponseBody
	Object wxLogin(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");

		ResultInfo rsInfo = new ResultInfo();

		String code = request.getParameter("code");


		if (code == null || "".equals(code)) {
			rsInfo.setMessage("码号不能为空！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		JSONObject result = null;

		JSONObject userInfo = null;

		String nickName;
		String userIcon;
		User user;
		String openID;
		try {

			result = (JSONObject) WechatUtil.getAccessToken(code);

			String accessToken = result.getString("access_token");
			openID = result.getString("openid");
			String refreshToken = result.getString("refresh_token");
			long expiresIn = result.getLong("expires_in");

			if (openID == null || "".equals(openID)) {

				rsInfo.setMessage("openID获取失败！");
				rsInfo.setCode("15114");
				return rsInfo;

			}
			try {
				user = userService.getUserBySnsName(openID);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取用户出错1111！" + e.getMessage());
				rsInfo.setCode("112112");
				return rsInfo;
			}

			if (user != null) {

				rsInfo.setData(user);
				rsInfo.setMessage("登录成功！");
				return rsInfo;
			}

			try {
				userInfo = (JSONObject) WechatUtil.getUserInfo(accessToken, openID,
						expiresIn);

				if (userInfo == null) {
					rsInfo.setMessage("获取第三方信息失败！");
					rsInfo.setCode("15114");
					return rsInfo;

				}
				nickName = userInfo.getString("nickname");
				userIcon = userInfo.getString("headimgurl");

			} catch (JSONException e) {


				rsInfo.setMessage("userInfo结果解析不正确！" + userInfo + e.getMessage());
				rsInfo.setCode("15114");
				rsInfo.setData(userInfo);
				return rsInfo;

			}

		} catch (JSONException e) {

			rsInfo.setMessage("token结果解析不正确！" + e.getMessage());
			rsInfo.setCode("15114");
			rsInfo.setData(result);
			return rsInfo;

		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("登录出错！" + e.getMessage());
			rsInfo.setCode("11211");
			return rsInfo;
		}

		int count;
		try {
			user = new User();

			user.setSnsId(openID);
			user.setPassword("123456");
			String emoji = EmojiFilter.filterEmoji(nickName);
			user.setNickName(emoji);
			user.setUserIcon(userIcon);
			user.setCreatedDate(LocalDateTime.now());
			count = userService.addUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage(e.getMessage());
			rsInfo.setCode("11212");
			return rsInfo;
		}

		if (count > 0) {
			try {
				user = userService.getUserBySnsName(openID);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取用户出错2222！" + e.getMessage());
				rsInfo.setCode("11213");
				return rsInfo;
			}
			if (user == null) {
				rsInfo.setMessage("没有找到此用户！");
				rsInfo.setCode("11212");
				return rsInfo;

			}
			rsInfo.setData(user);
			rsInfo.setMessage("登录成功！");
			return rsInfo;
		}

		return rsInfo;
	}
	
	/**
	 * Description: 绑定手机
	 *  
	 * @param userId 用户ID
	 * @param mobile 手机号
	 * @param authcode 验证码
	 * 
	 * @return count
	 * 
	 */
	@RequestMapping(value = "/user/bindMobile")
	public @ResponseBody
	Object bindMobile(HttpServletRequest request, HttpSession session) {

		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String verCode = request.getParameter("verCode");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("11127");
			return rsInfo;
		}
		if (mobile == null || mobile.equals("")) {
			rsInfo.setMessage("手机号不能为空！");
			rsInfo.setCode("11128");
			return rsInfo;
		}
		if (verCode == null || verCode.equals("")) {
			rsInfo.setMessage("验证码不能为空！");
			rsInfo.setCode("11129");
			return rsInfo;
		}

		// 验证码校验
		VerificationCode verificationCode = null;
		verificationCode = userService.getVercodeByUserName(mobile);
		
		if(verificationCode == null){
				// 验证码不存在，重新获取
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码不存在。");
				return rsInfo;
			}
		
	
			// 判断时间是否过期
		if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
				// 超过10分钟， 验证码过期
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码过期。");
				return rsInfo;
			} 
				// 验证码校验
				if (!verCode.equals(verificationCode.getVerCode())) {
					
					rsInfo.setCode("10001");
					rsInfo.setMessage("验证码不正确。");
					return rsInfo;
					
				}
					// 校验成功
					// 判断账号是否已经注册
					User user;
					try {
						user = userService.getUserByUserId(Integer.parseInt(userId));
					} catch (Exception e) {
						e.printStackTrace();
						rsInfo.setCode("10001");
						rsInfo.setMessage("获取用户失败");
						return rsInfo;
					}

					if (user == null) {
						rsInfo.setCode("10001");
						rsInfo.setMessage("账号不存在");
						return rsInfo;
					} 
					
					
						int count = 0;
						User u = new User();
						u.setUserId(user.getUserId());
						u.setUserName(mobile);

						try {
							count = userService.modifyUser(u);
						} catch (Exception e) {
							e.printStackTrace();
							rsInfo.setCode("10002");
							rsInfo.setMessage("绑定手机失败");
							return rsInfo;
						}
						if (count == 0) {
							rsInfo.setCode("10002");
							rsInfo.setMessage("绑定手机失败");
							return rsInfo;
						}
								
		rsInfo.setMessage("绑定手机成功");
		rsInfo.setData(count);
		return rsInfo;
		
}
	
	
	/**
	 * Description: 绑定手机
	 *  
	 * @param userId 用户ID
	 * @param mobile 手机号
	 * @param authcode 验证码
	 * 
	 * @return count
	 * 
	 */
	@RequestMapping(value = "/user/wxBindMobile")
	public @ResponseBody
	Object wxBindMobile(HttpServletRequest request, HttpSession session) {

		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String verCode = request.getParameter("verCode");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("11127");
			return rsInfo;
		}
		if (mobile == null || mobile.equals("")) {
			rsInfo.setMessage("手机号不能为空！");
			rsInfo.setCode("11128");
			return rsInfo;
		}
		if (verCode == null || verCode.equals("")) {
			rsInfo.setMessage("验证码不能为空！");
			rsInfo.setCode("11129");
			return rsInfo;
		}
		
		
		// 验证码校验
		VerificationCode verificationCode = null;
		verificationCode = userService.getVercodeByUserName(mobile);
		
		if(verificationCode == null){
				// 验证码不存在，重新获取
				rsInfo.setCode("10001");
				rsInfo.setMessage("验证码不存在。");
				return rsInfo;
			}
		
	
			// 判断时间是否过期
		if (Duration.between(verificationCode.getUpdatedDate(), LocalDateTime.now()).toMinutes() > 10) {
			// 超过10分钟， 验证码过期
			rsInfo.setCode("10001");
			rsInfo.setMessage("验证码过期。");
			return rsInfo;
		}
		// 验证码校验
		if (!verCode.equals(verificationCode.getVerCode())) {

			rsInfo.setCode("10001");
			rsInfo.setMessage("验证码不正确。");
			return rsInfo;

		}


		// 校验成功
		// 判断账号是否已经注册
		User user = null;
		try {
			user = userService.getUserByUserId(Integer.parseInt(userId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("10001");
			rsInfo.setMessage("获取用户失败");
			return rsInfo;
		}

		if (user == null) {
			rsInfo.setCode("10001");
			rsInfo.setMessage("账号不存在");
			return rsInfo;
		}


		User uu = null;
		try {
			uu = userService.getUserByUsername(mobile);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("10001");
			rsInfo.setMessage("获取用户失败");
			return rsInfo;
		}
		int count = 0;
		User u = null;
		if (uu != null) {

			uu.setSnsId(user.getSnsId());

			try {
				count = userService.modifyUser(uu);

				count = userService.delUser(Integer.parseInt(userId));
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setCode("10002");
				rsInfo.setMessage("绑定手机失败");
				return rsInfo;
			}

			u = uu;

		} else {


			user.setUserName(mobile);
			try {
				count = userService.modifyUser(user);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setCode("10002");
				rsInfo.setMessage("绑定手机失败");
				return rsInfo;
			}
			u = user;
		}


		if (count == 0) {
			rsInfo.setCode("10002");
			rsInfo.setMessage("绑定手机失败");
			return rsInfo;
		}

		rsInfo.setMessage("绑定手机成功");
		rsInfo.setData(user);
		return rsInfo;

	}
	
	
	@GetMapping(value = "/user/getUserBySnsName/{snsId}")
	public @ResponseBody Object getUserBySnsName(@PathVariable("snsId") String snsId) {
		ResultInfo rsInfo = new ResultInfo();
		
		User user;
		try {
			user = userService.getUserBySnsName(snsId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取用户出错！");
			rsInfo.setCode("112112");
			return rsInfo;
		}
		
		if (user != null) {

			rsInfo.setData(user);
			rsInfo.setMessage("登录成功！");

		}
		
		return rsInfo;
	}

	/**
	 * 查询我的账户信息
	 *
	 * @param userId 用户id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/user/getMyAccountInfo",method = RequestMethod.GET)
	public ResultInfo getMyAccountInfo(@RequestParam("userId") String userId) {
		ResultInfo resultInfo = new ResultInfo();
		MyAccountVO myAccountVO;
		try {

			//获取用户信息
			User user = userService.getUserByUserId(Integer.parseInt(userId));

			if (null == user) {

				resultInfo.setMessage("用户不存在！");
				resultInfo.setCode("112112");
				return resultInfo;
			} else {

				myAccountVO = userService.getAccountInfo(userId,user.getCompanyId());
			}


		}catch (Exception e){
			e.printStackTrace();

			resultInfo.setMessage("获取用户出错！");
			resultInfo.setCode("112112");
			return resultInfo;
		}

		resultInfo.setData(myAccountVO);
		return resultInfo;
	}

	/**
	 * 我的页面
	 *
	 * @param userId 用户id
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/user/getMyInfo")
	public ResultInfo getMyInfo(@RequestParam("userId") Integer userId) {
		ResultInfo resultInfo = new ResultInfo();

		RsUser rsUser = new RsUser();
		User user = userService.getUserByUserId(userId);
		if (null == user) {

			resultInfo.setMessage("用户不存在！");
			resultInfo.setCode("112112");
			return resultInfo;
		} else {

			PagingTool pagingTool = new PagingTool();
			Map<String, Object> map = new HashMap<>(12);
			map.put("userId", userId);
			pagingTool.setParams(map);

			//查询用户优惠券数量
			int receiveCount = receiveService.myReceiveCount(pagingTool);
			//查询用户收藏数量
			int collectCount = collectService.countTotal(pagingTool);
			//查询用户关注数量
			int focusCount = focusService.countTotal(pagingTool);


			BeanUtils.copyProperties(user, rsUser);

			rsUser.setCollectCount(collectCount);
			rsUser.setFocusCount(focusCount);
			rsUser.setReceiveCount(receiveCount);
		}

		resultInfo.setData(rsUser);
		return resultInfo;
	}

	/**
	 * 小程序注册
	 *
	 * @param request 请求
	 * @param response 相应
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/user/mpRegister")
	@ResponseBody
	public ResultInfo mpRegister(HttpServletRequest request,
								 HttpServletResponse response,
								 @RequestBody JSONObject jsonObject) {
		ResultInfo rsInfo = new ResultInfo();
		response.addHeader("Access-Control-Allow-Origin", "*");

		String code = (String) jsonObject.get("code");
		String encrypted = (String) jsonObject.get("encrypted");
		String iv = (String) jsonObject.get("iv");
		if (StringUtil.isEmpty(code)) {
			rsInfo.setMessage("code不能为空！");
			rsInfo.setCode("11112");
			return rsInfo;
		}

		JSONObject result1;
		try {
			//调用微信接口获取 openid,session_key,unionid
			result1 = (JSONObject) WechatUtil.getMpAccessToken(code);
			if (null == result1) {
				rsInfo.setMessage("用户信息结果解析不正确！");
				rsInfo.setCode("15114");
				return rsInfo;
			}
		} catch (JSONException e) {

			rsInfo.setMessage("用户信息结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;
		}

		String openId = result1.getString("openid");
		String session_key = result1.getString("session_key");

		User user;
		try {

			user = userService.getUserByMpId(openId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("查询用户出错！");
			rsInfo.setCode("11211");
			return rsInfo;
		}
		if (user != null) {

			rsInfo.setData(user);
			rsInfo.setMessage("用户已存在！");
			return  rsInfo;
		}

		JSONObject result2;
		try {
			//调用微信接口获取 access_token,expires_in
			result2 = (JSONObject) WechatUtil.getMpAccessToken();
			if (null == result2) {
				rsInfo.setMessage("用户信息结果解析不正确！");
				rsInfo.setCode("15114");
				return rsInfo;
			}
		} catch (JSONException e) {

			rsInfo.setMessage("用户信息结果解析不正确！");
			rsInfo.setCode("15114");
			return rsInfo;
		}



		User addUser;

		String userPhone;
		try {
			//获取微信绑定手机号信息
			userPhone = AES.wxDecrypt(encrypted, session_key, iv);
			userPhone = (String) com.alibaba.fastjson.JSONObject.parseObject(userPhone).get("phoneNumber");
			if (null == userPhone) {
				rsInfo.setMessage("用户绑定手机号为空！");
				rsInfo.setCode("15115");
				return rsInfo;
			}
		} catch (Exception e) {

			rsInfo.setMessage("绑定的手机号结果解析不正确！");
			rsInfo.setCode("15116");
			return rsInfo;
		}

		int count;
		try {
			addUser = new User();


			addUser.setMpId(openId);
			//String emoji = EmojiFilter.filterEmoji(userPhone);
			addUser.setUserName(userPhone);
			addUser.setMobile(userPhone);
			addUser.setUserName(userPhone);
			count = userService.addUser(addUser);

		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage(e.getMessage());
			rsInfo.setCode("11212");
			return rsInfo;
		}

		if (count > 0) {
			try {
				addUser = userService.getUserByMpId(openId);
			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取用户信息出错！");
				rsInfo.setCode("11213");
				return rsInfo;
			}
			rsInfo.setData(addUser);
			rsInfo.setMessage("登录成功！");
			return rsInfo;
		}
		return rsInfo;
	}
}
