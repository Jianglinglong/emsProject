package com.six.ems.dao.impl.users;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.excption.JDBCExcption;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.users.AdminDAO;
import com.six.ems.utils.CollectionUtils;

public class AdminDaoImpl implements AdminDAO {

    public <T> boolean add(T t) {
        return AdvanceUtil.insert(t) > 0;
    }

    @Override
    public <T> boolean update(T t) {
        return AdvanceUtil.update(t) > 0;
    }

    @Override
    public <T> boolean delete(T t) {
        boolean delete = true;
        try {
            Class<?> aClass = t.getClass();
            List<T> types = getTypes(t);
            Field available = aClass.getDeclaredField("available");
            if (types!=null){
                for (T change : types) {
                    available.setAccessible(true);
                    available.set(change, "0");
                    available.setAccessible(false);
                     delete = update(change);
                    if (!delete){
                        break;
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            delete = false;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return delete;
    }

    @Override
    public <T> List<T> getTypes(Class<T> tClass) {

        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("available", new SelectItem("1"));
        return AdvanceUtil.select(tClass, condition);
    }

    @Override
    public <T> List<T> getTypes(T t) {
        Class<?> aClass = t.getClass();
        try {
            Field available = aClass.getDeclaredField("available");
            available.setAccessible(true);
            available.set(t, "1");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return AdvanceUtil.select(t);
    }

//	public Role getRoleByID(Integer roleID) {
//		Map<String,SelectItem> condition = CollectionUtils.getCondition();
//		condition.put("role_id", new SelectItem(roleID));
//		List<Role> select = AdvanceUtil.select(Role.class, condition);
//		return CollectionUtils.isNotBlank(select)?select.get(0):null;
//	}
//
//	public Right getRightByID(Integer rightID) {
//        Map<String, SelectItem> condition = CollectionUtils.getCondition();
//        condition.put("right_id",new SelectItem(rightID));
//        List<Right> query = AdvanceUtil.select(Right.class, condition);
//        return CollectionUtils.isNotBlank(query)?query.get(0):null;
//	}

    @Override
    public <T> T getTypeByID(Class<T> tClass, Integer typeID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        Field[] fields = tClass.getDeclaredFields();
        String idName = null;
        for (Field field : fields) {
            SQLPrimaryKey primaryKey = field.getAnnotation(SQLPrimaryKey.class);
            if (primaryKey != null) {
                SQLColnum SQLColnum = field.getAnnotation(SQLColnum.class);
                if (SQLColnum != null) {
                    idName = SQLColnum.colName();
                } else {
                    idName = field.getName();
                }
                break;
            }
        }
        condition.put(idName, new SelectItem(typeID));
        condition.put("available", new SelectItem("1"));
        List<T> query = AdvanceUtil.select(tClass, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public <T> List<T> getTypesByCondidtion(Class<T> tClass, Map<String, SelectItem> condition) {
        condition.put("available", new SelectItem("1"));
        return AdvanceUtil.select(tClass, condition);
    }

    @Override
    public <T> List<T> getTypesForPage(Class<T> tClass, Integer page, Integer pageSize) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getTypesByCondidtion(tClass,condition, page, pageSize);
    }

    @Override
    public <T> List<T> getTypesByCondidtion(Class<T> tClass, Map<String, SelectItem> condition, Integer page,
                                            Integer pageSize) {
        condition.put("available", new SelectItem("1"));
        return AdvanceUtil.select(tClass, condition, page, pageSize);
    }


}
