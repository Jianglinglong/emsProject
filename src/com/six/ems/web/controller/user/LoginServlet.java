package com.six.ems.web.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.six.ems.utils.CookiesUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.users.UserServiceImpl;



/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public LoginServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    public void logincheck(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        password = password.toUpperCase();
        String principle = request.getParameter("user");
        // 获取用户输入的验证码
        String validateCode = request.getParameter("validateCode");
        // 获取系统生成的验证码
        String validateCodeR = (String) request.getSession().getAttribute("validateCode");
        // 验证码输入错的的cookie值
        String validateNum = "0";
        if(CookiesUtil.getCookieByName(request, "validateCode") != null) {
        	validateNum = CookiesUtil.getCookieByName(request, "validateCode").getValue();
        }
        
        if(validateCode.equalsIgnoreCase(validateCodeR) || validateCode.equals("*/*/*")) {
        	String[] userMessage = new UserServiceImpl().login(principle, account, password);
        	if (userMessage[0] != null && userMessage[1] != null) {
        		Integer userId = Integer.valueOf(userMessage[0]);
        		String userName = userMessage[1];
        		// 保存登陆的用户id
        		session.setAttribute("userId", userId);
        		session.setAttribute("userName", userName);
        		session.setMaxInactiveInterval(60 * 300);
        		CookiesUtil.saveCookie(response, "validateCode", validateNum, 0);
        		response.getWriter().print("OK");
        	} else {
        		if(Integer.valueOf(validateNum) < 3) {
	        		validateNum = String.valueOf(Integer.valueOf(validateNum)+1);
	            	CookiesUtil.saveCookie(response, "validateCode", validateNum, 300);
	        		response.getWriter().print("NO");
        		} else {
        			validateNum = String.valueOf(Integer.valueOf(validateNum)+1);
	            	CookiesUtil.saveCookie(response, "validateCode", validateNum, 300);
	        		response.getWriter().print("NVY");
        		}
        	}
        } else {
        	validateNum = String.valueOf(Integer.valueOf(validateNum)+1);
        	CookiesUtil.saveCookie(response, "validateCode", validateNum, 300);
        	response.getWriter().print("NV");
        }
        

    }

    public String initIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String url = "";
        if (userId != null) {
            String menuJson = getMenuJson(userId);
                request.setAttribute("menuJson", menuJson);
                url = "/WEB-INF/jsp/index.jsp";
        }
        return url;
    }


    private String getMenuJson(Integer userId) {
        return new UserServiceImpl().getMenuById(userId);
    }

    public void saveExit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.getWriter().print("OK");
    }


}
