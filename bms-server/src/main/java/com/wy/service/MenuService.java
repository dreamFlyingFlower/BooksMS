package com.wy.service;

import com.wy.dao.MenuDao;
import com.wy.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuService extends BaseService<Menu> {
    @Autowired
    private MenuDao menuDao;

    @Override
    public MenuDao getDao() {
        return menuDao;
    }
}