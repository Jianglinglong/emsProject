package com.six.ems.web.controller.exam;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.*;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.ExamServiceImpl;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.course.CourseService;
import com.six.ems.web.service.interfaces.users.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class ExamServlet
 */
public class ExamServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamServlet() {
        super();
    }

    public String initExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "WEB-INF/jsp/exam/exam.jsp";
    }

    /**
     * 获取所有课程
     */
    public void getCourseJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        User user = new UserServiceImpl().getUserByUserId(userId);
        List<Map<String, Object>> courseList = new ArrayList<>();

        List<TeacherCourse> teacherCourses = new TeacherServiceImpl().geTeacherCoursesByTeaId(user.getTeaId());
        if (CollectionUtils.isNotBlank(teacherCourses)) {
            CourseService courseService = new CourseServiceImpl();
            for (TeacherCourse teacherCourse : teacherCourses) {
                Course course = courseService.getCourseByID(teacherCourse.getCourseId());
                Map<String, Object> map = new HashMap<>();
                map.put("id", course.getCourseId());
                map.put("name", course.getCourseName());
                courseList.add(map);
            }
        }

        String json = JSON.toJSONString(courseList);
        response.getWriter().print(json);
    }

//	考试班级
	public void getExamJson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			List<Classes> className = new ExamServiceImpl().getClassByCondition();
	        List<Map<String, Object>> classList = new ArrayList<>();
	        String json = null;
	        if (className != null) {
				for (Classes classes : className) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", classes.getClassId());
					map.put("name", classes.getClassName());
					classList.add(map);
				}
				json = JSON.toJSONString(classList);
			}
			response.getWriter().print(json == null ? "[{}]" : json);

	}
//	考试地点
	public void getLocationJson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			List<Location> locationName = new ExamServiceImpl().getLocationByCondition();
	        List<Map<String, Object>> locationList = new ArrayList<>();
	        String json = null;
	        if (locationName != null) {
				for (Location location : locationName) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", location.getLocationId());
					map.put("name", location.getLocationName());
					locationList.add(map);
				}
				json = JSON.toJSONString(locationList);
			}
			response.getWriter().print(json == null ? "[{}]" : json);

	}
// 获取试卷名称
	 public void getPaperNameById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String paperId = request.getParameter("paperId");
	        Paper paper = new PaperServiceImpl().getPaperById(Integer.valueOf(paperId));
	        String courseName = paper.getPaperName();
	        response.getWriter().print(courseName);
	    }
    /**
     * 获取所有老师根据课程
     */
    public void getTeaJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coursId = request.getParameter("coursId");
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(Integer.valueOf(coursId)));
        List<Teacher> teachers = new ExamServiceImpl().getTeacherByCondition(condition);
        List<Map<String, Object>> courseList = new ArrayList<>();
        String json = null;
        if (teachers != null) {
            for (Teacher teacher : teachers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", teacher.getTeaId());
                map.put("name", teacher.getTeaRealName());
                courseList.add(map);
            }
            json = JSON.toJSONString(courseList);
        }
        response.getWriter().print(json == null ? "[{}]" : json);

    }
    
    /**
     * 根据课程id获取paper
     */
    public void getPaperJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coursId = request.getParameter("coursId");
        List<Paper> papers = new PaperServiceImpl().getPaperByCourseId(Integer.valueOf(coursId));
        List<Map<String, Object>> paperList = new ArrayList<>();
        String json = null;
        if (papers != null) {
            for (Paper paper : papers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", paper.getPaperID());
                map.put("name", paper.getPaperName());
                paperList.add(map);
            }
            json = JSON.toJSONString(paperList);
        }
        response.getWriter().print(json == null ? "[{}]" : json);
    }


    public void getExams(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取	传入当前页和每页显示数
        String json = null;

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String couName = request.getParameter("couName");
        // 创建对象
        AdminService adminService = new AdminServiceImpl();
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        //判断姓名是否非空
        if (couName != null && !"".equals(couName)) {
            condition.put("course_name", new SelectItem(couName, SelectItem.LikeSelect.CONTAIN));
        }
        List<Exam> exams = new ArrayList<>();
        List<Course> course = adminService.getTypesForPage(Course.class, condition, Integer.valueOf(page), Integer.valueOf(rows));
        if (CollectionUtils.isNotBlank(course)) {
            Exam exam = new Exam();
            for (Course cou : course) {
                Integer courseId = cou.getCourseId();
                exam.setCourseID(courseId);
                List<Exam> select = adminService.getTypeItems(exam);
                if (CollectionUtils.isNotBlank(select)) {
                    exams.addAll(select);
                }
            }
        }
        //	获取信息总条数
        int size = exams.size();
        int begin = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        int end = begin + Integer.parseInt(rows);
        List<Exam> subList = exams.subList(begin, end > size ? size : end);
        // 调用方法查询
        json = CollectionUtils.creatDataGritJson(subList, size);
        response.getWriter().print(json);
    }

    /**
     * servlet 添加
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
        Exam exam = WebRequestUtil.parseObject(Exam.class, request);

        // 调用工具类存入数据
        Paper paper = new PaperServiceImpl().getPaperById(exam.getPaperID());
        exam.setPaperTime(paper.getPaperTime());

        ExamServiceImpl ExamService = new ExamServiceImpl();
        // 响应结果
        response.getWriter().print(ExamService.examAdd(exam) > 0 ? "OK" : "NO");
    }

    /**
     * 修改exam
     */

    public void updateExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        Exam exam = WebRequestUtil.parseObject(Exam.class, request);
        // 创建TeacherServiceImpl实例
        ExamServiceImpl ExamService = new ExamServiceImpl();
        // 判断  响应相应的结果
        response.getWriter().print(ExamService.examUpdate(exam) > 0 ? "OK" : "NO");

    }

    /**
     * 删除Seervlet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteExam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求删除的id

        String examId = request.getParameter("examID");
//        System.out.println(examId);
        // 用逗号分隔id数组
        String[] exa = examId.split(",");
        int row = 0;// 定义全局变量

        // 遍历id个数
        for (String sd : exa) {
            Exam exam = new Exam(Integer.valueOf(sd));
            row += AdvanceUtil.delete(exam);

        }
        // 判断是否成功
        if (row == exa.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        } else {
            response.getWriter().print("NK");
        }
    }
    
    /**
     * 根据教室id获取教室名
     */
    public void getLocationNameByLocationId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationId = request.getParameter("locationId");
        System.out.println("locationId:"+locationId);
        Location condition = new Location();
        condition.setLocationId(Integer.valueOf(locationId));
        List<Location> locationList = new ExamServiceImpl().getLocationByLocationCondition(condition);
        String locationName = "";
        if(locationList != null) {
        	locationName = locationList.get(0).getLocationName();
        }
        response.getWriter().print(locationName);
    }

}
