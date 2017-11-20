package com.six.ems.web.service.interfaces.roles;

import com.six.ems.entity.tables.Role;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;

import java.util.List;

public interface  RoleService {
     List<UserRole> getUserRolesByUserID(Integer userID);

     User getUserByStuID(Integer stuID);

     User getUserByTeaID(Integer teaID);

     List<Role> getRoles();

     Role getRoleByRoleID(Integer roleID);
}
