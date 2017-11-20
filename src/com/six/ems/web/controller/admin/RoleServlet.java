package com.six.ems.web.controller.admin;

import com.alibaba.fastjson.JSON;

import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.*;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.role.RoleServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.StudentServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.interfaces.roles.RoleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleServlet extends BaseServlet {
    public String initRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/WEB-INF/jsp/admin/role.jsp";
    }

    public void getAllRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = AdvanceUtil.select(Role.class);
        Map<String, Object> rows = new HashMap<>();
        rows.put("total", roles.size());
        rows.put("rows", roles);
        response.getWriter().print(JSON.toJSONString(rows, true));
    }

    public void getRightsByRoleID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId = request.getParameter("roleId");
        AdminServiceImpl adminService = new AdminServiceImpl();
        String roleRightsByRoleID = adminService.getRoleRightsByRoleID(Integer.valueOf(roleId));
        response.getWriter().print(roleRightsByRoleID);
    }

    public void addRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        Role role = new Role(roleName);
        boolean add = new AdminServiceImpl().add(role);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void updateRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String roleId = request.getParameter("roleId");
        Role role = new Role(roleName);
        role.setRoleId(Integer.valueOf(roleId));
        boolean add = new AdminServiceImpl().update(role);
        response.getWriter().print(add ? "OK" : "NO");
    }

    public void deleteRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roelIDs = request.getParameter("roelID");
        boolean delete = true;
        AdminServiceImpl adminService = new AdminServiceImpl();
        if (roelIDs != null) {
            String[] split = roelIDs.split(",");
            for (String roleID : split) {
                delete = adminService.delete(new Role(Integer.valueOf(roleID)));
                if (!delete) {
                    break;
                }
            }
        }
        response.getWriter().print(delete ? "OK" : "NO");
    }

    public String initGrantTeaRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teaId = request.getParameter("teaId");
        RoleService roleService = new RoleServiceImpl();
        List<Role> oldRole = new ArrayList<>();
        List<Role> roleArrayList = roleService.getRoles();
        User userByTeaID = new User();

        Teacher teacher = new TeacherServiceImpl().getTeacherById(Integer.valueOf(teaId));
        if (teacher != null) {
            userByTeaID = roleService.getUserByTeaID(teacher.getTeaId());
            if (userByTeaID != null) {
                List<UserRole> userRoles = roleService.getUserRolesByUserID(userByTeaID.getUserId());
                for (UserRole userRole : userRoles) {
                    oldRole.add(roleService.getRoleByRoleID(userRole.getRoleId()));
                }
            }else {
                userByTeaID = new User();
                userByTeaID.setTeaId(Integer.valueOf(teaId));
                new AdminServiceImpl().add(userByTeaID);
                userByTeaID = roleService.getUserByTeaID(userByTeaID.getTeaId());
            }
        }
        request.setAttribute("allRole", roleArrayList);
        request.setAttribute("oldRole", oldRole);
        request.setAttribute("userId", userByTeaID.getUserId());
        return "/WEB-INF/jsp/admin/grantRole.jsp";
    }

    public String initGrantStuRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuId = request.getParameter("stuId");
        RoleService roleService = new RoleServiceImpl();
        List<Role> oldRole = new ArrayList<>();
        List<Role> roleArrayList = roleService.getRoles();
        User user = new User();
        Student student = new StudentServiceImpl().getStudentById(Integer.valueOf(stuId));
        if (student != null) {
            user = roleService.getUserByStuID(student.getStuId());
            if (user != null) {
                List<UserRole> userRoles = roleService.getUserRolesByUserID(user.getUserId());
                for (UserRole userRole : userRoles) {
                    oldRole.add(roleService.getRoleByRoleID(userRole.getRoleId()));
                }
            }else {
                user = new User();
                user.setStuId(Integer.valueOf(stuId));
                new AdminServiceImpl().add(user);
                user = roleService.getUserByStuID(user.getStuId());
            }
        }
        request.setAttribute("allRole", roleArrayList);
        request.setAttribute("oldRole", oldRole);
        request.setAttribute("userId", user.getUserId());
        return "/WEB-INF/jsp/admin/grantRole.jsp";
    }

    public void updateRoleForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String[] roles = request.getParameterValues("roles");
        boolean add = true;
        AdminServiceImpl adminService = new AdminServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        Integer userID = null;
        if (userId!=null && !"".equals(userId)){
            userID = Integer.valueOf(userId);
        }
        List<UserRole> userRoles = roleService.getUserRolesByUserID(userID);
        for(UserRole userRole : userRoles){
            adminService.delete(userRole);
        }
        if (roles != null && roles.length > 0) {
            for (String role : roles) {
                UserRole userRole = new UserRole(userID, Integer.valueOf(role));
                add = adminService.add(userRole);
                if (!add) {
                    break;
                }
            }
        }
        response.getWriter().print(add ? "OK" : "NO");
    }
}
