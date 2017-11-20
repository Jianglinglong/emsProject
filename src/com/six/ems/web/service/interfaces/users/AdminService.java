package com.six.ems.web.service.interfaces.users;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.RoleRight;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface AdminService {
    <T> boolean add(T t);
    <T> boolean update(T t);
    <T> boolean delete(T t);
    boolean deleteRightByRoleID(Integer roleID);
    <T>List<T> getTypeItems(Class<T> tClass);
    <T>List<T> getTypeItems(T t);
    <T>List<T> getTypeByCondition(Class<T> tClass, Map<String, SelectItem> condition);
    <T>List<T> getTypesForPage(Class<T> tClass, Integer page, Integer pageSiz);
    <T>List<T> getTypesForPage(Class<T> tClass, Map<String, SelectItem> condition, Integer page, Integer pageSiz);
    List<RoleRight> getRightsByRoleID(Integer roleID);
//    String parenseRightJson(List<Right> allRights,List<RoleRight> roleRights);
    String getRoleRightsByRoleID(Integer roleID);
}
