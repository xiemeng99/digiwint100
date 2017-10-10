package digiwin.smartdepott100.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.HashMap;

import butterknife.BindView;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.login.bean.AppVersionBean;
import digiwin.smartdepott100.login.fragment.FirstFragment;
import digiwin.smartdepott100.core.coreutil.GestureListener;
import digiwin.smartdepott100.login.fragment.SecondFragment;
import digiwin.smartdepott100.login.fragment.ThirdFragment;
import digiwin.smartdepott100.login.loginlogic.AppVersionLogic;

import static digiwin.smartdepott100.login.activity.LoginActivity.VERSIONKEY;

/**
 * @des      引导页
 * @author  xiemeng
 * @date    2017/1/3
 */
public class WelcomeGuideActivity extends BaseActivity {

    /**
     * viewpager
     */
    @BindView(R.id.pager)
    ViewPager pager;

    /**
     * 布局
     */
    @BindView(R.id.rootlayout)
    View rootlayout;
    /**
     * 引导页个数
     */
    private  static final int NUM_PAGES = 3;
    /**
     * 适配
     */
    private PagerAdapter pagerAdapter;

    private Bundle bundle;

    private AppVersionLogic logic;

    public final int FINISH=1001;
    public Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try{
                switch (msg.what){
                    case FINISH:
                        ActivityManagerUtils.startActivityForBundleDataFinish(activity,LoginActivity.class,bundle);
                        overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                        break;
                }

            }catch (Exception e){
                LogUtils.e(TAG,"mHandler异常");
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_welcomeguide;
    }

    @Override
    protected void initNavigationTitle() {

    }
    @Override
    protected void doBusiness() {
        logic = AppVersionLogic.getInstance(context,module,mTimestamp.toString());
        bundle=new Bundle();
        isFirstUse();
        transparentStatusBar();
        getVersion();
        pager.setLongClickable(true);
        pager.setOnTouchListener(new MyGestureListener(this));
    }

    /**
     * ViewPager的展示
     */
    private  void viewPager(){
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 判断是否为第一次使用
     */
    private void isFirstUse() {
        boolean isFirst = (boolean) SharedPreferencesUtils.get(activity, SharePreKey.ISFIRSTKEY, true);
        if (!isFirst){
            pager.setVisibility(View.GONE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FINISH),2000);
        }else {
            rootlayout.setVisibility(View.GONE);
            viewPager();
        }
   }

    /**
     * 获取版本更新
     */
    private  void getVersion(){
        HashMap<String, String> map = new HashMap<>();
        logic.getNewVersion(map, new AppVersionLogic.GetNewVersionListener() {
            @Override
            public void onSuccess(AppVersionBean bean) {
                bundle.putSerializable(VERSIONKEY,bean);
            }
            @Override
            public void onFailed(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * 适配器，动态创建fragment
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tp = null;
            switch(position){
                case 0:
                    tp = FirstFragment.newInstance();
                    break;
                case 1:
                    tp = SecondFragment.newInstance();
                    break;
                case 2:
                    tp = ThirdFragment.newInstance();
                    break;
//                case 3:
//                    tp = LastFragment.newInstance(bundle);
//                    break;
            }
            return tp;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String moduleCode() {
        module= ModuleCode.OTHER;
        return module;
    }



    /**
     * 继承GestureListener，重写left和right方法
     */
    private class MyGestureListener extends GestureListener {
        public MyGestureListener(Context context) {
            super(context);
        }

        @Override
        public boolean left() {
            int position = pager.getCurrentItem();
            if (position==2) {
                SharedPreferencesUtils.put(activity, SharePreKey.ISFIRSTKEY, false);
                ActivityManagerUtils.startActivityForBundleDataFinish(activity, LoginActivity.class, bundle);
                activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
            return super.left();
        }

        @Override
        public boolean right() {
            return super.right();
        }
    }
}
