package digiwin.smartdepott100.module.activity.produce.productionleader;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.produce.productionleader.ProductionLeaderScanFg;
import digiwin.smartdepott100.module.fragment.produce.productionleader.ProductionLeaderSumFg;

/**
 * @author 赵浩然
 * @module 生产超领
 * @date 2017/3/30
 */

public class ProductionLeaderActivity extends BaseFirstModuldeActivity{
    private ProductionLeaderActivity pactivity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

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

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;

    ModuleViewPagerAdapter adapter;

    ProductionLeaderScanFg scanFg;

    ProductionLeaderSumFg sumFg;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    @Override
    protected void doBusiness() {
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new ProductionLeaderScanFg();
        sumFg = new ProductionLeaderSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.barcode_scan));
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
                if(position == 0){
                    scanFg.upDateList();
                }

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
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_productionleader;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRODUCTIONLEADER;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.title_production_leader);
        pactivity = (ProductionLeaderActivity) activity;
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
