package com.wy.service;

import com.wy.dao.UserRoleDao;
import com.wy.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userRoleService")
public class UserRoleService extends BaseService<UserRole> {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getDao() {
        return userRoleDao;
    }
}