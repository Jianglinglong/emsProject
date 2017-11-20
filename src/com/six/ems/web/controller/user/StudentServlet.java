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
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.StudentCourse;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;
import com.six.ems.entity.utils.UserCourse;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.StudentExcelUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.StudentServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.course.CourseService;
import com.six.ems.web.service.interfaces.users.AdminService;
import com.six.ems.web.service.interfaces.users.StudentService;

/**
 * 学生信息
 */
public class StudentServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
    }


    /**
     * 转发到student页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 转发到 WEB-INF/jsp/student/student.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/student/student.jsp").forward(request, response);
    }

    /**
     * 查询所有学生方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void queryStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取传入当前页和每页显示数
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String stuName = request.getParameter("stuName");
        String stuClass = request.getParameter("stuClass");
        // 创建对象
        AdminService adminService = new AdminServiceImpl();
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        //判断姓名是否非空
        if (stuName != null && !"".equals(stuName)) {
            condition.put("stu_real_name", new SelectItem(stuName, SelectItem.LikeSelect.CONTAIN));
        }
        //判断是否非空班级
        if (stuClass != null && !"".equals(stuClass)) {
            condition.put("stu_class", new SelectItem(stuClass, SelectItem.LikeSelect.CONTAIN));
        }
        // 调用方法查询
        List<Student> students = adminService.getTypesForPage(Student.class, condition, Integer.valueOf(page), Integer.valueOf(rows));
        //	获取信息总条数
        int size = adminService.getTypeItems(Student.class).size();
        // 调用方法查询
        String dataGritJson = CollectionUtils.creatDataGritJson(students, size);
//        System.out.println(dataGritJson);
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
    public void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
        Student student = WebRequestUtil.parseObject(Student.class, request);
        // 创建StudentServiceImpl实例对象
        StudentServiceImpl studentService = new StudentServiceImpl();
        int add = studentService.studentAdd(student);

        // 判断 响应结果
        if (add > 0) {
            InsertUser.InserttStudents(student,null);
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
        // 响应结果   courseService.courseAdd(course)(调用方法实现添加数据)
        //response.getWriter().print(studentService.studentAdd(student) > 0 ? "OK" : "NO");
    }

    /**
     * 修改学生信息方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类出入对象封装数据---注意(表单名必须与Student对象的属性相同)
        Student student = WebRequestUtil.parseObject(Student.class, request);
        // 创建StudentServiceImpl实例对象
        StudentServiceImpl studentService = new StudentServiceImpl();
        int update = studentService.studentUpdate(student);
        // 判断 响应结果
        if (update > 0) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
        // 响应结果   courseService.courseAdd(course)(调用方法实现修改数据)
        // response.getWriter().print(studentService.studentUpdate(student) > 0 ? "OK" : "NO");
    }

    /**
     * 新版删除学生方法
     */
    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建AdminService实例对象
        AdminService admin = new AdminServiceImpl();
        // 获取请求删除的id
        String stuId = request.getParameter("stuId");
        // 用逗号分隔id数组
        String[] stu = stuId.split(",");
        int row = 0;// 定义全局变量
        // 遍历id个数
        for (String sd : stu) {
            // 转为整型
            Integer sId = Integer.valueOf(sd);
            // 通过学生id查询用户id
            User user = new UserServiceImpl().getUserByUserId(sId);
            // 获取用户id
            Integer userId = user.getUserId();
            // 创建用户角色对象
            UserRole userRole = new UserRole(userId);
            // 调用工具类方法根据用户id删除用户角色对象
            row += AdvanceUtil.delete(userRole);
            // 调用工具类删除用户对象
            row += AdvanceUtil.delete(user);
            // 创建学生对象
            Student student = new Student(sId);
            // 调用方法删除学生(非永久删除)
            boolean delete = admin.delete(student);
            if (delete) {
                row++;
            }
        }
        // 判断是否成功
        if (row == stu.length) {
            response.getWriter().print("OK");
        } else if (row == 0) {
            response.getWriter().print("NO");
        } else {
            response.getWriter().print("NK");
        }
    }


    /**
     * 导入excel学生信息的方法
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
                String result = StudentExcelUtils.importFile(name, in, Student.class);
                // 响应页面
                response.getWriter().print(result);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }


    public String initStudentCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/student/studentCourse.jsp";
    }

    public void showstudentScoure(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        // 获取要查询的科目名称
        String studentCourseName = request.getParameter("studentCourseName");
        // 构建查询条件
        StudentService studentService = new StudentServiceImpl();
        // 创建对象实例
        AdminService adminService = new AdminServiceImpl();
        List<Course> courses = new ArrayList<>();
        List<StudentCourse> studentCourses = new ArrayList<>();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        // 判断查询条件是否非空
        if (studentCourseName != null && !"".equals(studentCourseName)) {
            // 构建模糊查询条件
            condition.put("course_name", new SelectItem(studentCourseName, SelectItem.LikeSelect.CONTAIN));
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
            List<StudentCourse> list = adminService.getTypeByCondition(StudentCourse.class, condition);
            // 判断是否非空
            if (CollectionUtils.isNotBlank(list)) {
                studentCourses.addAll(list);
            }
        }

        List<UserCourse> userCourses = new ArrayList<>();
        CourseService courseService = new CourseServiceImpl();
        // 判断是否有查询集合条件
        if (condition.size() == 0) {
            // 按分页查询
            studentCourses = adminService.getTypesForPage(StudentCourse.class, page, pageSize);
        }

        // 遍历
        for (int i = (page - 1) * pageSize; i < pageSize && i < studentCourses.size(); i++) {
            // for (StudentCourse studentCourse :studentCourses){
            StudentCourse studentCourse = studentCourses.get(i);
            UserCourse userCourse = new UserCourse();
            Integer courseId = studentCourse.getCourseId();
            Integer stuId = studentCourse.getStuId();
            Course course = courseService.getCourseByID(courseId);
            Student student = studentService.getStudentById(stuId);
            userCourse.setCourseId(courseId);
            userCourse.setStuId(stuId);
            userCourse.setCourseName(course.getCourseName());
            userCourse.setStuName(student.getStuRealName());
            userCourse.setStuCourseId(studentCourse.getStuCourseId());
            userCourses.add(userCourse);
        }

        String json = CollectionUtils.creatDataGritJson(userCourses, studentCourses.size());
        response.getWriter().print(json);
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


    public void getStudentJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminServiceImpl();
        List<Map<String, Object>> courseList = new ArrayList<>();
        List<Student> students = adminService.getTypeItems(Student.class);
        for (Student student : students) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", student.getStuId());
            map.put("name", student.getStuRealName());
            courseList.add(map);
        }
        response.getWriter().print(JSON.toJSONString(courseList));
    }

    public void updateStudentCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String stuId = request.getParameter("stuId");
        String stuCourseId = request.getParameter("stuCourseId");

        boolean update = new AdminServiceImpl().update(new StudentCourse(Integer.valueOf(stuCourseId), Integer.valueOf(stuId), Integer.valueOf(courseId)));

        response.getWriter().print(update ? "OK" : "NO");
    }

    public void addStudentCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String stuId = request.getParameter("stuId");

        boolean add = new AdminServiceImpl().add(new StudentCourse(Integer.valueOf(stuId), Integer.valueOf(courseId)));

        response.getWriter().print(add ? "OK" : "NO");
    }

    public void deleteStudentCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] stuCourseIds = request.getParameterValues("stuCourseId");
        AdminService adminService = new AdminServiceImpl();
        boolean delete = false;
        if (stuCourseIds != null) {
            for (String sutCourseId : stuCourseIds) {
                StudentCourse studentCourse = new StudentCourse(Integer.valueOf(sutCourseId));
                delete = adminService.delete(studentCourse);
                if (!delete) {
                    break;
                }
            }
        }

        response.getWriter().print(delete ? "OK" : "NO");
    }

    public void getStudentNameById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuId = request.getParameter("stuId");
        Student student = new StudentServiceImpl().getStudentById(Integer.valueOf(stuId));
        response.getWriter().print(student==null?"":student.getStuRealName());
    }
//    public String initPractice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        return "/WEB-INF/jsp/student/practice.jsp";
//    }




}
