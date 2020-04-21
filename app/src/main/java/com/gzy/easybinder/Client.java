package com.gzy.easybinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gzy.easybinder.data.Book;

import java.util.List;

/**
 * 客户端
 */
public class Client extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        Button mProxy = findViewById(R.id.proxy_btn);
        mProxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client.this, com.gzy.easybinder.proxy.Client.class);
                startActivity(intent);
            }
        });

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
            Parcel _data = Parcel.obtain();

            Book book = new Book(1, "Android开发艺术探索");
            try {
                book.writeToParcel(_data, 0);
                service.transact(Server.TRANSACTION_addBook, _data, null, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                _data.recycle();
            }
        }

        private List<Book> getBook(IBinder service) {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
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
