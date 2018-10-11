package com.wy.service;

import com.wy.dao.RoleButtonDao;
import com.wy.entity.RoleButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleButtonService")
public class RoleButtonService extends BaseService<RoleButton> {
    @Autowired
    private RoleButtonDao roleButtonDao;

    @Override
    public RoleButtonDao getDao() {
        return roleButtonDao;
    }
}