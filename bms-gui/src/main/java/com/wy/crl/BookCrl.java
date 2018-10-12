package com.wy.crl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wy.entity.Book;
import com.wy.service.BookService;

@RestController
@RequestMapping("book")
public class BookCrl extends BaseCrl<Book> {
    @Autowired
    private BookService bookService;

    @Override
    public BookService getService() {
        return bookService;
    }
}