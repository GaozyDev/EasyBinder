package com.gzy.easybinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.gzy.easybinder.data.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端
 */
public class Server extends Service {

    /**
     * getBookList 方法标记
     */
    public static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION);
    /**
     * addBook 方法标记
     */
    public static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    private List<Book> bookList = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return new IBookManager();
    }

    /**
     * Binder
     */
    private class IBookManager extends Binder implements IInterface {

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags)
                throws RemoteException {
            switch (code) {
                case TRANSACTION_getBookList:
                    reply.writeTypedList(bookList);
                    return true;
                case TRANSACTION_addBook:
                    Book book = Book.CREATOR.createFromParcel(data);
                    bookList.add(book);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }
}
