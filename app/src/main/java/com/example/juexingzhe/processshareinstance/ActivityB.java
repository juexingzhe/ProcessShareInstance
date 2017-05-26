package com.example.juexingzhe.processshareinstance;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import Utils.Utils;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import instance.SingletonBImpl;
import instance.SingletonMainImpl;
import service.MainService;
import service.ServiceConnectionImpl;

/**
 * Created by juexingzhe on 2017/5/24.
 */

public class ActivityB extends AppCompatActivity {
    @BindView(R.id.b_invoke_main_increment)
    Button invokeMain;
    @BindView(R.id.b_invoke_b_increment)
    Button invokeB;

    @BindView(R.id.b_text)
    TextView textView;
    @BindView(R.id.b_text_count_main)
    TextView countTextViewMain;
    @BindView(R.id.b_text_count_b)
    TextView countTextViewB;

    @BindString(R.string.text_process_b)
    String text;
    private int mCount_in_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Bundle extras = getIntent().getExtras();
        mCount_in_main = extras.getInt(MainActivity.COUNT_IN_MAIN);

        ButterKnife.bind(this);
        textView.setText(text);

        bindService(MainService.class);
    }

    private void bindService(Class serviceClass) {
        Intent intent = new Intent(this, serviceClass);
        bindService(intent, new ServiceConnectionImpl(), BIND_AUTO_CREATE);
    }

    @OnClick(R.id.b_start_main)
    void startProcessMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.b_invoke_main_increment)
    void invokeMainIncrement() {
        try {
            SingletonMainImpl.getInstance().increment(Utils.currentProcessName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setCountTextView();
    }

    @OnClick(R.id.b_invoke_b_increment)
    void invoke_b_increment() {
        try {
            SingletonBImpl.getInstance().increment(Utils.currentProcessName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setCountTextView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCountTextView();
    }

    private void setCountTextView() {

        if (null == SingletonMainImpl.getInstance()){
            countTextViewMain.setText(String.format("count In Main Process = %d", mCount_in_main));
        }else {
            try {
                countTextViewMain.setText(String.format("count In Main Process = %d", SingletonMainImpl.getInstance().getCount()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (null != SingletonBImpl.getInstance()) {
            try {
                countTextViewB.setText(String.format("count In B Process = %d", SingletonBImpl.getInstance().getCount()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}
