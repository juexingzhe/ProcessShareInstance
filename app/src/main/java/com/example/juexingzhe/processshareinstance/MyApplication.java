package com.example.juexingzhe.processshareinstance;

import android.app.Application;
import android.content.Context;

/**
 * Created by juexingzhe on 2017/5/25.
 */

public class MyApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
