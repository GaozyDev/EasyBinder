package com.gzy.easybinder.proxy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gzy.easybinder.data.Book;
import com.gzy.easybinder.proxy.pattern.Stub;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端
 */
public class Server extends Service {

    private List<Book> bookList = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private Stub stub = new Stub() {
        @Override
        public List<Book> getBookList() {
            return bookList;
        }

        @Override
        public void addBook(Book book) {
            bookList.add(book);
        }
    };
}
