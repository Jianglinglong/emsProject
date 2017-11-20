package com.six.ems.dao.impl.users;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.users.UserDAO;
import com.six.ems.entity.tables.Right;
import com.six.ems.entity.tables.RoleRight;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;
import com.six.ems.utils.CollectionUtils;

public class UserDAOImpl implements UserDAO {

    @Override
    public Teacher teaLogin(Teacher tea) {
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("tea_account", new SelectItem(tea.getTeaAccount()));
        condition.put("tea_password", new SelectItem(tea.getTeaPassword()));
        condition.put("available",new SelectItem(1));
        List<Teacher> query = AdvanceUtil.select(Teacher.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public Student stuLogin(Student stu) {
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("stu_account", new SelectItem(stu.getStuAccount()));
        condition.put("stu_password", new SelectItem(stu.getStuPassword()));
        condition.put("available",new SelectItem(1));
        List<Student> query = AdvanceUtil.select(Student.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public User getUserIdBytea(Teacher tea) {
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("tea_id", new SelectItem(tea.getTeaId()));
        condition.put("available",new SelectItem(1));
        List<User> query = AdvanceUtil.select(User.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public User getUserIdBystu(Student stu) {
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("stu_id", new SelectItem(stu.getStuId()));
        condition.put("available",new SelectItem(1));

        List<User> query = AdvanceUtil.select(User.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public List<UserRole> getRoleByUserId(Integer userId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("user_id", new SelectItem(userId));
        condition.put("available",new SelectItem(1));
        List<UserRole> query = AdvanceUtil.select(UserRole.class, condition);

        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Set<Integer> getRightIdByRoleId(Integer roleId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("role_id", new SelectItem(roleId));
        condition.put("available",new SelectItem(1));
        List<RoleRight> query = AdvanceUtil.select(RoleRight.class, condition);
        Set<Integer> rightIds = new HashSet<>();
        if (CollectionUtils.isNotBlank(query)) {
            for (RoleRight roleRight : query) {
                rightIds.add(roleRight.getRightId());
            }
        }


        return CollectionUtils.isNotBlank(rightIds) ? rightIds : null;
    }

    @Override
    public List<Right> getRightListByRightId(Integer rightId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("right_id", new SelectItem(rightId));
        condition.put("available",new SelectItem(1));
        List<Right> query = AdvanceUtil.select(Right.class, condition);


        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    /**
     * 根据用户id查询用户信息
     */
    public User getUserByUserId(Integer userId) {
        // 构建条件
        Map<String, SelectItem> condition = new HashMap<String, SelectItem>();
        condition.put("user_id", new SelectItem(userId));
        condition.put("available",new SelectItem(1));
        List<User> list = AdvanceUtil.select(User.class, condition);
        return CollectionUtils.isNotBlank(list) ? list.get(0) : null;
    }

}
