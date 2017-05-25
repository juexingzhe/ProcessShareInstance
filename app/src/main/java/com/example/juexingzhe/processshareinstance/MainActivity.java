package com.example.juexingzhe.processshareinstance;

import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import instance.SingletonBImpl;
import instance.SingletonMainImpl;
import service.ServiceB;
import service.ServiceConnectionImpl;
import Utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_invoke_main_increment)
    Button invokeMain;
    @BindView(R.id.main_invoke_b_increment)
    Button invokeB;

    @BindView(R.id.main_text_main)
    TextView textView;
    @BindView(R.id.main_text_count)
    TextView countTextView;

    @BindString(R.string.text_process_main)
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        textView.setText(text);

        bindService(ServiceB.class);
    }

    private void bindService(Class serviceClass){
        Intent intent = new Intent(this, serviceClass);
        bindService(intent, new ServiceConnectionImpl(), BIND_AUTO_CREATE);
    }

    @OnClick(R.id.main_start_b)
    void startProcessB(){
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_invoke_main_increment)
    void invokeMainIncrement(){
        try {
            SingletonMainImpl.getInstance().increment(Utils.currentProcessName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setCountTextView();
    }

    @OnClick(R.id.main_invoke_b_increment)
    void invoke_b_increment(){
        try {
            SingletonBImpl.getInstance().increment(Utils.currentProcessName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setCountTextView();
    }

    private void setCountTextView(){
        countTextView.setText(String.format("count In Main Process = %d\ncount In B Process = %d", SingletonMainImpl.getCount(), SingletonBImpl.getCount()));
    }
}
