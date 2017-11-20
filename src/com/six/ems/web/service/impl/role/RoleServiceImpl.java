package com.six.ems.web.service.impl.role;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Role;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.roles.RoleService;
import com.six.ems.web.service.interfaces.users.AdminService;

import java.util.List;
import java.util.Map;

public class RoleServiceImpl implements RoleService {
    private AdminService adminService = new AdminServiceImpl();
    @Override
    public List<UserRole> getUserRolesByUserID(Integer userID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("user_id",new SelectItem(userID));
        return adminService.getTypeByCondition(UserRole.class, condition);
    }

    @Override
    public User getUserByStuID(Integer stuID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("stu_id",new SelectItem(stuID));
        List<User> users = adminService.getTypeByCondition(User.class, condition);
        return CollectionUtils.isNotBlank(users)?users.get(0):null;
    }

    @Override
    public User getUserByTeaID(Integer teaID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("tea_id",new SelectItem(teaID));
        List<User> users = adminService.getTypeByCondition(User.class, condition);
        return CollectionUtils.isNotBlank(users)?users.get(0):null;
    }

    @Override
    public List<Role> getRoles() {
        return adminService.getTypeItems(Role.class);
    }

    @Override
    public Role getRoleByRoleID(Integer roleID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("role_id",new SelectItem(roleID));
        List<Role> roles = adminService.getTypeByCondition(Role.class, condition);
        return CollectionUtils.isNotBlank(roles)?roles.get(0):null;
    }
}
