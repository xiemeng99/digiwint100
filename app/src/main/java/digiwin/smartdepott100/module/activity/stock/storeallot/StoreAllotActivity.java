package digiwin.smartdepott100.module.activity.stock.storeallot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.activity.common.NoComeUnComActivity;
import digiwin.smartdepott100.module.fragment.stock.storeallot.StoreAllotScanFg;
import digiwin.smartdepott100.module.fragment.stock.storeallot.StoreAllotSumFg;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 无来源调拨
 * @date 2017/3/9
 */

public class StoreAllotActivity extends BaseFirstModuldeActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * tab
     */
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    /**
     * ViewPager
     */
    @BindView(R.id.module_vp)
    public ViewPager mZXVp;
//    @BindView(R.id.un_com)
//    ImageView unCom;

//    @OnClick(R.id.un_com)
//    void toUnCom() {
//        Bundle bundle = new Bundle();
//        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
//        bundle.putString(NoComeUnComActivity.MODULECODE, module);
//        ActivityManagerUtils.startActivityForBundleData(activity, NoComeUnComActivity.class, bundle);
//    }

    @BindView(R.id.img_chooseallot)
   public ImageView ImgChooseallot;
    @OnClick(R.id.img_chooseallot)
    void chooseAllot(){
        if (OUT.equals(INOROUT)) {
            scanFg.etScanInLocator.requestFocus();
            INOROUT=IN;
        }else {
            INOROUT=OUT;
            scanFg.etScanOutLocator.requestFocus();
        }
        initNavigationTitle();
    }
    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
    public StoreAllotScanFg scanFg;
    /**
     * 汇总提交
     */
    StoreAllotSumFg sumFg;

    ModuleViewPagerAdapter adapter;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;


    /**
     * 拨入
     */
    public String IN = "IN";
    /**
     * 拨出
     */
    public String OUT = "OUT";

    /**
     * 当前为拨入还是拨出
     */
    public String INOROUT = "";

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.NOCOMESTOREALLOT;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        ImgChooseallot.setVisibility(View.VISIBLE);
        mName.setText(R.string.nocome_allot);
        if (OUT.equals(INOROUT)) {
            mName.setText(getString(R.string.nocome_allot) + getString(R.string.nocomeallot_out));
        } else {
            mName.setText(getString(R.string.nocome_allot) + getString(R.string.nocomeallot_in));
        }
//        unCom.setVisibility(View.VISIBLE);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_store_allot;
    }

    @Override
    protected void doBusiness() {
        initFragment();
        initDialog();
    }

    public void initDialog() {
        INOROUT = "";
        INOROUT=OUT;
        ChooseAllotDailog.showChooseAllotDailog(activity, new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                //拨入
                scanFg.etScanInLocator.requestFocus();
                INOROUT = IN;
                initNavigationTitle();
            }

            @Override
            public void onCallback2() {
                //拨出
                scanFg.etScanOutLocator.requestFocus();
                INOROUT = OUT;
                initNavigationTitle();
            }
        });
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {

        scanFg = new StoreAllotScanFg();
        sumFg = new StoreAllotSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        mZXVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(mZXVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        select();
    }

    /**
     * 滑动
     */
    private void select() {
        mZXVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    sumFg.upDateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == DETAILCODE) {
                sumFg.upDateList();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "onActivityResult-->" + e);
        }
    }


    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }
}

