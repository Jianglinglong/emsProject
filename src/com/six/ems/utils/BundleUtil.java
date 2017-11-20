package com.six.ems.utils;

import java.util.ResourceBundle;

/**
 * 通过key读取属性文件中的内容
 * @author Direct
 *
 */
public class BundleUtil {
	private static ResourceBundle bundle;
	
	static {
		bundle = ResourceBundle.getBundle("CommonURL");
	}
	
	public static String getValue(String key) {
		return bundle.getString(key);
	}
	
}
