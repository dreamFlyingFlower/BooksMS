package com.wy.crl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wy.crl.BaseCrl;
import com.wy.entity.Booktype;
import com.wy.service.BooktypeService;

@RestController
@RequestMapping("booktype")
public class BooktypeCrl extends BaseCrl<Booktype> {
    @Autowired
    private BooktypeService booktypeService;

    @Override
    public BooktypeService getService() {
        return booktypeService;
    }
}