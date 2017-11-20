package com.six.ems.web.controller.course;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.StudentCourse;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.entity.tables.User;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.StudentServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 科目实现servlet
 */
public class CourseServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CourseServlet() {
       
    }
    
    /**
	 * 转发到course页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 转发到 /WEB-INF/jsp/course/course.jsp
		request.getRequestDispatcher("/WEB-INF/jsp/course/course.jsp").forward(request, response);
	}
	
    /**
     * 科目查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	public void queryCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取传入当前页和每页显示数
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		// 获取传入的科目名称
		String courseName = request.getParameter("courseSearchName");
		// 创建对象
		AdminService adminService = new AdminServiceImpl();
		// 构建条件
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		//判断科目是否非空
		if(courseName!=null && !"".equals(courseName)) {
			// 构建模糊查询条件
			condition.put("course_name", new SelectItem(courseName, SelectItem.LikeSelect.CONTAIN));
		}
		// 调用方法查询
		List<Course> Courses = adminService.getTypesForPage(Course.class,condition,Integer.valueOf(page), Integer.valueOf(rows));
		//	获取信息总条数
		int size = adminService.getTypeItems(Course.class).size();
		// 调用方法查询
		String dataGritJson = CollectionUtils.creatDataGritJson(Courses, size);
		response.getWriter().print(dataGritJson);
		
		// 创建CourseServiceImpl实例对象
		// CourseServiceImpl courseService = new CourseServiceImpl();
		// 响应结果
		// response.getWriter().print(courseService.courseQuery(Integer.valueOf(page), Integer.valueOf(rows)));
		
	}
	
	/**
	 * 添加科目
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
		Course course = WebRequestUtil.parseObject(Course.class, request);
		// 创建CourseServiceImpl实例对象
		CourseServiceImpl courseService = new CourseServiceImpl();
		// 响应结果   courseService.courseAdd(course)(调用方法实现添加数据)
		response.getWriter().print(courseService.courseAdd(course) > 0 ? "OK" : "NO");
	}
	
	/**
	 * 修改科目
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取传入的课程名称
		String courseName = request.getParameter("courseName");
		// 构建条件
		Map<String,SelectItem> condition = new HashMap<String,SelectItem>();
		condition.put("course_name", new SelectItem(courseName));
		// 调用方法查询
		List<Course> courses = AdvanceUtil.select(Course.class, condition);
		String name = null;
		if(courses.size() > 0) {
			Course course = courses.get(0);
			name = course.getCourseName();
		}
		
		if (courseName != null && !"".equals(courseName)) {
			if(courseName.equals(name)) {
				response.getWriter().print("NK");
			}else {
				// 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
				Course course = WebRequestUtil.parseObject(Course.class, request);
				// 创建CourseServiceImpl实例对象
				CourseServiceImpl courseService = new CourseServiceImpl();
				int row = courseService.courseUpdate(course);
				if (row > 0) {
					response.getWriter().print("OK");
				}else {
					response.getWriter().print("NO");
				}
			}
		}
		
		// 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
		// Course course = WebRequestUtil.parseObject(Course.class, request);
		// 创建CourseServiceImpl实例对象
		// CourseServiceImpl courseService = new CourseServiceImpl();
		// 响应结果   courseService.courseAdd(course)(调用方法实现修改数据)
		// response.getWriter().print(courseService.courseUpdate(course) > 0 ? "OK" : "NO");
	}
	
	/**
	 * 删除科目
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 创建CourseServiceImpl实例对象
		CourseServiceImpl courseService = new CourseServiceImpl();
		// 获取请求删除的id
		String courseId = request.getParameter("courseId");
		// 用逗号分隔要删除的id数组
		String[] cou = courseId.split(",");
		int row = 0;// 定义全局变量
		// 遍历id个数
		for (String sd : cou) {
			Course cs = new Course(Integer.valueOf(sd));
			// 调用方法
			row += courseService.courseDelete(cs);
		}
		// 判断是否成功
		if(row == cou.length) {
			response.getWriter().print("OK");
		}else if(row == 0) {
			response.getWriter().print("NO");
		}else {
			response.getWriter().print("NK");
		}
	}
	
	/**
	 * 查询科目名称
	 */
	public void getCourseNameById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseId = request.getParameter("courseId");
		Course course = new CourseServiceImpl().getCourseByID(Integer.valueOf(courseId));
		String courseName = course.getCourseName();
		response.getWriter().print(courseName);
	}
	
	/**
	 * 根据用户id获取课程
	 */
	public void getCourseByUserId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		// 获取Id
		User user = new UserServiceImpl().getUserByUserId(userId);
		// 定义json字符串格式
		List<Map<String, Object>> listJson = new ArrayList<>();
		// 获取课程Id
		if(user.getTeaId() != 0) {
			List<TeacherCourse> teacherCourses = new TeacherServiceImpl().geTeacherCoursesByTeaId(user.getTeaId());
			for(TeacherCourse teacherCourse : teacherCourses) {
				// 获取科目
				Course course = new CourseServiceImpl().getCourseByID(teacherCourse.getCourseId());
				Map<String, Object> map = new HashMap<>();
				map.put("id", course.getCourseId());
				map.put("name", course.getCourseName());
				listJson.add(map);
			}
		} else if(user.getStuId() != 0) {
			List<StudentCourse> studentCourseByStuId = new StudentServiceImpl().getStudentCourseByStuId(user.getStuId());
			for(StudentCourse studentCourse : studentCourseByStuId) {
				// 获取科目
				Course course = new CourseServiceImpl().getCourseByID(studentCourse.getCourseId());
				Map<String, Object> map = new HashMap<>();
				map.put("id", course.getCourseId());
				map.put("name", course.getCourseName());
				listJson.add(map);
			}
		}
		String json = JSON.toJSONString(listJson);
		
		response.getWriter().print(json);
	}
}
