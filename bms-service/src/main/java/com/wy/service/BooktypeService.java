package com.wy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.dao.BooktypeDao;
import com.wy.entity.Booktype;
import com.wy.service.BaseService;

@Service("booktypeService")
public class BooktypeService extends BaseService<Booktype> {
	@Autowired
	private BooktypeDao booktypeDao;

	@Override
	public BooktypeDao getDao() {
		return booktypeDao;
	}
}