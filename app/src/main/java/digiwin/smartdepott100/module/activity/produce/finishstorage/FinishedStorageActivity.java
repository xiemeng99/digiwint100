package digiwin.smartdepott100.module.activity.produce.finishstorage;

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
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.activity.common.NoComeUnComActivity;
import digiwin.smartdepott100.module.fragment.produce.finishedstorage.FinishedStorageScanFg;
import digiwin.smartdepott100.module.fragment.produce.finishedstorage.FinishedStorageSumFg;

/**
 * @author xiemeng
 * @des  依成品入库
 * @date 2017/2/22
 */
public class FinishedStorageActivity extends BaseFirstModuldeActivity {
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
    public ViewPager moduleVp;
   /* @BindView(R.id.un_com)
    ImageView unCom;

    @OnClick(R.id.un_com)
    void toUnCom() {
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        bundle.putString(NoComeUnComActivity.MODULECODE, module);
        ActivityManagerUtils.startActivityForBundleData(activity, NoComeUnComActivity.class, bundle);
    }
*/
    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
   public  FinishedStorageScanFg scanFg;
    /**
     * 汇总提交
     */
    FinishedStorageSumFg sumFg;

    ModuleViewPagerAdapter adapter;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.FINISHEDSTORAGE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.finishedstorage);
       // unCom.setVisibility(View.VISIBLE);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_finished_storage;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }


    /**
     * 初始化Fragment
     */
    private void initFragment() {

        scanFg = new FinishedStorageScanFg();
        sumFg = new FinishedStorageSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        moduleVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(moduleVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        select();
    }

    /**
     * 滑动
     */
    private void select() {
        moduleVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
