package com.six.ems.web.service.impl.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.six.ems.constant.UserConstant;
import com.six.ems.dao.impl.users.UserDAOImpl;
import com.six.ems.dao.interfaces.users.UserDAO;
import com.six.ems.entity.utils.Menu;
import com.six.ems.entity.utils.MenuItem;
import com.six.ems.entity.tables.Right;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.tables.UserRole;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.users.UserService;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO = new UserDAOImpl();
	@Override
	public String[] login(String principle, String account, String password) {
		Integer userId = null;
		String userName = "";
		String[] userMessage = new String[2];
		if(principle.equals(UserConstant.TEACHER.getValue())) {
			Teacher tea = new Teacher(account,password);
			tea = userDAO.teaLogin(tea);
			if(tea != null) {
				User user = new User();
				user = userDAO.getUserIdBytea(tea);
				userId = user.getUserId();
				userName = tea.getTeaRealName();
				userMessage[0] = userId+"";
				userMessage[1] = userName;
			}
			
		} else if(principle.equals(UserConstant.STUDENT.getValue())){
			Student stu = new Student(account,password);
			stu = userDAO.stuLogin(stu);
			if(stu != null) {
				User user = new User();
				user = userDAO.getUserIdBystu(stu);
				userId = user.getUserId();
				userName = stu.getStuRealName();
				userMessage[0] = userId+"";
				userMessage[1] = userName;
			}
		}
		return userMessage;
	}

	@Override
	public String getMenuById(Integer userId) {
		List<Right> rightLists = new ArrayList<>();
		
		// 根据userId查找角色Id
		List<UserRole> userList = userDAO.getRoleByUserId(userId);
		for(UserRole userRole : userList) {
			Integer roleId = userRole.getRoleId();
			// 根据角色Id查找权限Id
			Set<Integer> rightsIds = userDAO.getRightIdByRoleId(roleId);
			if(rightsIds!=null) {
				for (Integer rightId : rightsIds) {
					// 根据权限Id查找权限
					List<Right> rightListByRightId = userDAO.getRightListByRightId(rightId);
					Right right = rightListByRightId.get(0);
					rightLists.add(right);
				}
			}
		}
		String menu = "";
		if(CollectionUtils.isNotBlank(rightLists)){
            menu = menuJson(rightLists);
        }
		return menu;
	}
	
	/**
	 * 将权限集合转换为Json字符串
	 * @param rightLists
	 * @return
	 */
	private String menuJson(List<Right> rightLists) {
		// 创建菜单对象
		Menu mune = new Menu();
		// 创建菜单项
		List<MenuItem> menuItemsList = new ArrayList<>();
		
		// 创建主菜单项
		List<Right> parentMenu = new ArrayList<>();
		for(Right right : rightLists) {
			if(UserConstant.ROOT_MENU.getValue().equals(right.getParentId()+"")) {
				parentMenu.add(right);
			}
		}
		
		// 构建菜单项
		for(Right mainMenu : parentMenu) {
			// 创建MenuItem对象
			MenuItem menuItem = new MenuItem();
			menuItem.setMenuid(mainMenu.getRightId());
			menuItem.setMenuname(mainMenu.getRightName());
			
			// 创建子菜单项
			List<MenuItem> childMenuItemList = new ArrayList<>();
			
			for(Right childright : rightLists) {
				if(childright.getParentId() == mainMenu.getRightId()) {
					MenuItem childMenuItem = new MenuItem();
					childMenuItem.setMenuid(childright.getRightId());
					childMenuItem.setMenuname(childright.getRightName());
					childMenuItem.setUrl(childright.getRightUrl());
					
					childMenuItemList.add(childMenuItem);
				}
			}
			
			menuItem.setMenus(childMenuItemList);
			
			menuItemsList.add(menuItem);	
		}
		mune.setList(menuItemsList);
		
		return JSON.toJSONString(mune,true);
	}

	/**
	 * 根据用户id查询用户信息
	 */
	public User getUserByUserId(Integer userId) {
		return userDAO.getUserByUserId(userId);
	}

	 /**
     * 返回相应页面路径的方法
     * @param userId 传入用户id
     * @param tea 传入转发教师页面字符串
     * @param stu 传入转发学生页面字符串
     * @return  页面路径字符串
     */
	public String showUserInfo(Object userId,String tea, String stu) {
		Integer id  = (Integer)userId;
		// 调用方法
		User user = userDAO.getUserByUserId(id);
		Integer teaId = user.getTeaId();
    	Integer stuId = user.getStuId();
    	String str = "";
    	if(userId != null) {
    		if(teaId != 0) {
    			str = tea;
    		}else if(stuId != 0) {
    			str = stu;
        	}
    	}
    	return str;
	}
	

}
