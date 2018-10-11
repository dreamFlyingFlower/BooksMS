package com.wy.crl;

import com.wy.entity.RoleMenu;
import com.wy.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rolemenu")
public class RoleMenuCrl extends BaseCrl<RoleMenu> {
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public RoleMenuService getService() {
        return roleMenuService;
    }
}