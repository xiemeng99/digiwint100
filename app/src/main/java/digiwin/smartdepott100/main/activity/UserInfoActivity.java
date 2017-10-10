package digiwin.smartdepott100.main.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.login.activity.LoginActivity;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;

/**
 * 账号管理
 *
 * @author 毛衡
 */
public class UserInfoActivity extends BaseTitleActivity {

    /**
     * 用户对象
     */
    private AccoutBean accoutBean;

    /**
     * toolbar
     */
    @BindView(R.id.toolbar_title)
    public Toolbar toolbar;

    /**
     * 用户名
     */
    @BindView(R.id.tv_userInfo_name)
    TextView tv_userInfo_name;

    /**
     * 员工编号
     */
    @BindView(R.id.tv_userInfo_no)
    TextView tv_userInfo_no;

    /**
     * 员工职位
     */
    @BindView(R.id.tv_userInfo_position)
    TextView tv_userInfo_position;

    /**
     * 退出按钮
     */
    @BindView(R.id.tv_userInfo_exit)
    TextView tv_userInfo_exit;

    @OnClick(R.id.tv_userInfo_exit)
    public void exit() {
        AlertDialogUtils.showSettingExitDialog(context, new OnDialogClickListener() {
            @Override
            public void onCallback() {

//                Map<String, String> hashMap = new HashMap<>();
//                hashMap.put("statu", "1");
//                DeviceLogic.getInstance(context, module, mTimestamp.toString()).getDevice(hashMap, new DeviceLogic.DeviceListener() {
//                    @Override
//                    public void onSuccess(List<DeviceInfoBean> deviceInfoBeen) {
//
//                    }
//
//                    @Override
//                    public void onFailed(String msg) {
//                        showFailedDialog(msg);
//                    }
//                });
                ActivityManagerUtils.startActivity(activity, LoginActivity.class);
                List<Activity> activityLists = ActivityManagerUtils.getActivityLists();
                for (Activity mActivity : activityLists) {
                    if (!mActivity.getClass().getSimpleName().equals("LoginActivity")) {
                        if (mActivity != null && !mActivity.isFinishing()) {
                            mActivity.finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initNavigationTitle() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBack.setImageResource(R.drawable.left);
        mBack.setBackgroundResource(R.drawable.image_click_bg);
        mName.setTextColor(getResources().getColor(R.color.black_32));
        Toolbar.LayoutParams tl = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tl.gravity = Gravity.CENTER;
        mName.setLayoutParams(tl);
        mName.setEnabled(false);
        mName.setText(R.string.user_info);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void doBusiness() {
        try {
            accoutBean = LoginLogic.getUserInfo();
            tv_userInfo_name.setText(accoutBean.getEmployee_name());
            tv_userInfo_no.setText(accoutBean.getEmployee_no());
            tv_userInfo_position.setText(accoutBean.getDepartment_name());
            // JPushManager.sendMessage(TelephonyUtils.getDeviceId(activity),"99999999999999999");
        } catch (Exception e) {
            LogUtils.e(TAG, "doBusiness------Exception");
        }

    }

    @Override
    public String moduleCode() {
        module = ModuleCode.OTHER;
        return module;
    }
}
