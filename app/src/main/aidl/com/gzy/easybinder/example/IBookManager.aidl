package com.gzy.easybinder.example;

import com.gzy.easybinder.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
