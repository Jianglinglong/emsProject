package com.six.ems.web.service.interfaces.users;

import com.six.ems.entity.tables.User;

public interface UserService {
	/**
	 * 登陆业务方法
	 */
	String[] login(String principle, String account, String password);
	
	/**
	 * 初始化菜单业务方法
	 */
	String getMenuById(Integer userId);
	
	
	/**
	* 根据用户id查询用户信息
	*/
    User getUserByUserId(Integer userId);
    
    /**
     * 返回相应页面路径的方法
     * @param userId 传入用户id
     * @param tea 传入转发教师页面字符串
     * @param stu 传入转发学生页面字符串
     * @return 页面路径字符串
     */
    String showUserInfo(Object userId, String tea, String stu);
    
    
  
    
}
