package com.example.juexingzhe.processshareinstance;

import android.os.Parcel;
import android.os.Parcelable;

import Utils.Utils;
import instance.SingletonBImpl;
import instance.SingletonMainImpl;

/**
 * Created by juexingzhe on 2017/5/25.
 */

public class InstanceFactory implements Parcelable {

    private static final int PROCESS_MAIN = 1;
    private static final int PROCESS_B = 2;

    public InstanceFactory() {

    }

    public InstanceFactory(Parcel in) {

        int processId = in.readInt();

        switch (processId) {
            case PROCESS_MAIN:
                SingletonMainImpl.INSTANCE = ISingletonMain.Stub.asInterface(in.readStrongBinder());
                break;
            case PROCESS_B:
                SingletonBImpl.INSTANCE = ISingletonB.Stub.asInterface(in.readStrongBinder());
                break;
        }

    }

    public static final Creator<InstanceFactory> CREATOR = new Creator<InstanceFactory>() {
        @Override
        public InstanceFactory createFromParcel(Parcel in) {
            return new InstanceFactory(in);
        }

        @Override
        public InstanceFactory[] newArray(int size) {
            return new InstanceFactory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (Utils.isMainProcess()) {
            dest.writeInt(PROCESS_MAIN);
            dest.writeStrongInterface(SingletonMainImpl.getInstance());
        } else if (Utils.isBProcess()) {
            dest.writeInt(PROCESS_B);
            dest.writeStrongInterface(SingletonBImpl.getInstance());
        }


    }
}
