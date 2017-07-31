package digiwin.smartdepott100.login.activity;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.core.coreutil.MD5Utils;
import digiwin.smartdepott100.core.coreutil.PermissionUtils;
import digiwin.smartdepott100.core.customview.CustomVideoView;
import digiwin.smartdepott100.core.jpush.JPushManager;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.login.activity.entIdcom.EntIdDialog;
import digiwin.smartdepott100.login.activity.entIdcom.SiteDialog;
import digiwin.smartdepott100.login.activity.setting_dialog.SettingDialog;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.bean.AppVersionBean;
import digiwin.smartdepott100.login.bean.EntSiteBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.main.activity.MainActivity;
import digiwin.smartdepott100.main.activity.versions.VersionsSettingDialog;
import digiwin.smartdepott100.main.bean.StorageBean;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {
    /**
     * 设置背景动画
     */
//    @BindView(R.id.iv_login_bg)
//    RecyclerImageView iv_login_bg;


    /**
     * 系统设置
     */
    @BindView(R.id.tv_setup_systemSettings)
    TextView tv_setup_systemSettings;
//    @BindView(R.id.iv_setup_systemSettings)
//    ImageView iv_setup_systemSettings;
    /**
     * 集团
     */
    @BindView(R.id.rl_entid)
    RelativeLayout rlEntid;
    @BindView(R.id.tv_entid)
    TextView tvEntid;

    /**
     * 据点
     */
    @BindView(R.id.rl_site)
    RelativeLayout rlSite;
    @BindView(R.id.tv_site)
    TextView tvSite;

    @BindView(R.id.line_login_user)
    View lineLoginUser;
    @BindView(R.id.line_login_lock)
    View lineLoginLock;
    @BindView(R.id.line_entid)
    View lineEntid;
    @BindView(R.id.line_site)
    View lineSite;
    @BindView(R.id.iv_login_user)
    ImageView ivLoginUser;
//    @BindView(R.id.iv_login_lock)
//    ImageView ivLoginLock;
    @BindView(R.id.iv_entid)
    ImageView ivEntid;
    @BindView(R.id.iv_site)
    ImageView ivSite;
    @BindView(R.id.videoview)
    CustomVideoView videoView;

    @OnClick(R.id.et_login_user)
    void loginUserColorChange() {
        if (etLoginUser.hasFocus()) changeColor(1);
    }

    @OnClick(R.id.et_login_lock)
    void loginLockColorChange() {
        if (etLoginLock.hasFocus()) {
            changeColor(2);
        }
    }

    @OnFocusChange(R.id.et_login_lock)
    void loginLockFocusChange() {
        if (etLoginLock.hasFocus()) {
            changeColor(2);
        }
    }

    /**
     * 集团
     */
    @OnClick(R.id.rl_entid)
    void showEntId() {
        if (StringUtils.isBlank(etLoginUser.getText().toString()) ||
                StringUtils.isBlank(etLoginLock.getText().toString())) {
            showFailedDialog(R.string.username_pwd_not_null);
            return;
        }
        String entid = tvEntid.getText().toString();
        changeColor(3);
        EntIdDialog.showEntDialog(activity, entid, mEntIds);
    }

    /**
     * 据点
     */
    @OnClick(R.id.rl_site)
    void showSite() {
        if (StringUtils.isBlank(etLoginUser.getText().toString()) ||
                StringUtils.isBlank(etLoginLock.getText().toString())) {
            showFailedDialog(R.string.username_pwd_not_null);
            return;
        }
        String site = tvSite.getText().toString();
        changeColor(4);
        SiteDialog.showSiteDialog(activity, site, mSites);
    }

    /**
     * 背景动画
     */
    private AnimationDrawable animationDrawable;

    /**
     * LoginActivity是否是结束状态,开机启动用到
     */
    private static boolean isFinished = true;
    /**
     * 账号
     */
    @BindView(R.id.et_login_user)
    EditText etLoginUser;
    /**
     * 密码
     */
    @BindView(R.id.et_login_lock)
    EditText etLoginLock;
    /**
     * 登入
     */
    @BindView(R.id.tv_login_rightNow)
    View tvLoginRightNow;
    /**
     * 记住密码
     */
    @BindView(R.id.cb_remeber_password)
    CheckBox cbRemeberPassword;

    /**
     * 版本对象传入到bundle的key值
     */
    static final String VERSIONKEY = "Version";

    /**
     * 逻辑管理
     */
    LoginLogic logic;
    /**
     * 集团
     */
    private List<EntSiteBean> mEntIds;
    /**
     * 据点
     */
    private List<EntSiteBean> mSites;


    private String entId;

    private String site;

    private final int HANDLERWHAT = 100;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initNavigationTitle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        animationDrawable = (AnimationDrawable) iv_login_bg.getBackground();
//        animationDrawable.start();
    }

    @Override
    protected void doBusiness() {
        initView();
        PermissionUtils.verifyStoragePermissions(this);
        final WiFiPrintManager wiFiPrintManager = WiFiPrintManager.getManager();
        wiFiPrintManager.openWiFi("", 0, new WiFiPrintManager.OpenWiFiPrintListener() {
            @Override
            public void isOpen(boolean isOpen) {
                wiFiPrintManager.printText("123");
                wiFiPrintManager.printText("123");
            }
        });
        mEntIds = new ArrayList<>();
        mSites = new ArrayList<>();
        entId = "";
        site = "";
        etLoginUser.setOnFocusChangeListener(focusChangeListener);
        isFinished = false;
        transparentStatusBar();
        logic = LoginLogic.getInstance(activity, module, mTimestamp.toString());
        getUserInfo();
        updateEntIdSite();
        getVersion();

    }


    /**
     * 对话框点击更新界面集团据点updateEntIdSite
     */
    private void updateEntIdSite() {
        EntIdDialog.setCallBack(new EntIdDialog.EntIdCallBack() {
            @Override
            public void entIdCallBack(String chooseEntShow, String chooseEntIdno) {
                tvEntid.setText(chooseEntShow);
                entId = chooseEntIdno;
                getSite(entId, true);
            }
        });
        SiteDialog.setCallBack(new SiteDialog.SiteCallBack() {
            @Override
            public void siteCallBack(String chooseSiteShow, String chooseSiteno) {
                tvSite.setText(chooseSiteShow);
                site = chooseSiteno;
                LogUtils.i(TAG, site + "site--" + site);
            }
        });
    }

    /**
     * 弹出Dialog
     */
    @OnClick(R.id.tv_setup_systemSettings)
    void showSetting() {
        SettingDialog.showSettingDialog(activity);
    }

