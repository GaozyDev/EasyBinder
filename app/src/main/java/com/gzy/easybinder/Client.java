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

import com.gzy.easybinder.example.ExampleServer;
import com.gzy.easybinder.example.IBookManager;

import java.util.List;

public class Client extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);

        bindExampleService();
    }

    private void bindExampleService() {
        Intent intent = new Intent(this, ExampleServer.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                iBookManager.addBook(new Book(2, "Android开发艺术探索"));
                List<Book> bookList = iBookManager.getBookList();

                StringBuilder stringBuilder = new StringBuilder();
                for (Book book : bookList) {
                    stringBuilder.append(book.toString()).append("\n");
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

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
