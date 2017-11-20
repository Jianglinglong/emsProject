package com.six.ems.web.controller.score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Score;
import com.six.ems.entity.tables.Student;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.score.ScoreServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

/**
 * 查询成绩
 */
public class ScoreServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ScoreServlet() {
	}

	/**
	 * 转发到Score页面
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 转发到 WEB-INF/jsp/Score/Score.jsp
		request.getRequestDispatcher("/WEB-INF/jsp/score/score.jsp").forward(request, response);
	}

	/**
	 * 查询所有成绩
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void queryScore(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// // 获取传入当前页和每页显示数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 获取要查询的姓名
		String scoreName = request.getParameter("scoreName");
		// 获取要查询的科目名称
		String scoreCourse = request.getParameter("scoreCourse");
		
		// 创建对象
		AdminService adminService = new AdminServiceImpl();
		// 构建条件对象
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		
		// 创建储存学生信息集合对象
		List<Student> students = new ArrayList<Student>();
		// 判断姓名是否非空
		if (scoreName != null && !"".equals(scoreName)) {
			// 构建查询条件
			condition.put("stu_real_name", new SelectItem(scoreName, SelectItem.LikeSelect.CONTAIN));
			// 调用方法查询学生信息
			students = adminService.getTypeByCondition(Student.class, condition);
		}
		
		// 创建科目集合对象
		List<Course> courses = new ArrayList<Course>();
		// 判断科目是否非空
		if (scoreCourse != null && !"".equals(scoreCourse)) {
			// 清除姓名条件
			condition.clear();
			// 构建查询条件（模糊查询）
			condition.put("course_name", new SelectItem(scoreCourse, SelectItem.LikeSelect.CONTAIN));
			// 根据科目名称查询科目
			courses = adminService.getTypeByCondition(Course.class, condition);
		}

		// 创建集合对象
		List<Score> scores = new ArrayList<Score>();
		// 判断学生信息集合和课程信息集合是否非空
		if (CollectionUtils.isNotBlank(students) && CollectionUtils.isNotBlank(courses)) {
			// 清除条件
			condition.clear();
			// 循环学生构建条件
			for (Student student : students) {
				// 构建学生条件
				condition.put("stu_id", new SelectItem(student.getStuId()));
				// 循环课程构建条件
				for (Course course : courses) {
					// 构建课程条件
					condition.put("course_id", new SelectItem(course.getCourseId()));
					// 调用方法查询成绩表相关信息
					List<Score> score = adminService.getTypeByCondition(Score.class, condition);
					// 判断查询的信息是否非空
					if (CollectionUtils.isNotBlank(score)) {
						// 将查询到的信息存入集合
						scores.addAll(score);
					}
				}
			}
		} else if (CollectionUtils.isNotBlank(students)) {
			// 清除查询条件
			condition.clear();
			// 循环学生对象构建条件
			for (Student student : students) {
				// 构建查询学生条件
				condition.put("stu_id", new SelectItem(student.getStuId()));
				//  调用方法查询成绩表相关信息
				List<Score> score = adminService.getTypeByCondition(Score.class, condition);
				// 判断查询的信息是否非空
				if (CollectionUtils.isNotBlank(score)) {
					// 将查询到的信息存入集合
					scores.addAll(score);
				}
			}
		} else if (CollectionUtils.isNotBlank(courses)) {
			// 清除查询条件
			condition.clear();
			// 循环课程构建条件
			for (Course course : courses) {
				// 构建课程查询条件
				condition.put("course_id", new SelectItem(course.getCourseId()));
				//  调用方法查询成绩表相关信息
				List<Score> score = adminService.getTypeByCondition(Score.class, condition);
				// 判断查询的信息是否非空
				if (CollectionUtils.isNotBlank(score)) {
					// 将查询到的信息存入集合
					scores.addAll(score);
				}
			}
		}

		// 判读查询条件是否有
		if (condition.size() == 0) {
			// 查询所有信息
			scores = adminService.getTypesForPage(Score.class, page, rows);
		}
		// 获取信息总条数
		int size = adminService.getTypeItems(Score.class).size();
		// 调用方法查询
		String dataGritJson = CollectionUtils.creatDataGritJson(scores, size);
		System.out.println(dataGritJson);
		// 响应结果
		response.getWriter().print(dataGritJson);
	}

	/**
	 * 添加学生信息方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 调用工具类出入对象封装数据---注意(表单名必须与Score对象的属性相同)
		Score score = WebRequestUtil.parseObject(Score.class, request);
		// 创建ScoreServiceImpl实例对象
		ScoreServiceImpl scoreService = new ScoreServiceImpl();
		// 响应结果 ScoreService.scoreAdd(Score)(调用方法实现添加数据)
		response.getWriter().print(scoreService.scoreAdd(score) > 0 ? "OK" : "NO");
	}

	/**
	 * 修改学生信息方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 调用工具类出入对象封装数据---注意(表单名必须与Score对象的属性相同)
		Score score = WebRequestUtil.parseObject(Score.class, request);
		// 创建ScoreServiceImpl实例对象
		ScoreServiceImpl ScoreService = new ScoreServiceImpl();
		// 响应结果 courseService.courseAdd(Score)(调用方法实现修改数据)
		response.getWriter().print(ScoreService.scoreUpdate(score) > 0 ? "OK" : "NO");
	}

	/**
	 * 删除学生信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 创建ScoreServiceImpl实例对象
		ScoreServiceImpl scoreService = new ScoreServiceImpl();
		// 获取请求删除的id
		String scoreId = request.getParameter("scoreId");
		// System.out.println(stuId);
		// 用逗号分隔id数组
		String[] sc = scoreId.split(",");
		int row = 0;// 定义全局变量
		// 遍历id个数
		for (String sd : sc) {
			Score score = new Score(Integer.valueOf(sd));
			row += scoreService.scoreDelete(score);// 调用删除方法
		}
		// 判断是否成功
		if (row == sc.length) {
			response.getWriter().print("OK");
		} else if (row == 0) {
			response.getWriter().print("NO");// 部分
		} else {
			response.getWriter().print("NK");
		}
	}

}
