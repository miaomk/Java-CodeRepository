package com.techwells.wumei.annotation;
//package com.yilan.elantrip.annotation;
//
//import java.util.Date;
//import java.util.UUID;
//
//import javax.servlet.http.HttpSession;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import com.yilan.elantrip.domain.Log;
//import com.yilan.elantrip.domain.User;
//import com.yilan.elantrip.service.LogService;
//import com.yilan.elantrip.util.WebUtil;
//
//@Aspect
//@Component
//public class SystemLogAspect{
//	
//	@Autowired
//    private LogService logService;
//
//    @Pointcut("@annotation(systemLog)")
//    public void systemLogPointcut(SystemLog systemLog) {
//    }
//
//    @Around("systemLogPointcut(systemLog)")
//    public Object aroundMethod(ProceedingJoinPoint point, SystemLog systemLog) throws Throwable {
//        long time = System.currentTimeMillis();
//        try {
//            Object returns = point.proceed();
//            SaveDB(point, returns, systemLog, System.currentTimeMillis() - time);
//            return returns;
//        } catch (Throwable e) {
//        	SaveDB(point, e, systemLog, System.currentTimeMillis() - time);
//            throw e;
//        }
//    }
//
//    //保存到数据库
//    private void SaveDB(ProceedingJoinPoint point, Object returns, SystemLog systemLog, Long time) {
//        // 获取相关参数
//        WebUtil wu = WebUtil.getInstance();
//        HttpSession session = wu.getSession();//获取session
//        User user = (User) session.getAttribute("user");//读取session中的用户
//        String ip = wu.getIpAddress();// IP地址
//        String desc = !StringUtils.isEmpty(systemLog.module()) ? systemLog.module() : systemLog.desc();
//        String otion = !StringUtils.isEmpty(systemLog.desc()) ? systemLog.desc() : systemLog.module();
//        // 构造入库参数
//        Log log = new Log();
//        log.setLogId(UUID.randomUUID().toString().replaceAll("-", ""));
//        //登录ip
//        log.setLoginIp(ip);
//        // 用户信息
//        if (user != null) {
//            log.setAdminAccount(user.getUserId());
//        }
//        // 请求信息
//        log.setModule(desc);
//        log.setOperation(otion);
//        log.setActivated(1);
//        log.setDeleted(0);
//        log.setCreatedDate(new Date());
//        try {
//            // 入库
//           logService.insertSelective(log);
//        } catch (Exception e) {
//        	e.getLocalizedMessage();
//        }
//    }
//}
