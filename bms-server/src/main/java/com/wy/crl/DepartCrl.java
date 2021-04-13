package com.wy.crl;

import com.wy.entity.Depart;
import com.wy.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("depart")
public class DepartCrl extends BaseCrl<Depart> {
    @Autowired
    private DepartService departService;

    @Override
    public DepartService getService() {
        return departService;
    }
}