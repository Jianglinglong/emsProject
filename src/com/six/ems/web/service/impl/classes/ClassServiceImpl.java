package com.six.ems.web.service.impl.classes;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.*;
import com.six.ems.entity.utils.StuClassView;
import com.six.ems.entity.utils.TeaClassView;
import com.six.ems.entity.utils.UserClassView;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.StringUtils;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.classes.ClassService;
import com.six.ems.web.service.interfaces.users.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassServiceImpl implements ClassService {
    private AdminService adminService = new AdminServiceImpl();

    @Override
    public List<Classes> getClassByStuName(String stuName) {
        Student student = new Student(stuName);
        Classes classes = new Classes();
        List<Classes> classesList = new ArrayList<>();
        List<Student> students = adminService.getTypeItems(student);
        if (CollectionUtils.isNotBlank(students)) {
            for (Student stu : students) {
                Integer stuId = stu.getStuId();
                classes.setClassId(stuId);
                List<Classes> items = adminService.getTypeItems(classes);
                if (CollectionUtils.isNotBlank(items)) {
                    if (classesList.containsAll(items)) {
                        continue;
                    }
                    classesList.addAll(items);
                }
            }
        }
        return classesList;
    }

    @Override
    public List<Classes> getClassByStuId(Integer stuId) {
        return null;
    }

    @Override
    public List<Classes> getClassByTeaName(String teaName) {
        return null;
    }

    @Override
    public List<Classes> getClassByTeaId(Integer teaId) {
        return null;
    }

    @Override
    public String getClassByStuName(String stuName, String stuClassName, Integer page, Integer pageSize){
        List<UserClass> stuClassList = new ArrayList<>();
        List<UserClass> stuClass = new ArrayList<>();
        List<UserClassView> stuClassViews = new ArrayList<>();
        List<Student> students = null;
        List<Classes> classesList = null;
        UserClass userClass = new UserClass();
        if (StringUtils.isNotEmpty(stuName)){
            Student student = new Student();
            student.setStuRealName(stuName);
            students = adminService.getTypeItems(student);
        }
        if (StringUtils.isNotEmpty(stuClassName)){
            Classes classes = new Classes(stuClassName);
            classesList = adminService.getTypeItems(classes);
        }
        if (students == null && classesList ==null){
            Map<String, SelectItem> condition = CollectionUtils.getCondition();
            condition.put("tea_id",new SelectItem(0, SelectItem.LikeSelect.NO));
            stuClassList = adminService.getTypeByCondition(UserClass.class,condition);
            students = adminService.getTypeItems(Student.class);
            classesList = adminService.getTypeItems(Classes.class);
        }else if (classesList!=null){
            for (Classes classes : classesList){
                userClass.setClassId(classes.getClassId());
                List<UserClass> userClasses = adminService.getTypeItems(userClass);
                if (CollectionUtils.isNotBlank(userClasses)){
                    stuClassList.addAll(userClasses);
                }
            }
            students = adminService.getTypeItems(Student.class);
        }else if (students!=null){
            for (Student student : students){
                userClass.setStuId(student.getStuId());
                List<UserClass> userClasses = adminService.getTypeItems(userClass);
                if (CollectionUtils.isNotBlank(userClasses)){
                    stuClassList.addAll(userClasses);
                }
            }
            classesList = adminService.getTypeItems(Classes.class);
        }
        int size = stuClassList.size();
        int begin = (page-1)*pageSize;
        int end = begin + pageSize;
        stuClass = stuClassList.subList(begin,end>size?size:end);
        Map<Integer, String> parseClass = CollectionUtils.parseClass(classesList);
        Map<Integer, String> stu = CollectionUtils.parseStudent(students);
        List<UserClassView> userClassViews = parensUserClassView(stuClass, stu, parseClass);
        return CollectionUtils.creatDataGritJson(userClassViews,size);
    }
    private List<UserClassView> parensUserClassView(List<UserClass> userClassList,Map<Integer, String> user,Map<Integer, String> classes){
        ArrayList<UserClassView> userClassViews = new ArrayList<>();
        if (CollectionUtils.isNotBlank(userClassList)){
            for(UserClass userClass : userClassList){
                UserClassView userClassView = new UserClassView();
                userClassView.setClassId(userClass.getClassId());
                userClassView.setStuId(userClass.getStuId());
                userClassView.setTeaId(userClass.getTeaId());
                userClassView.setUserClassId(userClass.getUserClassId());
                userClassView.setStuName(user.get(userClass.getStuId()));
                userClassView.setTeaName(user.get(userClass.getTeaId()));
                userClassView.setClassName(classes.get(userClass.getClassId()));
                userClassViews.add(userClassView);
            }
        }
        return userClassViews;
    }

    @Override
    public String getClassByTeaName(String teaName, String teaClassName, Integer page, Integer pageSize) {
        List<UserClass> teaClassList = new ArrayList<>();
        List<UserClass> teaClass = new ArrayList<>();
        List<UserClassView> teaClassViews = new ArrayList<>();
        List<Teacher> teachers = null;
        List<Classes> classesList = null;
        UserClass userClass = new UserClass();
        if (StringUtils.isNotEmpty(teaName)){
            Teacher teacher = new Teacher();
            teacher.setTeaRealName(teaName);
            teachers = adminService.getTypeItems(teacher);
        }
        if (StringUtils.isNotEmpty(teaClassName)){
            Classes classes = new Classes(teaClassName);
            classesList = adminService.getTypeItems(classes);
        }
        if (teachers == null && classesList ==null){
            Map<String, SelectItem> condition = CollectionUtils.getCondition();
            condition.put("stu_id",new SelectItem(0, SelectItem.LikeSelect.NO));
            teaClassList = adminService.getTypeByCondition(UserClass.class,condition);
            teachers = adminService.getTypeItems(Teacher.class);
            classesList = adminService.getTypeItems(Classes.class);
        }else if (classesList!=null){
            for (Classes classes : classesList){
                userClass.setClassId(classes.getClassId());
                List<UserClass> userClasses = adminService.getTypeItems(userClass);
                if (CollectionUtils.isNotBlank(userClasses)){
                    teaClassList.addAll(userClasses);
                }
            }
            teachers = adminService.getTypeItems(Teacher.class);
        }else if (teachers!=null){
            for (Teacher teacher : teachers){
                userClass.setStuId(teacher.getTeaId());
                List<UserClass> userClasses = adminService.getTypeItems(userClass);
                if (CollectionUtils.isNotBlank(userClasses)){
                    teaClassList.addAll(userClasses);
                }
            }
            classesList = adminService.getTypeItems(Classes.class);
        }
        int size = teaClassList.size();
        int begin = (page-1)*pageSize;
        int end = begin + pageSize;
        teaClass = teaClassList.subList(begin,end>size?size:end);
        Map<Integer, String> parseClass = CollectionUtils.parseClass(classesList);
        Map<Integer, String> tea = CollectionUtils.parseTeacher(teachers);
        List<UserClassView> userClassViews = parensUserClassView(teaClass, tea, parseClass);
        return CollectionUtils.creatDataGritJson(userClassViews,size);
    }

    private void addStudentClass(AdminService adminService, StudentClass studentClass, List<StudentClass> all) {
        List<StudentClass> typeItems = adminService.getTypeItems(studentClass);
        if (typeItems != null) {
            all.addAll(typeItems);
        }
    }
    private void addTeacherClass(AdminService adminService, TeacherClass teacherClass, List<TeacherClass> all) {
        List<TeacherClass> typeItems = adminService.getTypeItems(teacherClass);
        if (typeItems != null) {
            all.addAll(typeItems);
        }
    }

    private List<StuClassView> parseStuView(List<StudentClass> studentClassList) {
        List<StuClassView> stuClassViews = new ArrayList<>();
        Student student = new Student();
        Classes classes = new Classes();
        for (StudentClass studentClass : studentClassList) {
            StuClassView stuClassView = new StuClassView();
            stuClassView.setClassId(studentClass.getClassId());
            stuClassView.setStuId(studentClass.getStuId());
            stuClassView.setStuClassId(studentClass.getStuClassId());
            student.setStuId(studentClass.getStuId());
            List<Student> students = adminService.getTypeItems(student);
            stuClassView.setStuName(students.get(0).getStuRealName());
            classes.setClassId(studentClass.getClassId());
            List<Classes> classesList = adminService.getTypeItems(classes);
            stuClassView.setClassName(classesList.get(0).getClassName());
            stuClassViews.add(stuClassView);
        }
        return stuClassViews;
    }
    private List<TeaClassView> parseTeaView(List<TeacherClass> teacherClasses) {
        List<TeaClassView> teaClassViews = new ArrayList<>();
        Teacher teacher = new Teacher();
        Classes classes = new Classes();
        for (TeacherClass teacherClass : teacherClasses) {
            TeaClassView teaClassView = new TeaClassView();
            teaClassView.setClassId(teacherClass.getClassId());
            teaClassView.setTeaId(teacherClass.getTeaId());
            teaClassView.setTeaClassId(teacherClass.getTeaClassId());
            teacher.setTeaId(teacherClass.getTeaId());
            List<Teacher> students = adminService.getTypeItems(teacher);
            teaClassView.setTeaName(students.get(0).getTeaRealName());
            classes.setClassId(teacherClass.getClassId());
            List<Classes> classesList = adminService.getTypeItems(classes);
            teaClassView.setClassName(classesList.get(0).getClassName());
            teaClassViews.add(teaClassView);
        }
        return teaClassViews;
    }
}
