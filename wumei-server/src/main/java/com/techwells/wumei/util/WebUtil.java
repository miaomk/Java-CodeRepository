package com.techwells.wumei.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class WebUtil {

    private final HttpServletRequest request;

    private static final WebUtil instance = new WebUtil();

    private WebUtil() {
        this.request = null;
    }

    public WebUtil(HttpServletRequest request) {
        this.request = request;
    }

    public static WebUtil getInstance() {
        return instance;
    }

    public HttpServletRequest getRequest() {
        if (request != null) {
            return request;
        }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }

    public String getSessionId() {
        HttpSession session = getSession(false);
        return session == null ? null : session.getId();
    }

    public void setSessionAttribute(String name, Object value) {
        HttpSession session = getSession();
        session.setAttribute(name, value);
    }

    public Object getSessionAttribute(String name) {
        HttpSession session = getRequest().getSession(false);
        return session != null ? session.getAttribute(name) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttribute(String name, Class<T> type) {
        return (T) getSessionAttribute(name);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getSessionAttributes(String name, Class<T> type) {
        return (List<T>) getSessionAttribute(name);
    }

    public String getContextPath() {
        return getRequest().getContextPath();
    }

    public String getWebRoot() {
        return getRequest().getSession().getServletContext().getRealPath("/");
    }

    public String getWebRoot(String path) {
        return getRequest().getSession().getServletContext().getRealPath("/") + path;
    }

    public String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
