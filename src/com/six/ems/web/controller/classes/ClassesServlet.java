package com.six.ems.web.controller.classes;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.*;
import com.six.ems.entity.utils.ClassCourseView;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.StringUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.ExamServiceImpl;
import com.six.ems.web.service.impl.classes.ClassServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassesServlet extends BaseServlet {

    /**
     * 班级操作
     */
    public String intiClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/classes/classes.jsp";
    }

    public void getClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String className = request.getParameter("className");
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
//        if (className == null || "".equals(className)) {
//            className = null;
//        }
//        Classes classes = new Classes(className);
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("class_name", new SelectItem(className, SelectItem.LikeSelect.CONTAIN));
        List<Classes> classesList = new AdminServiceImpl().getTypeByCondition(Classes.class, condition);
        String json = null;
        if (CollectionUtils.isNotBlank(classesList)) {
            Integer begin = (page - 1) * pageSize;
            Integer end = begin + pageSize;
            int size = classesList.size();
            List<Classes> subList = classesList.subList(begin, end > size ? size : end);
            json = CollectionUtils.creatDataGritJson(subList, size);
        }
        response.getWriter().print(json);
    }


    public void deleteClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("classId");
        AdminService adminService = new AdminServiceImpl();
        String result = "OK";

        if (classId != null && classId.length() > 0) {
            Classes classes = new Classes();
            UserClass userClass = new UserClass();
            String[] strings = classId.split(",");
            for (String string : strings) {
                Integer id = Integer.valueOf(string);
                classes.setClassId(id);
                userClass.setClassId(id);
                boolean delete = adminService.delete(classes);
                delete = adminService.delete(userClass);
                if (!delete) {
                    result = "NO";
                    break;
                }
            }
        }
        response.getWriter().print(result);
    }

    public void addClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String className = request.getParameter("className");
        Classes classes = new Classes(className);
        boolean add = new AdminServiceImpl().add(classes);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void updateClasses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Classes classes = WebRequestUtil.parseObject(Classes.class, request);
        boolean update = new AdminServiceImpl().update(classes);
        response.getWriter().print(update ? "OK" : "NO");
    }

    /**
     * 班级学生成员操作
     */
    public String initStuClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/classes/stuClass.jsp";
    }

    public void addStuClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserClass userClass = WebRequestUtil.parseObject(UserClass.class, request);
        userClass.setTeaId(0);
        AdminService adminService = new AdminServiceImpl();
//        boolean add = adminService.add(userClass);
//        UserClass userClass = new UserClass(userClass.getStuId(),0,userClass.getClassId());
        boolean add = adminService.add(userClass);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void updateStuClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserClass studentClass = WebRequestUtil.parseObject(UserClass.class, request);
        boolean update = new AdminServiceImpl().update(studentClass);
        response.getWriter().print(update ? "OK" : "NO");
    }

    public void deleteUserClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuClassId = request.getParameter("userClassId");
        AdminService adminService = new AdminServiceImpl();
        String result = "NO";
        boolean deleteUserClass = deleteUserClass(stuClassId, adminService);
        if (deleteUserClass) {
            result = "OK";
        }
        response.getWriter().print(result);
    }


    public void getStuClassInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuName = request.getParameter("stuName");
        String stuClassName = request.getParameter("stuClassName");
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        String json = new ClassServiceImpl().getClassByStuName(stuName, stuClassName, page, pageSize);
        response.getWriter().print(json);
    }

    /**
     * 班级教师成员操作
     */

    public String initTeaClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/classes/teaClass.jsp";
    }

    public void addTeaClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserClass userClass = WebRequestUtil.parseObject(UserClass.class, request);
        AdminService adminService = new AdminServiceImpl();
        userClass.setStuId(0);
