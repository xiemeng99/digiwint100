package digiwin.smartdepott100.login.activity;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
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
import digiwin.pulltorefreshlibrary.recyclerview.RecyclerImageView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.core.coreutil.MD5Utils;
import digiwin.smartdepott100.core.jpush.JPushManager;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.login.activity.entIdcom.SiteDialog;
import digiwin.smartdepott100.login.activity.entIdcom.EntIdDialog;
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
    @BindView(R.id.iv_login_bg)
    RecyclerImageView iv_login_bg;
    /**
     * 系统设置
     */
    @BindView(R.id.tv_setup_systemSettings)
    TextView tv_setup_systemSettings;
    @BindView(R.id.iv_setup_systemSettings)
    ImageView iv_setup_systemSettings;
    /**
     * 集团
     */
    @BindView(R.id.rl_entid)
    RelativeLayout rl_entid;
    @BindView(R.id.tv_entid)
    TextView tv_entid;

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
    @BindView(R.id.iv_login_lock)
    ImageView ivLoginLock;
    @BindView(R.id.iv_entid)
    ImageView ivEntid;
    @BindView(R.id.iv_site)
    ImageView ivSite;

    @OnClick(R.id.et_login_user)
    void loginUserColorChange() {
        if (et_login_user.hasFocus()) changeColor(1);
    }

    @OnClick(R.id.et_login_lock)
    void loginLockColorChange() {
        if (et_login_lock.hasFocus()) {
            changeColor(2);
        }
    }

    @OnFocusChange(R.id.et_login_lock)
    void loginLockFocusChange() {
        if (et_login_lock.hasFocus()) {
            changeColor(2);
        }
    }

    /**
     * 集团
     */
    @OnClick(R.id.rl_entid)
    void showEntId() {
        String entid = tv_entid.getText().toString();
        changeColor(3);
        EntIdDialog.showEntDialog(activity, entid, mEntIds);
    }

    /**
     * 据点
     */
    @OnClick(R.id.rl_site)
    void showSite() {
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
    EditText et_login_user;
    /**
     * 密码
     */
    @BindView(R.id.et_login_lock)
    EditText et_login_lock;
    /**
     * 登入
     */
    @BindView(R.id.tv_login_rightNow)
    View tv_login_rightNow;
    /**
     * 记住密码
     */
    @BindView(R.id.cb_remeber_password)
    CheckBox cb_remeber_password;

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
        animationDrawable = (AnimationDrawable) iv_login_bg.getBackground();
        animationDrawable.start();
    }

    @Override
    protected void doBusiness() {
        final WiFiPrintManager wiFiPrintManager = WiFiPrintManager.getManager();
        wiFiPrintManager.openWiFi("", 0, new WiFiPrintManager.OpenWiFiPrintListener() {
            @Override
            public void isOpen(boolean isOpen) {
                wiFiPrintManager.printText("123");
            }
        });
        mEntIds = new ArrayList<>();
        mSites = new ArrayList<>();
        entId = "";
        site = "";
        et_login_user.setOnFocusChangeListener(focusChangeListener);
        isFinished = false;
        transparentStatusBar();
        logic = LoginLogic.getInstance(activity, module, mTimestamp.toString());
        getUserInfo();
        updateEntIdSite();
        getVersion();
    }


    /**
     * 对话框点击更新界面集团据点
     */
    private void updateEntIdSite() {
        EntIdDialog.setCallBack(new EntIdDialog.EntIdCallBack() {
            @Override
            public void entIdCallBack(String chooseEntShow, String chooseEntIdno) {
                tv_entid.setText(chooseEntShow);
                entId = chooseEntIdno;
                getSite(entId);
            }
        });
        SiteDialog.setCallBack(new SiteDialog.SiteCallBack() {
            @Override
            public void siteCallBack(String chooseSite) {
                tvSite.setText(chooseSite);
                site = chooseSite;
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

    @OnClick(R.id.iv_setup_systemSettings)
    void showSetting1() {
        SettingDialog.showSettingDialog(activity);
    }

    /**
     * 跳转主界面
     */
    @OnClick(R.id.tv_login_rightNow)
    void login() {
        if (StringUtils.isBlank(et_login_user.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.username_not_null);
            return;
        }
        if (StringUtils.isBlank(et_login_lock.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.password_not_null);
            return;
        }
        if (StringUtils.isBlank(tv_entid.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.ent_not_null);
            return;
        }
        if (StringUtils.isBlank(tvSite.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context, R.string.site_not_null);
            return;
        }
        AddressContants.ACCTFIRSTLOGIN = et_login_user.getText().toString().trim();
        AddressContants.ENTERPRISEFIRSTLOGIN = entId;
        AddressContants.SITEFIRSTLOGIN = site;
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.HASHKEY, MD5Utils.md5Encode(et_login_lock.getText().toString()));
        showLoadingDialog();
        logic.login(map, tv_entid.getText().toString(), new LoginLogic.LoginListener() {
            @Override
            public void onSuccess(AccoutBean accoutBean) {
                String vernum = accoutBean.getVernum();
//                if (!StringUtils.isBlank(vernum) && StringUtils.string2Float(vernum) > TelephonyUtils.getMAppVersion(context)) {
//                    AppVersionBean versionBean = new AppVersionBean();
//                    versionBean.setVernum(accoutBean.getVernum());
//                    versionBean.setVerurl(accoutBean.getVerurl());
//                    versionBean.setVerwhat(accoutBean.getVerwhat());
//                    dismissLoadingDialog();
//                    matchVersion(versionBean);
//                } else {
                //传入权限
                Bundle bundle = new Bundle();
                bundle.putString("access", accoutBean.getAccess());
                accoutBean.setPassword(et_login_lock.getText().toString());
                if (cb_remeber_password.isChecked()) {
                    accoutBean.setIsRemeberPassWord("Y");
                } else {
                    accoutBean.setIsRemeberPassWord("N");
                }
                List<String> split = StringUtils.split(accoutBean.getWare());
                ArrayList<StorageBean> storageList = new ArrayList<>();
                for (int i = 0; i < split.size(); i++) {
                    StorageBean storageBean = new StorageBean();
                    storageBean.setWare(split.get(i));
                    storageList.add(storageBean);
                }
                if (storageList.size() > 0) {
                    accoutBean.setWare(storageList.get(0).getWare());
                }
                accoutBean.setEnterpriseShow(tv_entid.getText().toString());
                accoutBean.setSiteShow(tvSite.getText().toString());
                //TODO:仓库逻辑需修改
                accoutBean.setWare("101");
                SQLiteDatabase db = Connector.getDatabase();
                DataSupport.deleteAll(StorageBean.class);
                DataSupport.deleteAll(AccoutBean.class);
                DataSupport.saveAll(storageList);
                accoutBean.save();
                ActivityManagerUtils.startActivityForBundleDataFinish(activity, MainActivity.class, bundle);
                JPushManager.login(TelephonyUtils.getDeviceId(activity), TelephonyUtils.getDeviceId(activity));
//                    dismissLoadingDialog();
            }
//            }

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
            String userName = et_login_user.getText().toString();
            if (hasFocus) {
                changeColor(1);
            }
            if (!hasFocus && !StringUtils.isBlank(userName)) {
                getEntId(userName);
            }
        }
    };

    /**
     * 从服务器获取集团
     */
    private void getEntId(String userName) {
        Map<String, String> map = new HashMap<>();
        map.put("account", userName);
        logic.getEntIdCom(map, new LoginLogic.GetEntIdComListener() {
            @Override
            public void onSuccess(List<EntSiteBean> plants) {
                try {
                    mEntIds = plants;
                    if (StringUtils.isBlank(tv_entid.getText().toString().trim())
                            && mEntIds.size() > 0) {
                        tv_entid.setText(mEntIds.get(0).getEnterprise_show());
                        entId = mEntIds.get(0).getEnterprise_no();
                    }
                    getSite(entId);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getEntId:" + e);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (mEntIds.size() > 0) {
                    tv_entid.setText(mEntIds.get(0).getEnterprise_show());
                    entId = mEntIds.get(0).getEnterprise_no();
                }
                AlertDialogUtils.showFailedDialog(context, msg);
            }
        });
    }

    /**
     * 获取据点
     *
     * @param chooseEntId
     */
    private void getSite(String chooseEntId) {
        logic.getSite(chooseEntId, new LoginLogic.GetSiteListener() {
            @Override
            public void onSuccess(List<EntSiteBean> plants) {
                try {
                    mSites = plants;
                    if (StringUtils.isBlank(tvSite.getText().toString().trim())
                            && mSites.size() > 0) {
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
            et_login_user.setText(accoutBean.getAccount());
            tv_entid.setText(accoutBean.getEnterprise_no());
            tvSite.setText(accoutBean.getEnterprise_no());
            if ("Y".equals(accoutBean.getIsRemeberPassWord())) {
                et_login_lock.setText(accoutBean.getPassword());
                cb_remeber_password.setChecked(true);
            } else {
                cb_remeber_password.setChecked(false);
            }
            entId = accoutBean.getEnterprise_no();
            site = accoutBean.getSite_no();
            tv_entid.setText(accoutBean.getEnterpriseShow());
            tvSite.setText(accoutBean.getSiteShow());
            et_login_user.requestFocus();
            getEntId(accoutBean.getAccount());
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
            ivLoginLock.setImageResource(R.drawable.login_lock_off);
            ivEntid.setImageResource(R.drawable.login_entid_off);
            ivSite.setImageResource(R.drawable.login_site_off);
            lineLoginUser.setBackgroundColor(getResources().getColor(R.color.Login_line_color));
            lineLoginLock.setBackgroundColor(getResources().getColor(R.color.Login_line_color));
            lineEntid.setBackgroundColor(getResources().getColor(R.color.Login_line_color));
            lineSite.setBackgroundColor(getResources().getColor(R.color.Login_line_color));
            et_login_user.setTextColor(getResources().getColor(R.color.Text_color));
            et_login_lock.setTextColor(getResources().getColor(R.color.Text_color));
            tv_entid.setTextColor(getResources().getColor(R.color.Text_color));
            tvSite.setTextColor(getResources().getColor(R.color.Text_color));
            switch (type) {
                case 1:
                    ivLoginUser.setImageResource(R.drawable.login_user_on);
                    lineLoginUser.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    et_login_user.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
                case 2:
                    ivLoginLock.setImageResource(R.drawable.login_lock_on);
                    lineLoginLock.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    et_login_lock.setTextColor(getResources().getColor(R.color.login_color_t));
                    break;
                case 3:
                    ivEntid.setImageResource(R.drawable.login_entid_on);
                    lineEntid.setBackgroundColor(getResources().getColor(R.color.login_color_t));
                    tv_entid.setTextColor(getResources().getColor(R.color.login_color_t));
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

}