//    @OnClick(R.id.iv_setup_systemSettings)
//    void showSetting1() {
//        SettingDialog.showSettingDialog(activity);
//    }

    /**
     * 跳转主界面
     */
    @OnClick(R.id.tv_login_rightNow)
    void login() {
        if (StringUtils.isBlank(etLoginUser.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.username_not_null);
            return;
        }
        if (StringUtils.isBlank(etLoginLock.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.password_not_null);
            return;
        }
        if (StringUtils.isBlank(tvEntid.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.ent_not_null);
            return;
        }
        if (StringUtils.isBlank(tvSite.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.site_not_null);
            return;
        }
        AddressContants.ACCTFIRSTLOGIN = etLoginUser.getText().toString().trim();
        AddressContants.ENTERPRISEFIRSTLOGIN = entId;
        AddressContants.SITEFIRSTLOGIN = site;
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.HASHKEY, MD5Utils.md5Encode(etLoginLock.getText().toString()));
        showLoadingDialog();
        logic.login(map, tvEntid.getText().toString(), new LoginLogic.LoginListener() {
            @Override
            public void onSuccess(AccoutBean accoutBean) {
                String vernum = accoutBean.getVernum();
                if (!StringUtils.isBlank(vernum) && StringUtils.string2Float(vernum) > TelephonyUtils.getMAppVersion(context)) {
                    AppVersionBean versionBean = new AppVersionBean();
                    versionBean.setVernum(accoutBean.getVernum());
                    versionBean.setVerurl(accoutBean.getVerurl());
                    versionBean.setVerwhat(accoutBean.getVerwhat());
                    dismissLoadingDialog();
                    matchVersion(versionBean);
                } else {
                    //传入权限
                    Bundle bundle = new Bundle();
                    bundle.putString("access", accoutBean.getAccess());
                    accoutBean.setPassword(etLoginLock.getText().toString());
                    if (cbRemeberPassword.isChecked()) {
                        accoutBean.setIsRemeberPassWord("Y");
                    } else {
                        accoutBean.setIsRemeberPassWord("N");
                    }
                    List<String> split = StringUtils.split(accoutBean.getWare());
                    ArrayList<StorageBean> storageList = new ArrayList<>();
                    for (int i = 0; i < split.size(); i++) {
                        StorageBean storageBean = new StorageBean();
                        storageBean.setWarehouse_no(split.get(i));
                        storageList.add(storageBean);
                    }
                    if (storageList.size() > 0) {
                        accoutBean.setWare(storageList.get(0).getWarehouse_no());
                    }
                    accoutBean.setEnterpriseShow(tvEntid.getText().toString());
                    accoutBean.setSiteShow(tvSite.getText().toString());
                    SQLiteDatabase db = Connector.getDatabase();
                    DataSupport.deleteAll(StorageBean.class);
                    DataSupport.deleteAll(AccoutBean.class);
                    DataSupport.saveAll(storageList);
                    accoutBean.save();
                    ActivityManagerUtils.startActivityForBundleDataFinish(activity, MainActivity.class, bundle);
                    JPushManager.login(TelephonyUtils.getDeviceId(activity), TelephonyUtils.getDeviceId(activity));
//                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailed(String msg) {
                dismissLoadingDialog();
                AlertDialogUtils.showFailedDialog(context, msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        isFinished = true;
    }


    /**
     * 返回当前LoginActivity的状态,避免开机启动重复开启
     *
     * @return boolean
     */
    public static boolean getIsFinished() {
        return isFinished;
    }

    /**
     * 获取服务器版本信息
     */
    private void getVersion() {
        Bundle bundle = getIntent().getExtras();
        // 从bundle数据包中取出数据
        if (null != bundle) {
            AppVersionBean versionBean = (AppVersionBean) bundle.getSerializable(VERSIONKEY);
            // 获取当前版本号进行匹对
            if (null != versionBean) {
                matchVersion(versionBean);
            }
        }
    }


    /**
     * 当用户名焦点发生变化时调用
     */
    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String userName = etLoginUser.getText().toString();
            if (hasFocus) {
                changeColor(1);
            }
            if (!hasFocus && !StringUtils.isBlank(userName)) {
                AddressContants.ACCTFIRSTLOGIN = userName;
                getEntId(userName, true);
            }
        }
    };

    /**
     * 从服务器获取集团
     */
    private void getEntId(String userName, final boolean flag) {
        Map<String, String> map = new HashMap<>();
        map.put("account", userName);
        logic.getEntIdCom(map, new LoginLogic.GetEntIdComListener() {
            @Override
            public void onSuccess(List<EntSiteBean> plants) {
                try {
                    mEntIds = plants;
                    if (flag && mEntIds.size() > 0) {
                        tvEntid.setText(mEntIds.get(0).getEnterprise_show());
                        entId = mEntIds.get(0).getEnterprise_no();
                    }
                    getSite(entId, flag);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getEntId:" + e);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (mEntIds.size() > 0) {
                    tvEntid.setText(mEntIds.get(0).getEnterprise_show());
                    entId = mEntIds.get(0).getEnterprise_no();
                }
                AlertDialogUtils.showFailedDialog(context, msg);
            }
        });
    }

    /**
     * 获取据点
     * flag----true刷新----false不刷新
     *
     * @param chooseEntId
     */
    private void getSite(String chooseEntId, final boolean flag) {
        logic.getSite(chooseEntId, new LoginLogic.GetSiteListener() {
            @Override
            public void onSuccess(List<EntSiteBean> plants) {
                try {
                    mSites = plants;
                    if (flag && mSites.size() > 0) {
                        tvSite.setText(mSites.get(0).getSite_show());
                        site = mSites.get(0).getSite_no();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "getSite:" + e);
                }
            }

            @Override
            public void onFailed(String msg) {
                showFailedDialog(msg);
            }
        });
    }

    /**
     * 配对版本号,高于当前版本强制弹出更新框.
     *
     * @param version
     */
    public void matchVersion(AppVersionBean version) {
        float mAppVersion = TelephonyUtils.getMAppVersion(activity);
        float newVersion = StringUtils.string2Float(version.getVernum());
        if (newVersion > mAppVersion) {
            VersionsSettingDialog.showVersionDialog(activity, version);
        } else {
            return;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size() > 0) {
            AccoutBean accoutBean = accoutBeen.get(0);
            etLoginUser.setText(accoutBean.getAccount());
            tvEntid.setText(accoutBean.getEnterprise_no());
            tvSite.setText(accoutBean.getEnterprise_no());
            if ("Y".equals(accoutBean.getIsRemeberPassWord())) {
                etLoginLock.setText(accoutBean.getPassword());
                cbRemeberPassword.setChecked(true);
            } else {
                cbRemeberPassword.setChecked(false);
            }
            entId = accoutBean.getEnterprise_no();
            site = accoutBean.getSite_no();
            tvEntid.setText(accoutBean.getEnterpriseShow());
            tvSite.setText(accoutBean.getSiteShow());
            etLoginUser.requestFocus();
            getEntId(accoutBean.getAccount(), false);
        }
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.OTHER;
        return module;
    }


    /**
     * 界面色彩变化
     *
     * @param type 1用户 2密码 3集团  4据点
     */
    private void changeColor(int type) {
        try {
            ivLoginUser.setImageResource(R.drawable.login_user_off);
//            ivLoginLock.setImageResource(R.drawable.login_lock_off);
            ivEntid.setImageResource(R.drawable.login_entid_off);
            ivSite.setImageResource(R.drawable.login_site_off);
            lineLoginUser.setBackgroundColor(getResources().getColor(R.color.login_color_line));
            lineLoginLock.setBackgroundColor(getResources().getColor(R.color.login_color_line));
            lineEntid.setBackgroundColor(getResources().getColor(R.color.login_color_line));
            lineSite.setBackgroundColor(getResources().getColor(R.color.login_color_line));
            etLoginUser.setTextColor(getResources().getColor(R.color.white_ff));
            etLoginLock.setTextColor(getResources().getColor(R.color.white_ff));
            tvEntid.setTextColor(getResources().getColor(R.color.white_ff));
            tvSite.setTextColor(getResources().getColor(R.color.white_ff));
            switch (type) {
                case 1:
                    ivLoginUser.setImageResource(R.drawable.login_user_on);
                    lineLoginUser.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    etLoginUser.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
                case 2:
                    //ivLoginLock.setImageResource(R.drawable.login_lock_on);
                    lineLoginLock.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    etLoginLock.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
                case 3:
                    ivEntid.setImageResource(R.drawable.login_entid_on);
                    lineEntid.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    tvEntid.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
                case 4:
                    ivSite.setImageResource(R.drawable.login_site_on);
                    lineSite.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    tvSite.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "changeColor" + e);
        }


    }


    private void initView() {
        //设置播放加载路径
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f,0f);
                videoView.start();
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            videoView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        return true;
                    }
                });
            }
        });
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test1));
        //循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

    }

//    返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoView.stopPlayback();
        super.onStop();
    }

}
