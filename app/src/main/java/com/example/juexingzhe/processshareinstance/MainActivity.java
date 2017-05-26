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
import service.ServiceB;
import service.ServiceConnectionImpl;

public class MainActivity extends AppCompatActivity {

    public static final String COUNT_IN_MAIN = "count_in_main";

    @BindView(R.id.main_invoke_main_increment)
    Button invokeMain;
    @BindView(R.id.main_invoke_b_increment)
    Button invokeB;

    @BindView(R.id.main_text_main)
    TextView textView;
    @BindView(R.id.main_text_count_main)
    TextView countTextViewMain;
    @BindView(R.id.main_text_count_b)
    TextView countTextViewB;

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

    private void bindService(Class serviceClass) {
        Intent intent = new Intent(this, serviceClass);
        bindService(intent, new ServiceConnectionImpl(), BIND_AUTO_CREATE);
    }

    @OnClick(R.id.main_start_b)
    void startProcessB() {
        Intent intent = new Intent(this, ActivityB.class);
        try {
            intent.putExtra(COUNT_IN_MAIN, SingletonMainImpl.getInstance().getCount());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    @OnClick(R.id.main_invoke_main_increment)
    void invokeMainIncrement() {
        try {
            SingletonMainImpl.getInstance().increment(Utils.currentProcessName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setCountTextView();
    }

    @OnClick(R.id.main_invoke_b_increment)
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
        if (null != SingletonMainImpl.getInstance()) {
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
