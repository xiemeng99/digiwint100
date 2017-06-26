package digiwin.smartdepott100.module.activity.stock.printlabel;

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
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.stock.printlable.PrintLabelFlowScanFg;
import digiwin.smartdepott100.module.fragment.stock.printlable.PrintLabelFlowReprintFg;

/**
 * @des  流转标签打印
 * @author  孙长权
 * @date    2017/5/28
 */
public class PrintLabelFlowActivity extends BaseTitleActivity {
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

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
    public PrintLabelFlowScanFg scanFg;
    /**
     * 标签补打
     */
    PrintLabelFlowReprintFg reprintFg;

    ModuleViewPagerAdapter adapter;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRINTLABEL;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.print_label_flow);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_printlabel_flow;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new PrintLabelFlowScanFg();
        reprintFg = new PrintLabelFlowReprintFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(reprintFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.title_flowlable));
        titles.add(getResources().getString(R.string.title_reprint));
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
//                if (position == 1) {
//                    sumFg.upDateList();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
