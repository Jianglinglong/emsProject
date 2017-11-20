package com.six.ems.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求对象的通用类
 * @author Direct
 *
 */
public class WebRequestUtil {
	
	/**
	 * 把请求对象中的表单元素的值封装到对应的对象属性中
	 * 约定： 表单元素名称必须和属性名称一致
	 * @param clazz 
	 * @param request
	 * @return
	 */
	public static <T> T parseObject(Class<T> clazz, HttpServletRequest request) {
		T entity = null;
		try {
			entity = clazz.newInstance();
			// 获取request中对应表单元素name属性值枚举
			Enumeration<String> names = request.getParameterNames();
			if (names != null) {
				while (names.hasMoreElements()) {
					// 表单元素名称必须和属性名称一致
					String key = names.nextElement();
					if (key.equals("method")) {
						continue;
					}
					// 通过key获取请求元素值
					String value = request.getParameter(key);
					if("".equals(value)) {
						continue;
					}
					// 创建属性对应描述器对象
					PropertyDescriptor pd = new PropertyDescriptor(key, clazz);
					// 获取setter方法
					Method writeMethod = pd.getWriteMethod();
					Field field = clazz.getDeclaredField(key);
					
					// 通过反射调用entity对象中setter方法
					// 对不同字段数据类型处理
					if(field.getType() == int.class) {
						writeMethod.invoke(entity, Integer.parseInt(value));
					} else if(field.getType() == double.class) {
						writeMethod.invoke(entity, Double.parseDouble(value));
					} else if(field.getType() == float.class) {
						writeMethod.invoke(entity, Float.parseFloat(value));
					} else if(field.getType() == Date.class) {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
						writeMethod.invoke(entity, date);
					} else if(field.getType() == Integer.class){
						writeMethod.invoke(entity, Integer.valueOf(value));
					}else if(field.getType() == Double.class){
						writeMethod.invoke(entity,Double.valueOf(value));
					} else {
						writeMethod.invoke(entity, value);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
}
