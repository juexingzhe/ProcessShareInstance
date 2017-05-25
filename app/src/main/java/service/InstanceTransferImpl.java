package service;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.example.juexingzhe.processshareinstance.IInstanceTransfer;
import com.example.juexingzhe.processshareinstance.InstanceFactory;


/**
 * Created by juexingzhe on 2017/5/25.
 */

public class InstanceTransferImpl extends IInstanceTransfer.Stub {
    @Override
    public InstanceFactory transfer() throws RemoteException {
        return new InstanceFactory();
    }

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        try{
            return super.onTransact(code, data, reply, flags);
        }catch (RuntimeException e){
            Log.i("InstanceTransferImpl", "Unexpected exception" + e);
            throw  e;
        }
    }
}
