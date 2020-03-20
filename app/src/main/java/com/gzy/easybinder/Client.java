package com.gzy.easybinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Client extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);

        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, Server.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            addBook(service);
            List<Book> bookList = getBook(service);

            StringBuilder stringBuilder = new StringBuilder();
            for (Book book : bookList) {
                stringBuilder.append(book.toString()).append("\n");
            }
            mTextView.setText(stringBuilder.toString());
        }

        private void addBook(IBinder service) {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();

            Book book = new Book(2, "Android开发艺术探索");
            try {
                book.writeToParcel(_data, 0);
                service.transact(Server.TRANSACTION_addBook, _data, _reply, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        private List<Book> getBook(IBinder service) {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            List<Book> bookList = null;
            try {
                service.transact(Server.TRANSACTION_getBookList, _data, _reply, 0);
                bookList = _reply.createTypedArrayList(Book.CREATOR);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return bookList;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
