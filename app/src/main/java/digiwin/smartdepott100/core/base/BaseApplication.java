package digiwin.smartdepott100.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.analytics.MobclickAgent;

import digiwin.library.utils.LogUtils;
import org.litepal.LitePalApplication;

import cn.jpush.im.android.api.JMessageClient;
import digiwin.library.cockroach.Cockroach;
import digiwin.library.netstate.NetChangeObserver;
import digiwin.library.netstate.NetStateReceiver;
import digiwin.library.netstate.NetworkUtils;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.jpush.NotificationClickEventReceiver;


/**
 * Created by ChangquanSun
 * 2017/1/5
 */

public class BaseApplication extends LitePalApplication {

    private static BaseApplication instance;
    private Activity context;
    public NetworkUtils.NetworkType mNetType;
    private NetStateReceiver netStateReceiver;
    /**
     * 极光IM使用
     */
    public static final String TARGET_APP_KEY = "targetAppKey";
    /**
     * 极光IM使用
     */
    public static final String TARGET_ID = "targetId";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //暂时不用（封装好的okhttp）
//        initOkHttp();
        instance = this;
        //友盟统计
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setScenarioType(instance, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //极光IM
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        //或得context对象
        getActivityContext();
        //注册广播
        initNetChangeReceiver();

        // 设置极光IM的Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        // 注册极光IM的Notification点击的接收器
         new NotificationClickEventReceiver(getApplicationContext());

        //安装讯飞语音组件
        SpeechUtility.createUtility(instance, SpeechConstant.APPID + "=5868adcb");
//        VoiceUtils.getInstance(getApplicationContext(), SharePreKey.VOICER_SELECTED).submitUserWords();
        //全局异常捕捉
//        initUnExceptionCatch();
    }
    private void getActivityContext() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                context=activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initUnExceptionCatch() {
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtils.e("AndroidRuntime", "--->CockroachException:" + thread + "---"+ throwable);
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 应用全局的网络变化处理
     */
    private void initNetChangeReceiver() {

        //获取当前网络类型
        mNetType = NetworkUtils.getNetworkType(this);

        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver();

        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver);

        //注册网络变化广播
        NetworkUtils.registerNetStateReceiver(this, netStateReceiver);

    }


    private NetChangeObserver netChangeObserver = new NetChangeObserver() {

        @Override
        public void onConnect(NetworkUtils.NetworkType type) {
            onNetConnect(type);
        }

        @Override
        public void onDisConnect() {
            onNetDisConnect();
        }
    };

    public void onNetDisConnect() {
        AlertDialogUtils.showFailedDialog(context, R.string.network_error);
        mNetType = NetworkUtils.NetworkType.NETWORK_NONE;
    }

    public void onNetConnect(NetworkUtils.NetworkType type) {
        if (type == mNetType) return; //net not change
        switch (type) {
            case NETWORK_WIFI:
                AlertDialogUtils.showNetWorkSuccessDialog(context, R.string.network_wifi_success);
                break;
            case NETWORK_MOBILE:
                AlertDialogUtils.showNetWorkSuccessDialog(context, R.string.network_mobile_success);
                break;
        }
        mNetType = type;
    }

    //释放广播接受者(建议在 最后一个 Activity 退出前调用)
    public void destroyReceiver() {
        //解注册广播接受者,
        NetworkUtils.unRegisterNetStateReceiver(this, netStateReceiver);
    }


}
