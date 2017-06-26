package digiwin.smartdepott100.module.activity.dailywork.procedurecheck;

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
import digiwin.smartdepott100.module.fragment.dailywork.procedurecheckin.ProcedureCheckin1EmployeeFg;
import digiwin.smartdepott100.module.fragment.dailywork.procedurecheckin.ProcedureCheckin2DeviceFg;
import digiwin.smartdepott100.module.fragment.dailywork.procedurecheckin.ProcedureCheckin3MouldFg;
import digiwin.smartdepott100.module.fragment.dailywork.procedurecheckin.ProcedureCheckin4KniftFg;

/**
 * @author xiemeng
 * @des  生产报工设置界面
 * @date 2017/5/18 16:53
 */

public class ProcedureCheckinSetActivity extends BaseTitleActivity{

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
     * 人员维护
     */
    public ProcedureCheckin1EmployeeFg employeeFg;

    /**
     * 设备维护
     */
    public ProcedureCheckin2DeviceFg deviceFg;

    /**
     * 模据维护
     */
    public ProcedureCheckin3MouldFg mouldFg;


    /**
     * 刀具维护
     */
    public ProcedureCheckin4KniftFg kniftFg;

    ModuleViewPagerAdapter adapter;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 上一个页面的传值
     */
    public static final  String GETHEAD="head";


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PROCEDUCECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.procedure_checkin_set);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_procedure_checkin_set;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }


    /**
     * 初始化Fragment
     */
    private void initFragment() {

        employeeFg = new ProcedureCheckin1EmployeeFg();
        deviceFg=new ProcedureCheckin2DeviceFg();
        mouldFg=new ProcedureCheckin3MouldFg();
        kniftFg=new ProcedureCheckin4KniftFg();
        fragments = new ArrayList<>();
        fragments.add(employeeFg);
        fragments.add(deviceFg);
        fragments.add(mouldFg);
        fragments.add(kniftFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.employee_maintain));
        titles.add(getResources().getString(R.string.device_maintain));
        titles.add(getResources().getString(R.string.mould_maintain));
        titles.add(getResources().getString(R.string.knift_maintain));

        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        moduleVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(2)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(3)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(moduleVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        moduleVp.setOffscreenPageLimit(4);
        //select();
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
