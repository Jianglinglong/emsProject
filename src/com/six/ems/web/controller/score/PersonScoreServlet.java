package com.six.ems.web.controller.score;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.six.ems.entity.tables.Score;
import com.six.ems.entity.tables.User;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.score.ScoreServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;

/**
 * 查询学生个人成绩
 */
public class PersonScoreServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PersonScoreServlet() {
    }

    /**
	 * 转发到personScore页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showPersonScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 转发 WEB-INF/jsp/score/personScore.jsp
		request.getRequestDispatcher("/WEB-INF/jsp/student/personScore.jsp").forward(request, response);
	}
	
	/**
	 * 查询学生个人成绩
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void queryPersonScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取登录保存的用户id  
		Object userId = request.getSession().getAttribute("userId");
		// 调用方法获取用户信息
		User user = new UserServiceImpl().getUserByUserId((Integer)userId);
		// 获取学生id
    	Integer stuId = user.getStuId();
    	// 调用方法查询成绩
		List<Score> scoreByStuId = new ScoreServiceImpl().getScoreByStuId(stuId);
		// 转为json字符串
		String jsonString = JSON.toJSONString(scoreByStuId);
		//  调用方法  响应结果
		response.getWriter().print(jsonString);
	}

}
