package digiwin.smartdepott100.main.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digiwin.library.constant.SharePreKey;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogClickgetTextListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.voiceutils.VibratorUtil;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.printer.BlueToothManager;
import digiwin.smartdepott100.login.activity.LoginActivity;
import digiwin.smartdepott100.login.activity.entIdcom.EntIdDialog;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.bean.AppVersionBean;
import digiwin.smartdepott100.login.bean.EntSiteBean;
import digiwin.smartdepott100.login.loginlogic.AppVersionLogic;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.main.activity.settingdialog.DeviceDialog;
import digiwin.smartdepott100.main.activity.settingdialog.StorageDialog;
import digiwin.smartdepott100.main.activity.versions.VersionsSettingDialog;
import digiwin.smartdepott100.main.bean.DeviceInfoBean;
import digiwin.smartdepott100.main.bluetooth.BlueToothDialog;
import digiwin.smartdepott100.main.logic.DeviceLogic;
import digiwin.smartdepott100.main.logic.GetStorageLogic;
import digiwin.smartdepott100.main.voicer.VoicerChooseDialog;

import static digiwin.library.constant.SharePreKey.VOICER_SELECTED;
import static digiwin.library.constant.SystemConstant.VIBRATEMETION;
import static digiwin.library.constant.SystemConstant.VIBRATEMETIONNOT;
import static digiwin.smartdepott100.main.bluetooth.BlueToothDialog.REQUEST_ENABLE_BT;


/**
 * @Author 毛衡
 */
public class SettingActivity extends BaseTitleActivity {
    @BindView(R.id.ll_userinforseting)
    LinearLayout llUserinforseting;
    @BindView(R.id.ll_operatingCenterSetting)
    LinearLayout llOperatingCenterSetting;
    @BindView(R.id.ll_storageSetting)
    LinearLayout llStorageSetting;
    @BindView(R.id.ll_pageSetting)
    LinearLayout llPageSetting;
    @BindView(R.id.ll_repeatSetting)
    LinearLayout llRepeatSetting;
    @BindView(R.id.ll_voicerSetting)
    LinearLayout llVoicerSetting;
    @BindView(R.id.ll_vibrateSetting)
    LinearLayout llVibrateSetting;

    @BindView(R.id.ll_blueToothSetting)
    LinearLayout llBlueToothSetting;
    @BindView(R.id.rl_versionsSetting)
    RelativeLayout rlVersionsSetting;
    @BindView(R.id.activity_setting)
    LinearLayout activitySetting;
    /**
     * 版本信息
     */
    private AppVersionBean versionBean;
    @BindView(R.id.toolbar_title)
    public Toolbar toolbar;
    /**
     * 人员信息
     */
    @BindView(R.id.tv_username)
    TextView tvUsername;
    /**
     * 营运中心
     */
    @BindView(R.id.tv_operatingCenterSetting)
    TextView tv_operatingCenterSetting;
    /**
     * 仓库
     */
    @BindView(R.id.tv_storageSetting)
    TextView tv_storageSetting;
    /**
     * 发音人选择
     */
    @BindView(R.id.tv_voicerSetting)
    TextView tv_voicerSetting;

    /**
     * 分页设置
     */
    @BindView(R.id.tv_pageSetting)
    TextView tv_pageSetting;

    /**
     * 震动提醒
     */
    @BindView(R.id.tv_vibrateSetting)
    TextView tv_vibrateSetting;
    @BindView(R.id.tb_vibrateSetting)
    ToggleButton tb_vibrateSetting;


    /**
     * 蓝牙
     */
    @BindView(R.id.tv_blueToothSetting)
    TextView tv_blueToothSetting;
    @BindView(R.id.tb_blueToothSetting)
    ToggleButton tb_blueToothSetting;
    /**
     * 版本信息
     */
    @BindView(R.id.tv_versionsSetting)
    TextView tv_versionsSetting;

    /**
     * new图标
     */
    @BindView(R.id.tv_versions_new)
    TextView tv_versions_new;
    /**
     * 轮播时间
     */
    @BindView(R.id.tv_repeat_time)
    TextView tvRepeaTime;
    /**
     * 设备解绑
     */
    @BindView(R.id.tv_deviceinfo)
    TextView tvDeviceinfo;

    private LoginLogic loginlogic;
    /**
     * 用户对象
     */
    private AccoutBean accoutBean;

    /**
     * 发音人
     */
    private ArrayList<String> voicersList = new ArrayList<>();
    /**
     * 蓝牙对话框
     */
    private BlueToothDialog blueToothDialog;

