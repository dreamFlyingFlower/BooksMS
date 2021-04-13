package com.wy.service;

import com.wy.dao.ButtonDao;
import com.wy.entity.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("buttonService")
public class ButtonService extends BaseService<Button> {
    @Autowired
    private ButtonDao buttonDao;

    @Override
    public ButtonDao getDao() {
        return buttonDao;
    }
}