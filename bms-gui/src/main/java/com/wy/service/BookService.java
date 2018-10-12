package com.wy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.dao.BookDao;
import com.wy.entity.Book;

@Service("bookService")
public class BookService extends BaseService<Book> {
    @Autowired
    private BookDao bookDao;

    @Override
    public BookDao getDao() {
        return bookDao;
    }
}