package com.wy.crl;

import com.wy.entity.UserRole;
import com.wy.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userrole")
public class UserRoleCrl extends BaseCrl<UserRole> {
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserRoleService getService() {
        return userRoleService;
    }
}