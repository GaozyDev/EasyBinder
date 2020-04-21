package com.gzy.easybinder.proxy.pattern;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.gzy.easybinder.data.Book;

import java.util.List;

/**
 * 接口
 */
public interface IBookManager extends IInterface {

    /**
     * getBookList 方法标记
     */
    int TRANSACTION_getBookList = (IBinder.FIRST_CALL_TRANSACTION);
    /**
     * addBook 方法标记
     */
    int TRANSACTION_addBook = (IBinder.FIRST_CALL_TRANSACTION + 1);

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
