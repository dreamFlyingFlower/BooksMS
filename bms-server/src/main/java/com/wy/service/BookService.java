package com.wy.service;

import com.wy.dao.BookDao;
import com.wy.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookService extends BaseService<Book> {
    @Autowired
    private BookDao bookDao;

    @Override
    public BookDao getDao() {
        return bookDao;
    }
}