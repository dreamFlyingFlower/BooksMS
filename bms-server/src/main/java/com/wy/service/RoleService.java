package com.wy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.dao.RoleDao;
import com.wy.entity.Role;
import com.wy.utils.Result;

@Service("roleService")
public class RoleService extends BaseService<Role> {
	@Autowired
	private RoleDao roleDao;

	@Override
	public RoleDao getDao() {
		return roleDao;
	}

	public Result getRoles(Role bean) {
		return roleDao.getRoles(bean);
	}

	public List<Map<String, Object>> getRoleMenu(int roleId) {
		return roleDao.getRoleMenu(roleId);
	}

	public List<Map<String, Object>> getRoleMenus(int roleId) {
		return roleDao.getRoleMenus(roleId);
	}

	public void saveMenus(int roleId, List<Map<String, Object>> datas) {
		roleDao.saveMenus(roleId, datas);
	}
}