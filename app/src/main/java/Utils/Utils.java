package Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.widget.Toast;

import com.example.juexingzhe.processshareinstance.MyApplication;

import java.util.List;


/**
 * Created by juexingzhe on 2017/5/25.
 */

public class Utils {

    public static boolean isMainProcess() {

        String packageName = MyApplication.getContext().getPackageName();
        return packageName.equals(currentProcessName());

    }

    public static boolean isBProcess(){
        return currentProcessName().endsWith(":B");
    }

    public static String currentProcessName() {

        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
            if (null != info && info.pid == myPid) {
                return info.processName;
            }
        }
        return "";

    }

    public static void showToast(String msg){
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }


}
