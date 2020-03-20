package com.gzy.easybinder.example;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.gzy.easybinder.Book;

import java.util.ArrayList;
import java.util.List;

public class ExampleServer extends Service {

    private List<Book> bookList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        bookList.add(new Book(1, "Android第一行代码"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBookManager;
    }

    IBookManager.Stub iBookManager = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() {
            return bookList;
        }

        @Override
        public void addBook(Book book) {
            bookList.add(book);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }
    };
}
