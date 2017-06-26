package digiwin.smartdepott100.module.activity.purchase.purchasestore;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.purchase.purchasestore.PurchaseStoreScanFg;
import digiwin.smartdepott100.module.fragment.purchase.purchasestore.PurchaseStoreSumFg;
import digiwin.library.utils.LogUtils;

/**
 * sunchangquan
 * 采购仓库退料
 * 2017/5/30
 */

public class PurchaseStoreActivity extends BaseFirstModuldeActivity {

    PurchaseStoreActivity pactivity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * tab
     */
    @BindView(R.id.tl_tab)
    TabLayout tlTab;

    @BindView(R.id.module_vp)
    public
    ViewPager moduleVp;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;


    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;

    /**
     * 扫码
     */
    public PurchaseStoreScanFg scanFg;
    /**
     * 汇总提交
     */
    PurchaseStoreSumFg sumFg;
    ModuleViewPagerAdapter adapter;

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        pactivity = (PurchaseStoreActivity) activity;
        mName.setText(R.string.purchase_store);
    }


    private String mode;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STORERETURNMATERIAL;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_returnmaterial;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {

        scanFg = new PurchaseStoreScanFg();
        sumFg = new PurchaseStoreSumFg();
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
//                if(position == 0){
//                    scanFg.updateView();
//                }
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
            if(requestCode == DETAILCODE){
                sumFg.upDateList();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "onActivityResult-->" + e);
        }

    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
