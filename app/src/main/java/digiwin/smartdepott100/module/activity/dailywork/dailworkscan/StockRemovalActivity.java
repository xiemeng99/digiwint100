package digiwin.smartdepott100.module.activity.dailywork.dailworkscan;

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
import digiwin.smartdepott100.module.fragment.dailywork.stockremoval.StockRemovalaDetailFg;
import digiwin.smartdepott100.module.fragment.dailywork.stockremoval.StockRemovalaScanFg;

/**
 * @author maoheng
 * @des 扫出扫描
 * @date 2017/4/1
 */

public class StockRemovalActivity extends BaseFirstModuldeActivity{
    /**
     * 作业号
     */
    private String mode;

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
    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
    public StockRemovalaScanFg scanFg;
    /**
     * 明细提交
     */
    public StockRemovalaDetailFg detailFg;

    ModuleViewPagerAdapter adapter;

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.scanout_scan);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        mode = ModuleCode.SCANINSCAN;
        return mode;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_stockremoval;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new StockRemovalaScanFg();
        detailFg = new StockRemovalaDetailFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(detailFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.scandetail));
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
    private void select() {
        mZXVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    detailFg.upDateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public BaseFirstModuldeActivity.ExitMode exitOrDel() {
        return BaseFirstModuldeActivity.ExitMode.EXITISD;
    }
}
