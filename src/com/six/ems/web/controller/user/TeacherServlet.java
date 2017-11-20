package com.six.ems.web.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.six.ems.utils.InsertUser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;
import com.six.ems.entity.utils.UserCourse;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.TeacherExcelUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.course.CourseService;
import com.six.ems.web.service.interfaces.users.AdminService;

/**
 * 教师信息
 */
public class TeacherServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
    }

    /**
     * 转发到Teacher页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 转发到  WEB-INF/jsp/Teacher/Teacher.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/teacher/teacher.jsp").forward(request, response);
    }

    /**
     * 转发到教师课程关系页面
     */
    public String initTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/WEB-INF/jsp/teacher/teacherCourse.jsp";
    }

    /**
     * 查询所有老师的方法
     * com.six.ems.web.controller.user.TeacherServlet.initTeacherCourse
     * com.six.ems.web.controller.user.TeacherServlet.initTeacherCourse
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void queryTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取传入当前页和每页显示数
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        String teaName = request.getParameter("teaName");
        String teaAccount = request.getParameter("teaAccount");
        // 创建对象
        AdminService adminService = new AdminServiceImpl();
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        //判断姓名是否非空
        if (teaName != null && !"".equals(teaName)) {
            condition.put("tea_real_name", new SelectItem(teaName, SelectItem.LikeSelect.CONTAIN));
        }
        //判断是否非空班级
        if (teaAccount != null && !"".equals(teaAccount)) {
            condition.put("tea_account", new SelectItem(teaAccount, SelectItem.LikeSelect.CONTAIN));
        }
        // 调用方法查询
        List<Teacher> students = adminService.getTypesForPage(Teacher.class, condition, Integer.valueOf(page), Integer.valueOf(rows));
        //	获取信息总条数
        int size = adminService.getTypeItems(Teacher.class).size();
        // 调用方法查询
        String dataGritJson = CollectionUtils.creatDataGritJson(students, size);
        // 响应结果
        response.getWriter().print(dataGritJson);
    }

    /**
     * 根据id获取教师的名称
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getTeacherById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teaId = request.getParameter("teaId");
        Teacher course = new TeacherServiceImpl().getTeacherById(Integer.valueOf(teaId));
        String courseName = course.getTeaRealName();
        response.getWriter().print(courseName);
    }


    /**
     * 添加老师信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Teacher对象的属性相同)
        Teacher teacher = WebRequestUtil.parseObject(Teacher.class, request);
        // 创建TeacherServiceImpl实例
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 调用添加的方法
        int add = teacherService.teacherAdd(teacher);
        if (add>0){
            InsertUser.insertTeacher(teacher,null);
        }
        // 响应结果
        response.getWriter().print( add > 0 ? "OK" : "NO");
    }

    /**
     * 修改老师信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        Teacher teacher = WebRequestUtil.parseObject(Teacher.class, request);
        // 创建TeacherServiceImpl实例
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 调用方法实现修改教师信息
        int update = teacherService.teacherUpdate(teacher);
        // 判断  响应相应的结果
        response.getWriter().print( update > 0 ? "OK" : "NO");

    }

    /**
     * 删除老师信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求删除的id
        AdminService adminService = new AdminServiceImpl();
        String teaId = request.getParameter("teaId");
        // 用逗号分隔id数组
        String[] tea = teaId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        for (String sd : tea) {
        	Integer tId = Integer.valueOf(sd);
        	// 通过教师id获取教师对象
            Teacher teacher = new Teacher(tId);
            // 通过教师id查询用户对象
            User user = new UserServiceImpl().getUserByUserId(tId);
            // 通过用户对象获取用户id
            Integer userId = user.getUserId();
            // 通过用户id获取角色对象
            UserRole userRole = new UserRole(userId);
            // 调用方法删除用户（非永久删除）
            adminService.delete(userRole);
            // 调用方法删除用户（非永久删除）
            adminService.delete(user);
            // 调用方法删除用户（非永久删除）
            boolean delete = adminService.delete(teacher);
            if(delete){
                row++;
            }
        }
        // 判断是否成功
        if (row == tea.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        } else {
            response.getWriter().print("NK");
        }
    }


    /**
     * 获取老师与课程的关系信息
     */
    public void showTeacherScoure(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        // 获取要查询的科目名称
        String teacherCourseName = request.getParameter("teacherCourseName");
        // 构建查询条件
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 创建对象实例
        AdminService adminService = new AdminServiceImpl();
        List<Course> courses = new ArrayList<>();
        List<TeacherCourse> teacherCourses = new ArrayList<>();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        // 判断查询条件是否非空
        if (teacherCourseName != null && !"".equals(teacherCourseName)) {
            // 构建模糊查询条件
            condition.put("course_name", new SelectItem(teacherCourseName, SelectItem.LikeSelect.CONTAIN));
            // 调用方法查询信息
            courses = adminService.getTypeByCondition(Course.class, condition);
        }

        // 遍历查询信息
        for (Course course : courses) {
            // 清除条件查询的集合
            condition.clear();
            // 构建查询条件
            condition.put("course_id", new SelectItem(course.getCourseId()));
            // 调用方法查询
            List<TeacherCourse> list = adminService.getTypeByCondition(TeacherCourse.class, condition);
            // 判断是否非空
            if (CollectionUtils.isNotBlank(list)) {
                teacherCourses.addAll(list);
            }
        }

        List<UserCourse> userCourses = new ArrayList<>();
        CourseService courseService = new CourseServiceImpl();
        // 判断是否有查询集合条件
        if (condition.size() == 0) {
            // 按分页查询
            teacherCourses = adminService.getTypesForPage(TeacherCourse.class, page, pageSize);
        }
        int begin = (page - 1) * pageSize;
        int end = begin + pageSize;
        end = end > teacherCourses.size() ? teacherCourses.size() : end;
        for (int i = (page - 1) * pageSize; i < end; i++) {
//        for (TeacherCourse teacherCourse :teacherCourses){
            TeacherCourse teacherCourse = teacherCourses.get(i);
            UserCourse userCourse = new UserCourse();
            Integer courseId = teacherCourse.getCourseId();
            Integer teaId = teacherCourse.getTeaId();
            Course course = courseService.getCourseByID(courseId);
            Teacher teacherById = teacherService.getTeacherById(teaId);
            userCourse.setCourseId(courseId);
            userCourse.setTeaId(teaId);
            userCourse.setCourseName(course.getCourseName());
            userCourse.setTeaName(teacherById.getTeaRealName());
            userCourse.setTeaCourseId(teacherCourse.getTeaCourseId());
            userCourses.add(userCourse);
        }

        String json = CollectionUtils.creatDataGritJson(userCourses, teacherCourses.size());
        response.getWriter().print(json);

    }


    /**
     * 导入excel教师信息的方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void importExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建文件项工厂类对象
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(1024 * 1024 * 10);
        fileItemFactory.setRepository(new File("C:/temp"));
        // 上传的解析器对象
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        try {
            // 获取文件项对象
            List<FileItem> list = upload.parseRequest(request);
            if (CollectionUtils.isNotBlank(list)) {
                FileItem item = list.get(0);
                String name = item.getName();
                InputStream in = item.getInputStream();
                // 调用导入的方法
                String result = TeacherExcelUtils.importFile(name, in, Teacher.class);
                // 响应页面
                response.getWriter().print(result);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除教师课程信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建TeacherServiceImpl实例
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 获取请求删除的id
        String courseId = request.getParameter("courseId");
        // 用逗号分隔id数组
        String[] tea = courseId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        for (String sd : tea) {
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setTeaCourseId(Integer.valueOf(sd));
            // 调用方法实现
            row += teacherService.deleteTeacherCourse(teacherCourse);

        }
        // 判断是否成功
        if (row == tea.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        }
    }

    /**
     * 添加教师课程信息
     */
    public void addTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Teacher对象的属性相同)
        TeacherCourse teacherCourse = WebRequestUtil.parseObject(TeacherCourse.class, request);
        // 创建TeacherServiceImpl实例
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 响应结果
        response.getWriter().print(teacherService.addTeacherCourse(teacherCourse) > 0 ? "OK" : "NO");
    }

    /**
     * 修改教师课程信息
     */
    public void updateTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类的封装参数
        TeacherCourse teacherCourse = WebRequestUtil.parseObject(TeacherCourse.class, request);
        // 创建TeacherServiceImpl实例
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        // 判断  响应相应的结果
        response.getWriter().print(teacherService.updateTeacherCourse(teacherCourse) > 0 ? "OK" : "NO");

    }


    public void getCourseJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminServiceImpl();
        List<Map<String, Object>> courseList = new ArrayList<>();
        List<Course> courses = adminService.getTypeItems(Course.class);
        for (Course course : courses) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", course.getCourseId());
            map.put("name", course.getCourseName());
            courseList.add(map);
        }
        response.getWriter().print(JSON.toJSONString(courseList));
    }

    public void getTeacherCJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminServiceImpl();
        List<Map<String, Object>> courseList = new ArrayList<>();
        List<Teacher> teachers = adminService.getTypeItems(Teacher.class);
        for (Teacher teacher : teachers) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", teacher.getTeaId());
            map.put("name", teacher.getTeaRealName());
            courseList.add(map);
        }
        response.getWriter().print(JSON.toJSONString(courseList));
    }

}
