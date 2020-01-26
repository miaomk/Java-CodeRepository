package com.techwells.wumei.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AllInterceptor implements HandlerInterceptor  {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
		String userId = (String)request.getAttribute("userId");
		System.out.println(userId);
		
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		String requestToken =  request.getHeader("token");
        String username =  (String)request.getSession().getAttribute("user");
        
        String sessionToken = (String)request.getSession().getAttribute("token");
        request.getHeaderNames();
        
        response.addHeader("Access-Control-Allow-Origin", "*");
        request.setAttribute("userId", "123455");
        
        
        
        return true;
        
/*        if(token == null){   
            request.getRequestDispatcher("/WebContent/login.jsp").forward(request, response);            
            return true;  
        }else  
            return true; */
	}
	
}	
