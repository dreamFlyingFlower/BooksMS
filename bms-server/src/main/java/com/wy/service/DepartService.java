package com.wy.service;

import com.wy.dao.DepartDao;
import com.wy.entity.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departService")
public class DepartService extends BaseService<Depart> {
    @Autowired
    private DepartDao departDao;

    @Override
    public DepartDao getDao() {
        return departDao;
    }
}