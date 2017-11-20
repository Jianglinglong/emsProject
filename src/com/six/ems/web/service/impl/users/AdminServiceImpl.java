package com.six.ems.web.service.impl.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.dao.impl.users.AdminDaoImpl;
import com.six.ems.dao.interfaces.users.AdminDAO;
import com.six.ems.entity.tables.Right;
import com.six.ems.entity.tables.RoleRight;
import com.six.ems.entity.utils.RoleRightJson;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.users.AdminService;

public class AdminServiceImpl implements AdminService {

    private AdminDAO adminDAO = new AdminDaoImpl();
    public <T> boolean add(T t) {
        return adminDAO.add(t);
    }

    public <T> boolean update(T t) {
        return adminDAO.update(t);
    }

    public <T> boolean delete(T t) {
        return adminDAO.delete(t);
    }

    public boolean deleteRightByRoleID(Integer roleID) {
        List<RoleRight> rightsByRoleID = getRightsByRoleID(roleID);
        for (RoleRight roleRight : rightsByRoleID){
            boolean delete = delete(roleRight);
            if (!delete){
                return false;
            }
        }
        return true;
    }


    public <T> List<T> getTypeItems(Class<T> tClass) {
        return adminDAO.getTypes(tClass);
    }

    @Override
    public <T> List<T> getTypeItems(T t) {
        return adminDAO.getTypes(t);
    }

    @Override
    public <T> List<T> getTypeByCondition(Class<T> tClass, Map<String, SelectItem > condition) {
        return adminDAO.getTypesByCondidtion(tClass,condition);
    }

    @Override
    public <T> List<T> getTypesForPage(Class<T> tClass, Integer page, Integer pageSize) {
        return adminDAO.getTypesForPage(tClass,page,pageSize);
    }

    @Override
    public List<RoleRight> getRightsByRoleID(Integer roleID){
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("role_id",new SelectItem(roleID));
        return adminDAO.getTypesByCondidtion(RoleRight.class,condition);
    }

    private String parenseRightJson(List<Right> allRights, List<RoleRight> roleRights) {
        List<RoleRightJson> roleRightJsons = new ArrayList<>();
        List<Right> mainRight = new ArrayList<>();
        for (Right right :allRights){
            if (right.getParentId()!= null &&right.getParentId()==100001){
                mainRight.add(right);
            }
        }
        allRights.removeAll(mainRight);
        for (Right mRight : mainRight){
            RoleRightJson mRoleRightJson = new RoleRightJson();
            mRoleRightJson.setId(mRight.getRightId());
            mRoleRightJson.setText(mRight.getRightName());
            List<RoleRightJson> roleRightJsonList = new ArrayList<>();
            for (Right cRight : allRights){
                if (cRight.getParentId() == mRight.getRightId()){
                    RoleRightJson cRoleRightJson = new RoleRightJson();
                    cRoleRightJson.setId(cRight.getRightId());
                    cRoleRightJson.setText(cRight.getRightName());
                    for (RoleRight roleRight :roleRights){
                        if (cRight.getRightId() ==roleRight.getRightId()){
                            cRoleRightJson.setChecked(true);
                        }
                    }
                    roleRightJsonList.add(cRoleRightJson);
                }
            }
            mRoleRightJson.setChildren(roleRightJsonList);
            roleRightJsons.add(mRoleRightJson);
        }
        return JSON.toJSONString(roleRightJsons,true);
    }

    @Override
    public String getRoleRightsByRoleID(Integer roleID) {
        List<RoleRight> rightsByRoleID = getRightsByRoleID(roleID);
        List<Right> typeItems = getTypeItems(Right.class);
        return parenseRightJson(typeItems,rightsByRoleID);
    }

	@Override
	public <T> List<T> getTypesForPage(Class<T> tClass, Map<String, SelectItem> condition, Integer page,
			Integer pageSiz) {
		return adminDAO.getTypesByCondidtion(tClass, condition,page,pageSiz);
	}
}
