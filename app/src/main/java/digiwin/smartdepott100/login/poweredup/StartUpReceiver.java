package digiwin.smartdepott100.login.poweredup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import digiwin.smartdepott100.login.activity.LoginActivity;


/**
 * 开机启动
 */
public class StartUpReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (LoginActivity.getIsFinished()){
            //跳转至登录页
            Intent service = new Intent(context,LoginActivity.class);
            context.startService(service);
            //启动应用
            Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("digiwin.smartdepot");
            context.startActivity(intent1);
        }
    }
}
