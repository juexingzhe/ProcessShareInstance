package instance;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.example.juexingzhe.processshareinstance.ISingletonB;
import com.example.juexingzhe.processshareinstance.MyApplication;

import service.ServiceB;
import service.ServiceConnectionImpl;
import Utils.Utils;

/**
 * Created by juexingzhe on 2017/5/25.
 */

public class SingletonBImpl extends ISingletonB.Stub {

    public static ISingletonB INSTANCE;

    public static int COUNT = 0;

    private SingletonBImpl() {
    }

    public static synchronized ISingletonB getInstance() {
        if (Utils.isBProcess()) {//B线程
            if (null == INSTANCE) {
                INSTANCE = new SingletonBImpl();
            }
        } else {//主线程
            Context context = MyApplication.getContext();
            Intent intent = new Intent(context, ServiceB.class);
            context.bindService(intent, new ServiceConnectionImpl(), Context.BIND_AUTO_CREATE);
        }

        return INSTANCE;
    }


    @Override
    public void increment(String currentPorcessName) throws RemoteException {
        COUNT++;
        StringBuilder stringBuilder = new StringBuilder("进程：" + currentPorcessName + "调用" + Utils.currentProcessName() + "的方法increment");
        Utils.showToast(stringBuilder.toString());
    }


    @Override
    public int getCount() {
        return COUNT;
    }
}
