package digiwin.smartdepott100.login.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.activity.LoginActivity;
import digiwin.smartdepott100.login.activity.WelcomeGuideActivity;


/**
 * @des      引导页最后一个fragment
 * @author  xiemeng
 * @date    2017/1/4
 */
public class LastFragment extends BaseFragment {

    static LastFragment pane;

    private WelcomeGuideActivity pactivity;

    private Bundle bundle;
    public static LastFragment newInstance(Bundle bundle) {
        if (null==pane){
            pane = new LastFragment();
            pane.setArguments(bundle);
        }
        return pane;
    }
    @Override
    protected int bindLayoutId() {
        bundle = getArguments();
        return R.layout.welcome_fragment04;
    }

    @Override
    protected void doBusiness() {
//        pactivity = (WelcomeGuideActivity) activity;
//        pactivity.mHandler.sendMessageDelayed(pactivity.mHandler.obtainMessage(pactivity.FINISH),2000);
//        SharedPreferencesUtils.put(activity, SharePreKey.ISFIRSTKEY,false);
//        ActivityManagerUtils.startActivityForBundleDataFinish(activity, LoginActivity.class,bundle);
//        activity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }

}