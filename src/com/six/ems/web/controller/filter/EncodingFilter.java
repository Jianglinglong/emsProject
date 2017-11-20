package com.six.ems.web.controller.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 编码格式的过滤器
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

	private String encode;
    /**
     * Default constructor. 
     */
    public EncodingFilter() {
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
		
		// 设置响应对象的编码格式
		request.setCharacterEncoding(encode);
		response.setContentType("text/html;charset="+encode);

		chain.doFilter(new HttpRequestDecorator(request), response);
	}
	
	/**
	 * 定义一个处理get请求乱码的HttpServletRequest的装饰类
	 */
	private class HttpRequestDecorator extends HttpServletRequestWrapper {
		private HttpServletRequest request; // 持有被装饰类的引用
		
		public HttpRequestDecorator(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		public String getParameter(String name) {
			// 获取表单数据
			String value = request.getParameter(name);
			// 获取请求方法
			String method = request.getMethod();
			// 判断请求方式为GET才进行处理
			if ("GET".equals(method)) {
				try {
					if (value != null) {
						value = new String(value.getBytes("ISO-8859-1"),encode);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			return value;
		}
		
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.encode = fConfig.getInitParameter("encode");
	}

}
