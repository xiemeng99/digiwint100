package digiwin.smartdepott100.module.activity.stock.store;

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
import digiwin.smartdepott100.module.fragment.stock.store.ReturnMaterialScanFg;
import digiwin.smartdepott100.module.fragment.stock.store.ReturnMaterialSumFg;

/**
 * @author maoheng
 * @des 仓库退料 扫码/明细页
 * @date 2017/3/30
 */

public class StoreReturnMaterialActivity extends BaseFirstModuldeActivity {

    StoreReturnMaterialActivity pactivity;

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
    public ReturnMaterialScanFg scanFg;
    /**
     * 汇总提交
     */
    ReturnMaterialSumFg sumFg;
    ModuleViewPagerAdapter adapter;

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        pactivity = (StoreReturnMaterialActivity) activity;
        mName.setText(R.string.store_return_material);
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

        scanFg = new ReturnMaterialScanFg();
        sumFg = new ReturnMaterialSumFg();
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
                if(position == 0){
                    scanFg.updateView();
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
