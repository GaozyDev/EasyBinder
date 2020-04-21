package com.gzy.easybinder.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gzy.easybinder.R;
import com.gzy.easybinder.data.Book;
import com.gzy.easybinder.proxy.pattern.IBookManager;
import com.gzy.easybinder.proxy.pattern.Stub;

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

        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, Server.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = Stub.asInterface(service);
            try {
                Book book = new Book(1, "Android开发艺术探索");
                iBookManager.addBook(book);

                List<Book> bookList = iBookManager.getBookList();
                StringBuilder stringBuilder = new StringBuilder();
                for (Book b : bookList) {
                    stringBuilder.append(b.toString()).append("\n");
                }
                mTextView.setText(stringBuilder.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
