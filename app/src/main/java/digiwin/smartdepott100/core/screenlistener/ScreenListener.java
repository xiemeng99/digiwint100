package digiwin.smartdepott100.core.screenlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * 监听屏幕变化
 *
 * @author 毛衡
 * Created by Administrator on 2017/2/3 0003.
 */

public class ScreenListener {
    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;

    public ScreenListener(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 亮屏
                mScreenStateListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 黑屏
                mScreenStateListener.onScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁后触发
                mScreenStateListener.onUserPresent();
            }
        }
    }
    /**
     * 开始监听screen状态
     * @param listener
     */
    public void begin(ScreenStateListener listener) {
        mScreenStateListener = listener;
        //注册
        registerListener();
        getScreenState();
    }

    /**
     * 获取screen状态
     */
    private void getScreenState() {
        PowerManager manager = (PowerManager) mContext
                .getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOn();
            }
        } else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }

    /**
     * 注销screen状态监听
     */
    public void unregisterListener() {
        mContext.unregisterReceiver(mScreenReceiver);
    }

    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenReceiver, filter);
    }

    public interface ScreenStateListener {
        public void onScreenOn();
        public void onScreenOff();
        public void onUserPresent();
    }


}
