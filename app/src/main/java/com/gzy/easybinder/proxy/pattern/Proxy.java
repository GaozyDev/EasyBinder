package com.gzy.easybinder.proxy.pattern;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.gzy.easybinder.Server;
import com.gzy.easybinder.data.Book;

import java.util.List;

/**
 * IBookManager 代理类
 */
public class Proxy implements IBookManager {
    private IBinder mServer;

    Proxy(IBinder remote) {
        mServer = remote;
    }

    @Override
    public IBinder asBinder() {
        return mServer;
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        List<Book> bookList;
        try {
            mServer.transact(Server.TRANSACTION_getBookList, _data, _reply, 0);
            bookList = _reply.createTypedArrayList(Book.CREATOR);
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return bookList;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Parcel _data = Parcel.obtain();
        book.writeToParcel(_data, 0);
        try {
            mServer.transact(Server.TRANSACTION_addBook, _data, null, 0);
        } finally {
            _data.recycle();
        }
    }
}