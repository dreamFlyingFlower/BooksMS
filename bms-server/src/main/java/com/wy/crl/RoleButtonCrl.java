package com.wy.crl;

import com.wy.entity.RoleButton;
import com.wy.service.RoleButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rolebutton")
public class RoleButtonCrl extends BaseCrl<RoleButton> {
    @Autowired
    private RoleButtonService roleButtonService;

    @Override
    public RoleButtonService getService() {
        return roleButtonService;
    }
}