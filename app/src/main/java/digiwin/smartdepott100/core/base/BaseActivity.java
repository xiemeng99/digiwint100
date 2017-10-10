package digiwin.smartdepott100.core.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.screenlistener.ScreenListener;

/**
 * Created by ChangquanSun
 * 2017/1/20
 */
public abstract class BaseActivity extends BaseAppActivity {
    private Unbinder unBind;
    private ScreenListener listener;
    private int finishTime = 50000;
    public final int mHandlerWhat = 100;

    /**
     * 具体作业的Id
     */
    public StringBuilder mTimestamp;
    /**
     * 作业编号
     */
    public String module;

    /**
     * 锁屏一段时间后处理app
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mHandlerWhat:
                    ActivityManagerUtils.finishAllActivity();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        createModuleId();
        super.onCreate(savedInstanceState);
        //注册监听屏幕广播
//        listener = new ScreenListener(activity);
        //屏幕监听处理
//        listenerBegin();
    }

    private void listenerBegin() {
        listener.begin(new ScreenListener.ScreenStateListener() {
            //亮屏
            @Override
            public void onScreenOn() {
                mHandler.removeMessages(mHandlerWhat);
            }

            //黑屏
            @Override
            public void onScreenOff() {
                Message msg = Message.obtain();
                msg.what = mHandlerWhat;
                mHandler.sendMessageDelayed(msg, finishTime);
            }

            //解锁
            @Override
            public void onUserPresent() {
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unBind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        listener.unregisterListener();
    }

    /**
     * 模块ID
     */
    public  void createModuleId() {
        mTimestamp = new StringBuilder();
        String s= moduleCode();
        try {
            Bundle bundleForId = getIntent().getExtras();
            if (null != bundleForId ) {
                String stringExtra = bundleForId.getString(AddressContants.MODULEID_INTENT);
                if(!StringUtils.isBlank(stringExtra)) {
                    mTimestamp.append(stringExtra);
                }else{
                    createNewModuleId(s);
                }
            } else {
                createNewModuleId(s);
            }
        }
        catch (Exception e) {
            LogUtils.e(TAG, "createModuleId()---Exception");
        }
    }

    /**
     * 创建新的ModuleId
     */
    public void createNewModuleId(String s){
        mTimestamp = new StringBuilder();
        mTimestamp.append(TelephonyUtils.getDeviceId(this))
                .append(module)
                .append(TelephonyUtils.getTime());
        LogUtils.i(TAG,"createNewModuleId=="+mTimestamp.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * @return 作业编号
     */
    public abstract String moduleCode();

}