//        boolean add = adminService.add(teacherClass);
        boolean add = adminService.add(userClass);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void deleteTeaClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teaClassId = request.getParameter("teaClassId");
        AdminService adminService = new AdminServiceImpl();
        String result = "NO";
        boolean deleteUserClass = deleteUserClass(teaClassId, adminService);
        if (deleteUserClass) {
            result = "OK";
        }
        response.getWriter().print(result);
    }

    private boolean deleteUserClass(String userClassIds, AdminService adminService) {
        UserClass userClass = new UserClass();
        if (null != userClassIds) {
            String[] strings = userClassIds.split(",");
            for (String string : strings) {
                Integer id = Integer.valueOf(string);
                userClass.setUserClassId(id);
                boolean delete = adminService.delete(userClass);
                if (!delete) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateTeaClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserClass teacherClass = WebRequestUtil.parseObject(UserClass.class, request);
        boolean update = new AdminServiceImpl().update(teacherClass);
        response.getWriter().print(update ? "OK" : "NO");
    }

    public void getTeaClassInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teaName = request.getParameter("teaName");
        String teaClassName = request.getParameter("teaClassName");
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        String json = new ClassServiceImpl().getClassByTeaName(teaName, teaClassName, page, pageSize);
        response.getWriter().print(json);
    }

    public void getClassJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Classes> classesList = new AdminServiceImpl().getTypeItems(Classes.class);
        List<Map<String, Object>> classJson = new ArrayList<>();

        if (CollectionUtils.isNotBlank(classesList)) {
            for (Classes classes : classesList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", classes.getClassId());
                map.put("name", classes.getClassName());
                classJson.add(map);
            }
        }
        response.getWriter().print(JSON.toJSONString(classJson));
    }

    /**
     * 根据班级id获取班级名
     */
    public void getClassByClassId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("classId");
        Classes Name = new ExamServiceImpl().getClassByClassId(Integer.valueOf(classId));
        String className = Name.getClassName();
        response.getWriter().print(className);
    }

    public String initCourseClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/classes/course.jsp";
    }

    public void getCouseClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Integer currentPage = Integer.valueOf(page);
        Integer rows = Integer.valueOf(pageSize);
        AdminService adminService = new AdminServiceImpl();
        String courseClassName = request.getParameter("courseClassName");
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        List<ClassCourseView> classCourseViews = new ArrayList<>();
        List<Classes> classesList = adminService.getTypeItems(Classes.class);
        List<Course> courseList = adminService.getTypeItems(Course.class);
        Map<Integer, String> course = CollectionUtils.parseCourse(courseList);
        int total = 0;
        if (StringUtils.isNotEmpty(courseClassName)) {
            condition.put("class_name", new SelectItem(courseClassName));
        }
        if (condition.size() != 0) {
            List<Classes> classes = adminService.getTypeByCondition(Classes.class, condition);
            Map<Integer, String> parseClass = CollectionUtils.parseClass(classes);
            condition.clear();
            if(classes!=null){
                List<ClassCourse> classCourseList = new ArrayList<>();
                for (Classes cla : classes){
                    condition.put("class_id",new SelectItem(cla.getClassId()));
                    List<ClassCourse> classCourses = adminService.getTypeByCondition(ClassCourse.class, condition);
                    if (classCourses!=null){
                        classCourseList.addAll(classCourses);
                    }
                }
                total = classCourseList.size();
                int begin = (currentPage-1)*rows;
                int end = begin + rows;
                begin = begin>total?0:begin;
                end = end>total?total:end;
                classCourseViews = getViews(classCourseList.subList(begin,end),parseClass,course);
            }

        } else {
            total = adminService.getTypeItems(ClassCourse.class).size();
            List<ClassCourse> classCourses = adminService.getTypesForPage(ClassCourse.class, currentPage, rows);
            Map<Integer, String> parseClass = CollectionUtils.parseClass(classesList);
            classCourseViews = getViews(classCourses,parseClass,course);
        }

        response.getWriter().print(CollectionUtils.creatDataGritJson(classCourseViews, total));
    }

    public void addCourseClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassCourse classCourse = WebRequestUtil.parseObject(ClassCourse.class, request);
        boolean add = new AdminServiceImpl().add(classCourse);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void updateCourseClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassCourse classCourse = WebRequestUtil.parseObject(ClassCourse.class, request);
        boolean add = new AdminServiceImpl().update(classCourse);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void deleteCourseClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        String paperID = request.getParameter("classCourseId");
        String[] paperIds = paperID.split(",");
        int rows = 0;
        for (String pId : paperIds) {
            Integer classCourseId = Integer.valueOf(pId);
            /*int row = new PaperServiceImpl().deletePaper(classCourseId);
            if (row > 0) {
                rows++;
            }*/
            boolean b = adminServiceImpl.delete(new ClassCourse(classCourseId));
            if (b) {
                rows++;
            }
        }
        if (rows == paperIds.length) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
    }
    private List<ClassCourseView> getViews(List<ClassCourse> classCourses, Map<Integer, String>classMap, Map<Integer, String>courseMap){
        List<ClassCourseView> classCourseViews = new ArrayList<>();
        if (classCourses != null) {
            for (ClassCourse classCourse : classCourses) {
                ClassCourseView classCourseView = new ClassCourseView(classCourse);
                classCourseView.setClassName(classMap.get(classCourse.getClassId()));
                classCourseView.setCourseName(courseMap.get(classCourse.getCourseId()));
                classCourseViews.add(classCourseView);
            }
        }
        return classCourseViews;
    }
}