    /**
     * 跳转用户信息
     */
    @OnClick(R.id.ll_userinforseting)
    void toUserInfo() {
        ActivityManagerUtils.startActivity(activity, UserInfoActivity.class);
    }

    /**
     * 显示营运中心
     */
    @OnClick(R.id.ll_operatingCenterSetting)
    void showOperatingCenter() {
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null == accoutBean) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", accoutBean.getEmployee_no());
        showLoadingDialog();
        loginlogic.getEntIdCom(map, new LoginLogic.GetEntIdComListener() {
            @Override
            public void onSuccess(List<EntSiteBean> plants) {
                dismissLoadingDialog();
                EntIdDialog.showEntDialog(activity, tv_operatingCenterSetting.getText().toString(), plants);
            }

            @Override
            public void onFailed(String msg) {
                dismissLoadingDialog();
                showFailedDialog(msg);
            }
        });
    }

    /**
     * 选择仓库
     */
    @OnClick(R.id.ll_storageSetting)
    void showStorage() {
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null == accoutBean) {
            return;
        }
        getDefaultStorate(true);
    }

    /**
     * 显示发音人选择
     */
    @OnClick(R.id.ll_voicerSetting)
    void VoicerSetting() {
        VoicerChooseDialog.showVoicerChooseDialog(activity, tv_voicerSetting.getText().toString(), voicersList);
        VoicerChooseDialog.setCallBack(new VoicerChooseDialog.VoicerChooseCallBack() {
            public void VoicerChooseCallBack(String voicerType) {
                tv_voicerSetting.setText(voicerType);
                SharedPreferencesUtils.put(activity, VOICER_SELECTED, voicerType);
                voice(voicerType);
            }
        });
    }

    /**
     * 显示分页设置
     */
    @OnClick(R.id.ll_pageSetting)
    void showPageSetting() {
        AlertDialogUtils.showNumDialog(activity, 1, 100, tv_pageSetting.getText().toString(), new OnDialogClickgetTextListener() {
            @Override
            public void onCallback(CustomDialog dialog, String text) {
                tv_pageSetting.setText(text);
                SharedPreferencesUtils.put(context, SharePreKey.PAGE_SETTING, text);
            }
        });
        // PageSettingDialog.showPageSettingDialog(activity);
    }

    /**
     * 点击展示版本信息
     */
    @OnClick(R.id.rl_versionsSetting)
    public void versionsSetting() {
        VersionsSettingDialog dialog = new VersionsSettingDialog();
        dialog.showVersionDialog(activity, versionBean);
    }


    @OnClick(R.id.ll_repeatSetting)
    void chooseRepeat() {
        AlertDialogUtils.showNumDialog(activity, 1, 200, tvRepeaTime.getText().toString(), new OnDialogClickgetTextListener() {
            @Override
            public void onCallback(CustomDialog dialog, String text) {
                tvRepeaTime.setText(text);
                SharedPreferencesUtils.put(context, SharePreKey.REPEATTIME, text);
            }
        });
    }
    @OnClick(R.id.ll_device_unbind)
    void unBindDialog(){
        DeviceDialog.showUnBindStatuDailog(this, new DeviceDialog.DeviceInfoListener() {
            @Override
            public void unBindByDevice(String psw) {
                AccoutBean info = LoginLogic.getUserInfo();
                if (null!=info&&psw.equals(info.getPassword())){
                    getDeviceInfo("1");
                }else {
                    showFailedDialog(getString(R.string.psw_error));
                }
            }

            @Override
            public void unBindByUse(String psw) {
                AccoutBean info = LoginLogic.getUserInfo();
                if (null!=info&&psw.equals(info.getPassword())){
                    getDeviceInfo("2");
                }else {
                    showFailedDialog(getString(R.string.psw_error));
                }
            }
        });
    }



    @Override
    protected int bindLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initNavigationTitle() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBack.setImageResource(R.drawable.left);
        mBack.setBackgroundResource(R.drawable.image_click_bg);
        tvOperation.setVisibility(View.GONE);
        mName.setTextColor(getResources().getColor(R.color.black_32));
        Toolbar.LayoutParams tl = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tl.gravity = Gravity.CENTER;
        mName.setClickable(false);
        mName.setLayoutParams(tl);
        mName.setText(R.string.system_setting);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void doBusiness() {
        boolean open = BlueToothManager.getManager(activity).isOpen();
        loginlogic = LoginLogic.getInstance(context, module, mTimestamp.toString());
        initVoicerVibrate();
        updateRepeat();
        updatePage();
        setBlueToothUI(open);
        changeTooth();
        updatePlantStorage();
        getDeviceInfo("0");
        UpdateVer();
    }

    /**
     * 轮播时间
     */
    private void updateRepeat() {
        String str = SharedPreferencesUtils.get(context, SharePreKey.REPEATTIME, AddressContants.REPEATTIME).toString();
        tvRepeaTime.setText(str);
    }

    /**
     * 分页设置
     */
    private void updatePage() {
        String str = (String) SharedPreferencesUtils.get(context, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM);
        tv_pageSetting.setText(str);
//        PageSettingDialog.setCallback(new PageSettingDialog.PageSettingCallback() {
//            @Override
//            public void pageSettingCallback(String page) {
//                tv_pageSetting.setText(page);
//            }
//        });
    }

    /**
     * 设置界面发音人/震动提醒  初始化
     */
    public void initVoicerVibrate() {
        try {
            String[] stringArray = getResources().getStringArray(R.array.voicerstring);
            for (int i = 0; i < stringArray.length; i++) {
                voicersList.add(stringArray[i]);
            }
            String voicer = (String) SharedPreferencesUtils.get(activity, SharePreKey.VOICER_SELECTED, activity.getString(R.string.voicer_not));
            tv_voicerSetting.setText(voicer);

            String vibrate = (String) SharedPreferencesUtils.get(activity, SharePreKey.VIBRATE_SETTING, VIBRATEMETION);
            if (vibrate.equals(VIBRATEMETION)) {
                tb_vibrateSetting.setChecked(true);
            } else {
                tb_vibrateSetting.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "initVoicerVibrate----Exception"+e);
        }
    }

    /**
     * 人员信息显示
     * 更新界面的营用中心和仓库
     */
    private void updatePlantStorage() {
        accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
            tvUsername.setText(accoutBean.getEmployee_name());
            tv_operatingCenterSetting.setText(accoutBean.getEnterprise_no());
            EntIdDialog.setCallBack(new EntIdDialog.EntIdCallBack() {
                @Override
                public void entIdCallBack(String chooseEntShow,String chooseEntId) {
                    try {
                        if (!accoutBean.getEnterprise_no().equals(chooseEntShow)) {
                            Connector.getDatabase();
                            accoutBean.setEnterprise_no(chooseEntShow);
                            tv_operatingCenterSetting.setText(accoutBean.getEnterprise_no());
                            accoutBean.update(accoutBean.getId());
                            getDefaultStorate(false);
                        }
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updatePlantStorage---entIdCallBack---Exception"+e);
                    }
                }
            });

            tv_storageSetting.setText(accoutBean.getWare());
            StorageDialog.setCallBack(new StorageDialog.StorageCallBack() {
                @Override
                public void storageCallBack(String chooseStorage) {
                    Connector.getDatabase();
                    accoutBean.setWare(chooseStorage);
                    tv_storageSetting.setText(accoutBean.getWare());
                    accoutBean.update(accoutBean.getId());
                }
            });

        }
    }

    /**
     * 获取仓库
     * flag---true 点击仓库按钮获取
     * flag---false点击营用中心获取
     */
    private void getDefaultStorate(final boolean flag) {
        Map<String, String> map = new HashMap<>();
        if (flag) {
            showLoadingDialog();
        }
        GetStorageLogic.getInstance(context, module, mTimestamp.toString()).getStorage(map, new GetStorageLogic.GetStorageListener() {
            @Override
            public void onSuccess(List<String> wares) {
                dismissLoadingDialog();
                if (wares.size() > 0) {
                    if (flag) {
                        StorageDialog.showStorageDialog(activity, tv_storageSetting.getText().toString(), wares);
                    } else {
                        String chooseStorage = wares.get(0);
                        Connector.getDatabase();
                        accoutBean.setWare(chooseStorage);
                        tv_storageSetting.setText(accoutBean.getWare());
                        accoutBean.update(accoutBean.getId());
                    }
                } else {
                    Connector.getDatabase();
                    accoutBean.setWare("");
                    tv_storageSetting.setText(accoutBean.getWare());
                    accoutBean.update(accoutBean.getId());
                }
            }

            @Override
            public void onFailed(String msg) {
                dismissLoadingDialog();
                showFailedDialog(msg);
            }
        });
    }

    /**
     * 点击选择蓝牙开关
     */
    private void changeTooth() {
        tb_blueToothSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb_blueToothSetting.isChecked()) {
                    bluetooth();
                } else {
                    BlueToothManager.getManager(activity).close();
                    tv_blueToothSetting.setText("");
                }
            }
        });
    }

    /**
     * 点击蓝牙
     */
    // @OnClick(R.id.ll_blueToothSetting)
    void bluetooth() {
        Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enabler, REQUEST_ENABLE_BT);

    }

    /**
     * 震动提醒
     */
    @OnClick(R.id.tb_vibrateSetting)
    void vibrateSetting() {
        String VIBRATE = "";
        if (tb_vibrateSetting.isChecked()) {
            VIBRATE = VIBRATEMETION;
        } else {
            VIBRATE = VIBRATEMETIONNOT;
        }
        SharedPreferencesUtils.put(activity, SharePreKey.VIBRATE_SETTING, VIBRATE);
        if (VIBRATE.equals(VIBRATEMETION)) {
            VibratorUtil.Vibrate(BaseApplication.getContext(), VibratorUtil.VIBRATETIME);
        }

    }

    /**
     * 更新蓝牙显示的UI
     *
     * @param isConnected
     */
    private void setBlueToothUI(final boolean isConnected) {
        if (isConnected) {
            tb_blueToothSetting.setChecked(isConnected);
            tv_blueToothSetting.setText(BlueToothManager.getManager(activity).getDeviceName());
        } else {
            tb_blueToothSetting.setChecked(false);
            tv_blueToothSetting.setText("");
        }
    }

    /**
     * 获取设备信息
     */
    private void getDeviceInfo(final String statu){
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("statu",statu);
        DeviceLogic.getInstance(context,module,mTimestamp.toString()).getDevice(hashMap, new DeviceLogic.DeviceListener() {
            @Override
            public void onSuccess(List<DeviceInfoBean> deviceInfoBeen) {
                if ("0".equals(statu)){
                    if (deviceInfoBeen.size()>0)
                    tvDeviceinfo.setText(activity.getString(R.string.total)+deviceInfoBeen.get(0).getTotal()
                    +activity.getString(R.string.used)+deviceInfoBeen.get(0).getUse());
                }else {
                    DataSupport.deleteAll(AccoutBean.class);
                    ActivityManagerUtils.startActivity(activity, LoginActivity.class);
                    List<Activity> activityLists = ActivityManagerUtils.getActivityLists();
                    for (Activity mActivity:activityLists){
                        if(!mActivity.getClass().getSimpleName().equals("LoginActivity")){
                            if(mActivity!=null &&!mActivity.isFinishing()){
                                mActivity.finish();
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailed(String msg) {
                showFailedDialog(msg);
            }
        });
    }
    /**
     * 检测更新
     */
    private void UpdateVer() {
        tv_versionsSetting.setText(String.valueOf(TelephonyUtils.getMAppVersion(context)));
        Map<String, String> map = new HashMap<>();
        AppVersionLogic.getInstance(context, module, mTimestamp.toString()).getNewVersion(map, new AppVersionLogic.GetNewVersionListener() {
            @Override
            public void onSuccess(AppVersionBean bean) {
                try {
                    versionBean = bean;
                    float vernum = StringUtils.string2Float(bean.getVernum());
                    if (vernum > TelephonyUtils.getMAppVersion(context)) {
                        tv_versions_new.setVisibility(View.VISIBLE);
                        rlVersionsSetting.setEnabled(true);
                    } else {
                        rlVersionsSetting.setEnabled(false);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "UpdateVer异常"+e);
                }

            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_ENABLE_BT) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    setBTReceiver(activity);
                    blueToothDialog = new BlueToothDialog(this, new BlueToothDialog.IsConnectedDeviceListener() {
                        @Override
                        public void isConnected(boolean isConnected) {
                            setBlueToothUI(isConnected);
                        }
                    });
                }
                break;
                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    setBlueToothUI(false);
                }
                break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            activity.unregisterReceiver(mBTReceiver);
        } catch (Exception e) {
            LogUtils.e(TAG, "unregisterReceiver------Exception"+e);
        }

    }

    /**
     * 蓝牙状态发生改变时该广播响应
     */
    BroadcastReceiver mBTReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                final String action = intent.getAction();
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:
                            blueToothDialog.hide();
                            setBlueToothUI(false);
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            blueToothDialog.hide();
                            setBlueToothUI(false);
                            break;
                        case BluetoothAdapter.STATE_ON:
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                    }
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "new BroadcastReceiver()----报错"+e);
            }
        }
    };

    /**
     * 注册蓝牙监听广播
     */
    public void setBTReceiver(Activity activity) {
        IntentFilter btDiscoveryFilter = new IntentFilter();
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        activity.registerReceiver(mBTReceiver, btDiscoveryFilter);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.OTHER;
        return module;
    }
}
