package com.wy.service;

import com.wy.dao.RoleMenuDao;
import com.wy.entity.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleMenuService")
public class RoleMenuService extends BaseService<RoleMenu> {
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public RoleMenuDao getDao() {
        return roleMenuDao;
    }
}