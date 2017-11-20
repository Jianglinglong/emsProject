package com.six.ems.utils;

import java.util.HashMap;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.*;

/**
 * 学生赋权工具类
 *
 * @author Direct
 */
public class InsertUser {

    /**
     * 学生赋权工具类
     *
     * @param stu 传入学生对象
     * @return 返回flag
     */
    public static int InserttStudents(Student stu , Integer stuClassId) {
        int flag = 0;
        String stuAccount = stu.getStuAccount();
        // 构建查询条件
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("stu_account", new SelectItem(stuAccount));
        // 通过账号获取对象
        Student student = AdvanceUtil.select(Student.class, condition).get(0);
        // 通过学生对象获取学生id
        Integer stuId = student.getStuId();
        // 创建用户对象
        User user = new User();
        // 设置学生id
        user.setStuId(stuId);
        // 调用添加用户信息方法 添加学生id
        user.setTeaId(0);
        flag = AdvanceUtil.insert(user);
        // 构建查询条件
//        Map<String, SelectItem> condition2 = new HashMap<String, SelectItem>();
        condition.clear();
        condition.put("stu_id", new SelectItem(stuId));
        // 通过学生id 查询用户id
        User us = AdvanceUtil.select(User.class, condition).get(0);
        // 获取学生id
        Integer userId = us.getUserId();
        // 创建用户角色对象
        UserRole userRole = new UserRole();
        // 设置用户角色id
        userRole.setUserId(userId);
        // 设置权限为学生权限 3-学生 2-教师 1-管理员
        userRole.setRoleId(3);
        // 添加用户角色
        flag = AdvanceUtil.insert(userRole);
        if (stuClassId!=null) {
            UserClass userClass = new UserClass();
            userClass.setStuId(stuId);
            userClass.setClassId(stuClassId);
            userClass.setTeaId(0);
            AdvanceUtil.insert(userClass);
        }
        return flag;
    }

    /**
     * 教师添加权限工具类
     *
     * @param tea 传入教师对象
     * @return 返回flag
     */

    public static int insertTeacher(Teacher tea,Integer teacherClassId) {
        int flag = 0;
        // 获取教师对象账号
        String teaAccount = tea.getTeaAccount();
        // 构建查询条件
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("tea_account", new SelectItem(teaAccount));
        // 通过账号获取对象
        Teacher teacher = AdvanceUtil.select(Teacher.class, condition).get(0);
        // 通过教师对象获取教师id
        Integer teaId = teacher.getTeaId();
        // 创建用户对象
        User user = new User();
        // 设置教师id
        user.setTeaId(teaId);
        user.setStuId(0);
        // 调用添加用户信息方法 添加教师id
        flag = AdvanceUtil.insert(user);
        // 构建查询条件
//        Map<String, SelectItem> condition2 = new HashMap<String, SelectItem>();
        condition.clear();
        condition.put("tea_id", new SelectItem(teaId));
        // 通过教师id 获取用户对象
        User us = AdvanceUtil.select(User.class, condition).get(0);
        // 获取用户id
        Integer userId = us.getUserId();
        // 创建用户角色对象
        UserRole userRole = new UserRole();
        // 设置用户角色id
        userRole.setUserId(userId);
        // 设置权限为学生权限 3-学生 2-教师 1-管理员
        userRole.setRoleId(2);
        // 添加用户角色
        flag = AdvanceUtil.insert(userRole);
        if(teacherClassId!=null) {
            UserClass userClass = new UserClass();
            userClass.setTeaId(teaId);
            userClass.setClassId(teacherClassId);
            userClass.setStuId(0);
            AdvanceUtil.insert(userClass);
        }
        return flag;
    }
}
