package instance;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.example.juexingzhe.processshareinstance.ISingletonMain;
import com.example.juexingzhe.processshareinstance.MyApplication;

import service.MainService;
import service.ServiceConnectionImpl;

import Utils.Utils;

/**
 * Created by juexingzhe on 2017/5/25.
 */

public class SingletonMainImpl extends ISingletonMain.Stub {


    public static ISingletonMain INSTANCE;

    private static int count = 0;

    private SingletonMainImpl() {
    }

    public static synchronized ISingletonMain getInstance() {
        if (Utils.isMainProcess()) {//主线程
            if (null == INSTANCE) {
                INSTANCE = new SingletonMainImpl();
            }
        } else {//其它线程
            Context context = MyApplication.getContext();
            Intent intent = new Intent(context, MainService.class);
            context.bindService(intent, new ServiceConnectionImpl(), Context.BIND_AUTO_CREATE);
        }

        return INSTANCE;
    }


    @Override
    public void increment(String currentPorcessName) throws RemoteException {
        count++;
        StringBuilder stringBuilder = new StringBuilder("线程：" + currentPorcessName + "调用" + Utils.currentProcessName() + "的方法increment");
        stringBuilder.append("\n").append("线程" + Utils.currentProcessName() + "count = " + count);
        Utils.showToast(stringBuilder.toString());
    }

    public static int getCount() {
        return count;
    }
}