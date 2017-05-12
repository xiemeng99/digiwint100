package digiwin.smartdepott100.module.test;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.test.test_fragment.TestOneFragment;
import digiwin.smartdepott100.module.test.test_fragment.TestTwoFragment;

public class TestModuleActivity extends BaseTitleActivity {

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private List<String> strings;
    /**
     * toolbar
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 条形扫码
     */
    TestOneFragment oneFragment;
    /**
     * 数据汇总
     */
    TestTwoFragment twoFragment;

    /**
     * viewpager
     */
    @BindView(R.id.module_vp)
    ViewPager module_vp;
    private ModuleViewPagerAdapter adapter;
    /**
     * TabLayout
     */
    @BindView(R.id.tl_tab)
    TabLayout tl_tab;

    @Override
    public String moduleCode() {
        return null;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_test_module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText("杂项收料");
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void doBusiness() {
        initFragment();
        initListener();
    }

    /**
     * 初始化Listener
     */
    private void initListener() {

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {

        oneFragment = new TestOneFragment();
        twoFragment = new TestTwoFragment();
        fragments = new ArrayList<>();
        fragments.add(oneFragment);
        fragments.add(twoFragment);
        strings = new ArrayList<>();
        strings.add(getResources().getString(R.string.ScanCode));
        strings.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();

        adapter = new ModuleViewPagerAdapter(fragmentManager,fragments,strings);
        module_vp.setAdapter(adapter);
        tl_tab.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < strings.size(); i++) {
            //添加标题页卡信息
            tl_tab.addTab(tl_tab.newTab().setText(strings.get(i)));
        }
        //Tablayout和ViewPager关联起来
        tl_tab.setupWithViewPager(module_vp);
        tl_tab.setTabsFromPagerAdapter(adapter);
    }
}
