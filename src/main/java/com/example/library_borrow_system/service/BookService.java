package com.example.library_borrow_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library_borrow_system.model.Book;
import com.example.library_borrow_system.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // 新增書籍
    public Book addBook(Book book) {
        return bookRepository.save(book);  // 新增或更新書籍
    }
}