package com.gzy.easybinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Server extends Service {

    public static final String DESCRIPTOR = "com.gzy.easybinder.IBookManager";

    public static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION);
    public static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    private List<Book> bookList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "Android第一行代码"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IBookManager();
    }

    private class IBookManager extends Binder implements IInterface {

        IBookManager() {
            this.attachInterface(this, DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
                throws RemoteException {
            switch (code) {
                case TRANSACTION_getBookList: {
                    reply.writeTypedList(bookList);
                    return true;
                }
                case TRANSACTION_addBook: {
                    Book book = com.gzy.easybinder.Book.CREATOR.createFromParcel(data);
                    bookList.add(book);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }
    }
}
