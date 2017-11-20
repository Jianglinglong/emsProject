package com.six.ems.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.six.ems.entity.tables.RoleRight;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.users.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@WebServlet(name = "admin.do")
public class AdminServlet extends BaseServlet {
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RoleRight> roleRights = new AdminServiceImpl().getTypeItems(RoleRight.class);
        Map<String, Object> rows = new HashMap<>();
        rows.put("total", roleRights.size());
        rows.put("rows", roleRights);
        response.getWriter().print(JSON.toJSONString(rows, true));
    }

    public void updateRightsByRoleID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId = request.getParameter("roleId");
        String rights = request.getParameter("rights");
        AdminServiceImpl adminService = new AdminServiceImpl();
        String msg = "OK";
        //获取初始权限列表
        List<RoleRight> rightsByRoleID = adminService.getRightsByRoleID(Integer.valueOf(roleId));
        //创建临时角色权限对象
        RoleRight roleRight = new RoleRight();
        //设置角色
        roleRight.setRoleId(Integer.valueOf(roleId));
        //删除角色原有权限
        boolean delete = true;
        for (RoleRight r : rightsByRoleID){
           delete = adminService.delete(r);
           if (!delete){
               msg = "NO";
               break;
           }
        }
//        boolean deleteRightByRoleID = adminService.deleteRightByRoleID(Integer.valueOf(roleId));
        if (delete) {
            //如果删除成功且具有新权限
            if (rights != null && rights.length() > 0) {
                //获取新权限ID列表
                String[] rightArray = rights.split(",");
                for (String rightID : rightArray) {
                    //给临时角色权限对象绑定新的权限ID
                    roleRight.setRightId(Integer.valueOf(rightID));
                    //赋予新的权限
                    boolean add = adminService.add(roleRight);
                    if (!add) {
                        msg = "NO";
                        break;
                    }
                }
            }
        }
        //响应页面
        response.getWriter().print(msg);
    }
}
