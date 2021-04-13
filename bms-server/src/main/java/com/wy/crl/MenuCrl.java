package com.wy.crl;

import com.wy.entity.Menu;
import com.wy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menu")
public class MenuCrl extends BaseCrl<Menu> {
    @Autowired
    private MenuService menuService;

    @Override
    public MenuService getService() {
        return menuService;
    }
}