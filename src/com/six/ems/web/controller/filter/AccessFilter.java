package com.six.ems.web.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.ems.utils.BundleUtil;

/**
 * 非法访问过滤器
 */

public class AccessFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AccessFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// 把请求和响应对象强制转换为Http的请求和响应对象
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// 获取当前过滤的请求路径
		String uri = request.getRequestURI();
		if (uri.contains("login") || !uri.contains("do")){
			chain.doFilter(request, response);
			return;
		}
		// 获取当前登录用户
		Object obj = request.getSession().getAttribute("userName");
		
		if (obj == null) {
			// 重定向登录
			String url = request.getContextPath()+"/login.jsp";
			response.getWriter().print("<script>top.location.href='"+url+"';</script>");
		} else {
			// 已经登录，直接放行
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
