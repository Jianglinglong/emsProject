package com.six.ems.web.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.User;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.users.StudentServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;

/**
 * Servlet implementation class UserInfo
 */
public class UserInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
    }
    
    /**
     * 转发到相应的页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String initUser(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	Object userId = request.getSession().getAttribute("userId");
    	String tea = "/WEB-INF/jsp/userInfo/userTeacher.jsp";
    	String stu = "/WEB-INF/jsp/userInfo/userStudent.jsp";
    	UserServiceImpl userServiceImpl = new UserServiceImpl();
    	String userInfo = userServiceImpl.showUserInfo(userId,tea,stu);
    	return userInfo;
    	
    }

	/**
	 * 显示用户个人信息
	 */
	public void listUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取存入的用户id
		Object userId = request.getSession().getAttribute("userId");
		User user = new UserServiceImpl().getUserByUserId((Integer)userId);
		Integer stuId = user.getStuId();
		Integer teaId = user.getTeaId();
		if(stuId != 0) {
			Student student = new StudentServiceImpl().getStudentById(stuId);
			String jsonString = JSON.toJSONString(student,true);
			response.getWriter().print("["+jsonString+"]");
		}else {
			Teacher teacher = new TeacherServiceImpl().getTeacherById(teaId);
			String jsonString = JSON.toJSONString(teacher);
			response.getWriter().print("["+jsonString+"]");
		}
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取存入的用户id
		Object userId = request.getSession().getAttribute("userId");
		User user = new UserServiceImpl().getUserByUserId((Integer)userId);
		Integer teaId = user.getTeaId();
		if(teaId != 0 ) {
			// 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
			Teacher teacher = WebRequestUtil.parseObject(Teacher.class, request);
			// 创建TeacherServiceImpl实例对象
			TeacherServiceImpl teacherService = new TeacherServiceImpl();
			// 响应结果  teacherService.teacherUpdate(teacher)(调用方法实现修改数据)
			response.getWriter().print(teacherService.teacherUpdate(teacher) > 0 ? "OK" : "NO");
		}else {
			// 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
			Student student = WebRequestUtil.parseObject(Student.class, request);
			// 创建StudentServiceImpl实例对象
			StudentServiceImpl studentService = new StudentServiceImpl();
			// 响应结果   courseService.courseAdd(course)(调用方法实现修改数据)
			response.getWriter().print(studentService.studentUpdate(student) > 0 ? "OK" : "NO");
		}
	}
	
}
