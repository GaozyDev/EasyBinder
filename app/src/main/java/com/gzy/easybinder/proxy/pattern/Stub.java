package com.gzy.easybinder.proxy.pattern;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.gzy.easybinder.data.Book;

import java.util.List;

/**
 * IBookManager 实现类
 */
public abstract class Stub extends Binder implements IBookManager {

    @Override
    public IBinder asBinder() {
        return this;
    }

    public static IBookManager asInterface(IBinder iBinder) {
        return new Proxy(iBinder);
    }

    @Override
    public boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case TRANSACTION_getBookList:
                List<Book> bookList = this.getBookList();
                reply.writeTypedList(bookList);
                return true;
            case TRANSACTION_addBook:
                Book book = Book.CREATOR.createFromParcel(data);
                this.addBook(book);
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
        }
    }
}