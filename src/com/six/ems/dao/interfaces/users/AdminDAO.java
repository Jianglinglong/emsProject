package com.six.ems.dao.interfaces.users;

import com.jll.jdbc.base.SelectItem;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;


public interface AdminDAO {
    <T> boolean add(T t);
    <T> boolean update(T t);
    <T> boolean delete(T t);
    <T> List<T> getTypes(Class<T> tClass);
    <T> List<T> getTypes(T t);
//	Role getRoleByID(Integer roleID);
//	Right getRightByID(Integer rightID);
	<T> T getTypeByID(Class<T> tClass, Integer typeID);
	<T> List<T> getTypesByCondidtion(Class<T> tClass, Map<String, SelectItem> condition);
	<T> List<T> getTypesByCondidtion(Class<T> tClass, Map<String, SelectItem> condition, Integer page, Integer pageSize);
    <T>List<T> getTypesForPage(Class<T> tClass, Integer page, Integer pageSize);
}