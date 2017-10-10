package digiwin.smartdepott100.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.util.UserWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.main.bean.DeviceInfoBean;
import digiwin.smartdepott100.main.logic.DeviceLogic;
import digiwin.smartdepott100.main.logic.MainLogic;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.main.bean.VoiceWord;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ToastUtils;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.main.bean.ModuleBean;
import digiwin.smartdepott100.main.bean.TotalMode;
import digiwin.smartdepott100.main.fragment.DetailFragment;
import digiwin.smartdepott100.main.logic.MainLogic;


public class MainActivity extends BaseTitleActivity {
    @BindView(R.id.tv_title_person_name)
    TextView tvPersonName;
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.ll_point)
    LinearLayout linearLayoutPoints;
    @BindView(R.id.tb_title)
    TabLayout tablayout;
    /**
     * 未完事项
     */
    @BindView(R.id.un_com)
    ImageView iv_un_com;
    //    @BindView(R.id.tv_title)
//    public TextView mTitleName;
    //两秒内按返回键两次退出程序
    private long exitTime;
    /**
     * 指示点集合
     */
    private List<View> points;
    /**
     * 标题栏数组
     */
    private List<String> titles;

    @BindView(R.id.tv_title_operation)
    TextView tv_title_operation;

    String command = "";

    @BindView(R.id.voice_guide)
    ImageView voiceGuide;

    /**
     * 语音按钮
     */
    @OnClick(R.id.voice_guide)
    void voiceTest() {
        String voicer = (String) SharedPreferencesUtils.get(this, SharePreKey.VOICER_SELECTED, "voicer");
        final List<ModuleBean> list = MainLogic.ModuleList;
        VoiceUtils voiceUtils = VoiceUtils.getInstance(this, voicer);
        voiceUtils.voiceToText(new VoiceUtils.GetVoiceTextListener() {
            @Override
            public String getVoiceText(String str) {
                command = str.toUpperCase();
                Log.d(TAG,"command:"+command);
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String name = getResources().getString(list.get(i).getNameRes()).toUpperCase();
                        if(command.length() == name.length()) {
                            if (command.equals(name)) {
                                ActivityManagerUtils.startActivity(MainActivity.this, list.get(i).getIntent());
                            }else{
                                if(StringUtils.getPingYin(command).equals(StringUtils.getPingYin(name))){
                                    ActivityManagerUtils.startActivity(MainActivity.this, list.get(i).getIntent());
                                }
                            }
                        }
                    }
                }
                return str;
            }
        });

    }

    private final int REQUESTCODE = 1002;

    @OnClick(R.id.iv_title_setting)
    public void goSetting() {
        ActivityManagerUtils.startActivityForResult(activity, SettingActivity.class,REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE){
            recreate();
        }
    }

    /**
     * 用户权限
     */
    private String access;
    /**
     * 采购管理
     */
    private List<ModuleBean> purchaseItems;
    /**
     * 生产管理
     */
    private List<ModuleBean> produceItems;
    /**
     * 库存管理
     */
    private List<ModuleBean> storageItems;
    /**
     * 销售管理
     */
    private List<ModuleBean> salesItems;
    /**
     * 报工管理
     */
    private List<ModuleBean> dailyworkItems;
    /**
     * 看板管理
     */
    private List<ModuleBean> boardItems;
    /**
     * 用户权限模块
     */
    private List<String> powerItems;
    /**
     * 点击选择模块
     */
    private List<TotalMode> totalModes;

    MainLogic mainLogic;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initNavigationTitle() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        mBack.setVisibility(View.GONE);
        iv_title_setting.setVisibility(View.VISIBLE);

        Toolbar.LayoutParams tl = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tl.gravity = Gravity.CENTER;
        mName.setLayoutParams(tl);
        mName.setEnabled(false);
        mName.setText(R.string.app_name);

    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void doBusiness() {
        exitTime = 0;
        mainLogic = new MainLogic(this);
        initModule();
        submitUserWords();
    }
    /**
     * 上传用户此表，用于精准识别语音录入
     */
    private void submitUserWords() {
        List<ModuleBean> modlueList = mainLogic.ModuleList;
        Log.d(TAG,"moduleList.size():"+modlueList.size());
        List<String> voiceWordList = new ArrayList<>();
        VoiceWord defaultWord = new VoiceWord();
        defaultWord.setName("default");
        defaultWord.setWord(getResources().getString(R.string.purchase_check));
        voiceWordList.add(defaultWord.toString());
        if(modlueList.size()>0){
            for (ModuleBean module:modlueList) {
                String moduleName = getResources().getString(module.getNameRes());
                VoiceWord voiceWord = new VoiceWord();
                voiceWord.setName(moduleName);
                voiceWord.setWord(moduleName);
                voiceWordList.add(voiceWord.toString());
            }
            StringBuffer userword = new StringBuffer();
            String head =  "{"+"\"" + "userword" +"\"" + ":";
            userword.append(head);
            userword.append(voiceWordList.toString());
            userword.append("}");
            Log.d(TAG,"voiceWord.toArray():"+userword.toString());
            VoiceUtils.getInstance(getApplicationContext(), SharePreKey.VOICER_SELECTED).submitUserWords(userword.toString());
        }
    }

    //初始化各个模块
    private void initModule() {
        //正式发布版本时在解开
        access=getIntent().getExtras().getString("access");
        powerItems = StringUtils.split(access);
        //模拟测试
//        powerItems = new ArrayList<>();

        totalModes = new ArrayList<TotalMode>();

        titles = new ArrayList<>();
        purchaseItems = new ArrayList<>();
        produceItems = new ArrayList<>();
        storageItems = new ArrayList<>();
        salesItems = new ArrayList<>();
        dailyworkItems = new ArrayList<>();
        boardItems = new ArrayList<>();
        //初始化各个模块数据
        mainLogic.initModule(activity, powerItems, purchaseItems, produceItems, storageItems, salesItems, dailyworkItems, boardItems);
        showTitle();
    }

    /**
     * 显示标题栏
     */
    public void showTitle() {
        mainLogic.showTitle(totalModes, titles);
        initDatas();
        mainLogic.setCustomTab(context, tablayout, titles);
        mainLogic.initListener(context, points, viewPager, tablayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // MainLogic.setTitle(tvPersonName, tv_title_operation);
        boolean speechInput = (boolean) SharedPreferencesUtils.get(activity, SharePreKey.SPEECH_INPUT, true);
        if (speechInput) {
            voiceGuide.setVisibility(View.VISIBLE);
        }else {
            voiceGuide.setVisibility(View.GONE);
        }
    }

    /**
     * 用户信息界面跳转
     */
    @OnClick(R.id.tv_title_person_name)
    public void showUserInfo() {
        ActivityManagerUtils.startActivity(activity, UserInfoActivity.class);
    }

    private void initDatas() {
        points = new ArrayList<>();
        mainLogic.setPonit(context, titles, points, linearLayoutPoints);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.OTHER;
        return module;
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailFragment.getInstance(totalModes.get(position).list);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showToastByString(context, this.getResources().getString(R.string.app_exit));
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().destroyReceiver();
                ActivityManagerUtils.finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
