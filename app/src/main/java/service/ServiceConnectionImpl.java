package service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by juexingzhe on 2017/5/25.
 */

public class ServiceConnectionImpl implements ServiceConnection {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        try {
            InstanceTransferImpl.asInterface(service).transfer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
